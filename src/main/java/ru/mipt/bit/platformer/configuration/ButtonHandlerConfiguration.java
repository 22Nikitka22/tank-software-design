package ru.mipt.bit.platformer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.command.HealthBarCommand;
import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.command.ShotCommand;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.graphics.MapGraphic;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.utils.ButtonHandler;

import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.SPACE;

@Configuration
public class ButtonHandlerConfiguration {

    private final MapGraphic mapGraphic;
    private final MapModel mapModel;

    public ButtonHandlerConfiguration(MapGraphic mapGraphic, MapModel mapModel) {
        this.mapGraphic = mapGraphic;
        this.mapModel = mapModel;
    }

    @Bean
    public ButtonHandler buttonHandler() {
        ButtonHandler inputHandler = new ButtonHandler();

        Map<Direction, List<Integer>> controls = Map.of(
                Direction.UP, List.of(UP, W),
                Direction.LEFT, List.of(LEFT, A),
                Direction.DOWN, List.of(DOWN, S),
                Direction.RIGHT, List.of(RIGHT, D)
        );

        controls.forEach((direction, keys) ->
                inputHandler.addButtonAction(keys,
                        new MoveTankCommand(mapGraphic.getPlayerTank(), direction), true));

        inputHandler.addButtonAction(List.of(L), new HealthBarCommand(), false);
        inputHandler.addButtonAction(List.of(SPACE), new ShotCommand(mapModel.getPlayer()), false);

        return inputHandler;
    }
}
