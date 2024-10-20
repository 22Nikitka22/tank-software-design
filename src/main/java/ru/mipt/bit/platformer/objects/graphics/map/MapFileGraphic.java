package ru.mipt.bit.platformer.objects.graphics.map;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapFileGraphic implements MapLoader {

    private final File file;

    public MapFileGraphic(String mapFile) {
        this.file = new File(mapFile);
    }

    @Override
    public MapModel loadMap() {
        try(BufferedReader br = new BufferedReader (new FileReader(file))) {
            return readFile(br);
        } catch(IOException ex) {
            throw new RuntimeException("Failed to read file", ex);
        }
    }

    private MapModel readFile(BufferedReader br) throws IOException {
        List<GridPoint2> treesCoordinates = new ArrayList<>();
        GridPoint2 playerCoordinates = null;

        String line;
        int x = 0;

        while ((line = br.readLine()) != null) {
            processLine(line, x, treesCoordinates);
            if (playerCoordinates == null) {
                playerCoordinates = findPlayerCoordinates(line, x);
            }
            x++;
        }

        return new MapModel(treesCoordinates, playerCoordinates);
    }

    private void processLine(String line, int x, List<GridPoint2> treesCoordinates) {
        for (int y = 0; y < line.length(); y++) {
            if (line.charAt(y) == 'T') {
                treesCoordinates.add(new GridPoint2(x, y));
            }
        }
    }

    private GridPoint2 findPlayerCoordinates(String line, int x) {
        for (int y = 0; y < line.length(); y++) {
            if (line.charAt(y) == 'X') {
                return new GridPoint2(x, y);
            }
        }
        return null;
    }
}
