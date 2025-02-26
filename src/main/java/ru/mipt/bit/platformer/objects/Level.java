package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.getSingleLayer;

public class Level {

    final private TiledMap level;
    final private MapRenderer levelRenderer;

    public Level(String mapPath, Batch batch) {
        this.level = loadMap(mapPath);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
    }

    public void render() {
        levelRenderer.render();
    }

    public void dispose() {
        level.dispose();
    }

    public TiledMapTileLayer getLayer() {
        return getSingleLayer(level);
    }

    private TiledMap loadMap(String mapPath) {
        return new TmxMapLoader().load(mapPath);
    }
}