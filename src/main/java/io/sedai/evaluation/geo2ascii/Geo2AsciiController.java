package io.sedai.evaluation.geo2ascii;

import io.sedai.evaluation.geo2ascii.datasource.factory.DataSourceFactory;
import io.sedai.evaluation.geo2ascii.plotter.Plotter;
import io.sedai.evaluation.geo2ascii.plotter.SimplePlotter;
import io.sedai.evaluation.geo2ascii.vo.GeoLocation;

import java.util.List;

public class Geo2AsciiController {

    public void generateAsciiSketch(int width, int height) {
        try {
            // Retrieve location data
            System.out.println("Getting location data...");
            List<GeoLocation> geoLocationData = new DataSourceFactory().getDataSource("local").getGeoLocationData(1000000);

            // Generate ASCII art
            System.out.println("Generating ASCII art...");
            Plotter plotter = new SimplePlotter(width, height);
            plotter.plot(geoLocationData);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
