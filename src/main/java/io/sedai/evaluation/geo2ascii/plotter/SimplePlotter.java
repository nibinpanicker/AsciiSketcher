package io.sedai.evaluation.geo2ascii.plotter;

import io.sedai.evaluation.geo2ascii.vo.GeoLocation;

import java.util.Arrays;
import java.util.List;

/**
 * SimplePlotter is an implementation of the Plotter interface that generates a simple ASCII grid
 * representation of geolocation data. It plots points based on latitude and longitude values.
 * The grid size can be customized, with a default maximum size of 120x120.
 * The grid is printed to the console, with each point represented by an asterisk (*).
 */
public class SimplePlotter implements Plotter {

    private static final int MAX_GRID_WIDTH = 120;
    private static final int MAX_GRID_HEIGHT = 120;

    private final int maxGridWidth;
    private final int maxGridHeight;

    public SimplePlotter() {
        this.maxGridWidth = MAX_GRID_WIDTH;
        this.maxGridHeight = MAX_GRID_HEIGHT;
    }

    public SimplePlotter(int maxGridWidth, int maxGridHeight) {
        if (maxGridWidth <= 0 || maxGridHeight <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
        this.maxGridWidth = maxGridWidth;
        this.maxGridHeight = maxGridHeight;
    }

    /**
     * Plots the given list of GeoLocation objects on a simple ASCII grid.
     *
     * @param geoLocations List of GeoLocation objects to plot
     */
    public void plot(List<GeoLocation> geoLocations) {
        if (geoLocations.isEmpty()) {
            System.out.println("No data to plot");
            return;
        }

        // Find min and max bounds
        double minLat = 0, minLon = 0;
        double maxLat = 1, maxLon = 1;

        for (GeoLocation getLocation : geoLocations) {
            if (getLocation.latitude() < minLat || minLat == 0) {
                minLat = getLocation.latitude();
            }
            if (getLocation.latitude() > maxLat || maxLat == 1) {
                maxLat = getLocation.latitude();
            }
            if (getLocation.longitude() < minLon || minLon == 0) {
                minLon = getLocation.longitude();
            }
            if (getLocation.longitude() > maxLon || maxLon == 1) {
                maxLon = getLocation.longitude();
            }
        }

        System.out.printf("Lat %.2f to %.2f, Lon %.2f to %.2f%n", minLat, maxLat, minLon, maxLon);

        // Create an array to store grid locations
        boolean[][] grid = new boolean[maxGridHeight][maxGridWidth];

        // Initialize grid
        for (int y = 0; y < maxGridHeight; y++) {
            Arrays.fill(grid[y], false);
        }

        // Computing grid positions after normalizing the coordinates
        double latScale = (maxGridHeight - 1) / (maxLat - minLat);
        double lonScale = (maxGridWidth - 1) / (maxLon - minLon);
        for (GeoLocation getLocation : geoLocations) {
            int x = (int) ((getLocation.longitude() - minLon) * lonScale);
            int y = (int) ((maxLat - getLocation.latitude()) * latScale);

            if (x >= 0 && x < maxGridWidth && y >= 0 && y < maxGridHeight) { //Safety check
                grid[y][x] = true;
            }
        }

        // Printing the grid
        for (int y = 0; y < maxGridHeight; y++) {
            StringBuilder line = new StringBuilder(maxGridWidth);
            for (int x = 0; x < maxGridWidth; x++) {
                line.append(grid[y][x] ? '*' : ' ');
            }
            System.out.println(line);
        }

        System.out.printf("Total geo locations rendered: %d%n", geoLocations.size());
    }
}
