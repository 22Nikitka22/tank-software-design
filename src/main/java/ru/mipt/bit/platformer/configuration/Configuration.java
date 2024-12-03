package ru.mipt.bit.platformer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.mipt.bit.platformer.command.HealthBarCommand;
import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.command.ShotCommand;
import ru.mipt.bit.platformer.interfaces.MapLoader;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.loader.MapFileGraphic;
import ru.mipt.bit.platformer.loader.MapRandomGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.graphics.MapGraphic;
import ru.mipt.bit.platformer.utils.ButtonHandler;

import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.SPACE;

@org.springframework.context.annotation.Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ru.mipt.bit.platformer.objects")
public class Configuration {

    @Value("${app.loader.type}")
    private String type;

    @Value("${app.loader.path}")
    private String path;

    @Value("${app.loader.tanks}")
    private String tanks;

    @Value("${app.loader.rows}")
    private String rows;

    @Value("${app.loader.columns}")
    private String columns;

    @Bean
    public MapLoader mapLoader() {
        switch (type) {
            case "File": return new MapFileGraphic(path);
            case "Random": return new MapRandomGraphic(
                    Integer.parseInt(rows), Integer.parseInt(columns), Integer.parseInt(tanks)
            );
            default: throw new IllegalArgumentException("There is no such type of map loader");
        }
    }

    @Bean
    public ButtonHandler buttonHandler(MapGraphic mapGraphic) {
        ButtonHandler inputHandler = new ButtonHandler();
        MovingGraphic playerTank = mapGraphic.getPlayerTank();

        Map<Direction, List<Integer>> controls = Map.of(
                Direction.UP, List.of(UP, W),
                Direction.LEFT, List.of(LEFT, A),
                Direction.DOWN, List.of(DOWN, S),
                Direction.RIGHT, List.of(RIGHT, D)
        );

        controls.forEach((direction, keys) ->
                inputHandler.addButtonAction(keys,
                        new MoveTankCommand(playerTank, direction), true));

        inputHandler.addButtonAction(List.of(L), new HealthBarCommand(), false);
        inputHandler.addButtonAction(List.of(SPACE), new ShotCommand(playerTank.getModel()), false);

        return inputHandler;
    }
}
