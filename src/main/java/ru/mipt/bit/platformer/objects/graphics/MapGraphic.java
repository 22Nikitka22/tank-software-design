package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.interfaces.BulletObserver;
import ru.mipt.bit.platformer.interfaces.Graphic;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.interfaces.TankObserver;
import ru.mipt.bit.platformer.objects.Level;
import ru.mipt.bit.platformer.objects.models.BulletModel;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapGraphic implements BulletObserver, TankObserver {

    private static final String TANK_PLAYER_PATH = "images/tank_blue.png";
    private static final String TANK_PATH = "images/tank_green.png";
    private static final String TREE_PATH = "images/greenTree.png";
    private static final String LEVEL_FILE = "level.tmx";

    private Level gameLevel;
    private TileMovement tileMovement;
    private MovingGraphic playerTank;
    private Collection<MovingGraphic> enemyTanks;
    private Collection<Graphic> trees;
    private final Collection<BulletGraphic> bullets = new HashSet<>();
    private final MapModel mapModel;

    public MapGraphic(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public void create(Batch batch) {
        initializeGraphics(batch);
        initializeGameObjects();
    }

    public void render(float deltaTime) {
        playerTank.update(tileMovement, deltaTime);
        enemyTanks.forEach(tank -> tank.update(tileMovement, deltaTime));
    }

    public void render(Batch batch) {
        playerTank.render(batch);
        enemyTanks.forEach(tank -> tank.render(batch));
        trees.forEach(tree -> tree.render(batch));
        bullets.forEach(bullet -> bullet.render(batch));
    }

    public void renderLevel() {
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
    public void bulletAppeared(BulletModel bullet) {
        BulletGraphic bulletGraphic = new BulletGraphic("images/bullet.png", bullet);
        bullets.add(bulletGraphic);
        bullet.setBulletObserver(this);
    }

    @Override
    public void bulletDestroyed(BulletModel bullet) {
        Optional<BulletGraphic> bulletGraphicOpt = bullets.stream()
                .filter(b -> b.getBulletModel() == bullet)
                .findFirst();

        bulletGraphicOpt.ifPresent(bulletGraphic -> {
            bulletGraphic.dispose();
            bullets.remove(bulletGraphic);
        });
    }

    @Override
    public void tankDestroyed(TankModel tankModel) {
        Optional<MovingGraphic> tankGraphicsOpt = enemyTanks.stream()
                .filter(e -> e.getModel() == tankModel)
                .findFirst();

        if (tankGraphicsOpt.isPresent()) {
            MovingGraphic tankGraphics = tankGraphicsOpt.get();
            tankGraphics.dispose();
            enemyTanks.remove(tankGraphics);
        } else if (playerTank.getModel() == tankModel) {
            throw new RuntimeException("GAME OVER");
        }
    }

    private void initializeGraphics(Batch batch) {
        gameLevel = new Level(LEVEL_FILE, batch);
        tileMovement = new TileMovement(gameLevel.getLayer(), Interpolation.smooth);
    }

    private void initializeGameObjects() {
        playerTank = new HealthBarDecorator(new TankGraphic(TANK_PLAYER_PATH, mapModel.getPlayer()));
        playerTank.getModel().setBulletObserver(this);
        playerTank.getModel().setTankObserver(this);

        enemyTanks = mapModel.getTanks().stream()
                .map(tank -> new TankGraphic(TANK_PATH, tank))
                .map(HealthBarDecorator::new)
                .peek(healthBarDecorator -> healthBarDecorator.getModel().setBulletObserver(this))
                .peek(healthBarDecorator -> healthBarDecorator.getModel().setTankObserver(this))
                .collect(Collectors.toList());

        trees = mapModel.getTrees().stream()
                .map(tree -> new TreeGraphic(TREE_PATH, tree, gameLevel.getLayer()))
                .collect(Collectors.toList());
    }

    public MovingGraphic getPlayerTank() {
        return playerTank;
    }

    public Collection<MovingGraphic> getEnemyTanks() {
        return enemyTanks;
    }
}
