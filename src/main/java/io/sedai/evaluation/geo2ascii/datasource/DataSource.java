package io.sedai.evaluation.geo2ascii.datasource;

import io.sedai.evaluation.geo2ascii.vo.GeoLocation;

import java.io.IOException;
import java.util.List;

public interface DataSource {

    int NO_LIMIT = -1;

    /**
     * Retrieves geolocation data from the data source.
     *
     * @param limit Maximum number of records to read. Use -1 for no limit.
     * @return List of GeoLocation objects containing latitude and longitude data
     * @throws IOException if file reading fails
     */
    List<GeoLocation> getGeoLocationData(int limit) throws IOException;
}
