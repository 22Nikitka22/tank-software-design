package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapFileGraphic implements MapLoader {

    private final File file;

    public MapFileGraphic(String mapFile) { this.file = new File(mapFile); }

    @Override
    public MapModel loadMap() {
        try(BufferedReader br = new BufferedReader (new FileReader(file))) {
            return readFile(br);
        }
        catch(IOException ex) {
            throw new RuntimeException("Failed to read file", ex);
        }
    }

    private MapModel readFile(BufferedReader br) throws IOException {
        int x = 0;

        List<GridPoint2> treesCoordinates = new ArrayList<>();
        GridPoint2 playerCoordinates = null;

        while (br.ready()) {
            String line = br.readLine();

            for (int y = 0; y < line.length(); y++) {
                char cell = line.charAt(y);
                switch (cell) {
                    case 'T': {
                        treesCoordinates.add(new GridPoint2(x, y));
                    }
                    case 'X': {
                        playerCoordinates = new GridPoint2(x, y);
                    }
                }
            }
            x++;
        }

        return new MapModel(treesCoordinates, playerCoordinates);
    }
}
