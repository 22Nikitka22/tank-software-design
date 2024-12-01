package ru.mipt.bit.platformer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.interfaces.MapLoader;
import ru.mipt.bit.platformer.loader.MapFileGraphic;
import ru.mipt.bit.platformer.loader.MapRandomGraphic;
import ru.mipt.bit.platformer.objects.models.MapModel;

@Configuration
public class LoaderConfiguration {

    @Value("${app.loader.type}")
    private String type;

    @Value("${app.loader.path}")
    private String path;

    @Value("${app.loader.tanks}")
    private Integer tanks;

    @Value("${app.loader.rows}")
    private Integer rows;

    @Value("${app.loader.columns}")
    private Integer columns;

    @Bean
    public MapLoader mapLoader() {
        switch (type) {
            case "File": return new MapFileGraphic(path);
            case "Random": return new MapRandomGraphic(rows, columns, tanks);
            default: throw new IllegalArgumentException("There is no such type of map loader");
        }
    }

    @Bean
    public MapModel mapModel() {
        return mapLoader().loadMap();
    }
}
