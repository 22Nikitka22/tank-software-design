package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.interfaces.*;
import ru.mipt.bit.platformer.interfaces.Observer;
import ru.mipt.bit.platformer.objects.Level;
import ru.mipt.bit.platformer.objects.models.HealthBarModel;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.utils.TankAIController;
import ru.mipt.bit.platformer.utils.TileMovement;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MapGraphic implements Observer {

    @Value("${app.tank.player.path}")
    private String TANK_PLAYER_PATH;
    @Value("${app.tank.path}")
    private String TANK_PATH;
    @Value("${app.tree.path}")
    private String TREE_PATH;
    @Value("${app.bullet.path}")
    private String BULLET_PATH;
    @Value("${app.level.path}")
    private String LEVEL_FILE;

    private Level gameLevel;
    private TileMovement tileMovement;
    private MovingGraphic playerTank;
    private Collection<MovingGraphic> enemyTanks;
    private Collection<Graphic> trees;
    private final Collection<MovingGraphic> bullets = new HashSet<>();
    private final MapModel mapModel;

    @Autowired
    public MapGraphic(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public void render(Batch batch) {
        playerTank.render(batch);
        enemyTanks.forEach(tank -> tank.render(batch));
        trees.forEach(tree -> tree.render(batch));
        bullets.forEach(bullet -> bullet.render(batch));
    }

    public void renderLevel(float deltaTime) {
        enemyTanks.forEach(TankAIController::control);
        playerTank.update(tileMovement, deltaTime);
        enemyTanks.forEach(tank -> tank.update(tileMovement, deltaTime));
        List.copyOf(bullets).forEach(bullet -> bullet.update(tileMovement, deltaTime));
        gameLevel.render();
    }

    public void dispose() {
        gameLevel.dispose();
        playerTank.dispose();
        enemyTanks.forEach(Graphic::dispose);
        trees.forEach(Graphic::dispose);
        bullets.forEach(Graphic::dispose);
    }

    @Override
    public void objectAppeared(MovingModel model, String name) {
        mapModel.addBullet(model);
        if (Objects.equals(name, "bullet")) {
            BulletGraphic bulletGraphic = new BulletGraphic(BULLET_PATH, model);
            bullets.add(bulletGraphic);
            model.setObjectObserver(this);
        }
    }

    @Override
    public void objectDestroyed(MovingModel model, String name) {
        Optional<MovingGraphic> objectGraphicOpt = Stream.concat(bullets.stream(), enemyTanks.stream())
                .filter(b -> b.getModel() == model)
                .findFirst();

        if (Objects.equals(name, "bullet")) {
            objectGraphicOpt.ifPresent(bulletGraphic -> {
                bulletGraphic.dispose();
                bullets.remove(bulletGraphic);
            });
        } else if (Objects.equals(name, "tank") && objectGraphicOpt.isPresent()) {
            MovingGraphic tankGraphics = objectGraphicOpt.get();
            tankGraphics.dispose();
            enemyTanks.remove(tankGraphics);
        } else if (playerTank.getModel() == model) {
            throw new RuntimeException("GAME OVER");
        }
    }

    public void initializeGraphics(Batch batch) {
        gameLevel = new Level(LEVEL_FILE, batch);
        tileMovement = new TileMovement(gameLevel.getLayer(), Interpolation.smooth);
    }

    @PostConstruct
    public void initializeGameObjects() {
        playerTank = new HealthBarDecorator(
                new TankGraphic(TANK_PLAYER_PATH, mapModel.getPlayer()),
                new HealthBarModel(true)
        );
        playerTank.getModel().setObjectObserver(this);

        enemyTanks = mapModel.getTanks().stream()
                .map(tank -> new TankGraphic(TANK_PATH, tank))
                .map(tank -> new HealthBarDecorator(tank, new HealthBarModel(true)))
                .peek(healthBarDecorator -> healthBarDecorator.getModel().setObjectObserver(this))
                .collect(Collectors.toList());

        trees = mapModel.getTrees().stream()
                .map(tree -> new TreeGraphic(TREE_PATH, tree, gameLevel.getLayer()))
                .collect(Collectors.toList());
    }

    public MovingGraphic getPlayerGraphic() {
        return playerTank;
    }

    public TankModel getPlayerModel() {
        return mapModel.getPlayer();
    }
}
