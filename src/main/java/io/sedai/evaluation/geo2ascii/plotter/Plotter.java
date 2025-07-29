package io.sedai.evaluation.geo2ascii.plotter;

import io.sedai.evaluation.geo2ascii.vo.GeoLocation;

import java.util.List;

public interface Plotter {

    /**
     * Plots ASCII art based on the provided geolocation data.
     *
     * @param geoLocations List of GeoLocation objects containing latitude and longitude data
     */
    void plot(List<GeoLocation> geoLocations);
}
