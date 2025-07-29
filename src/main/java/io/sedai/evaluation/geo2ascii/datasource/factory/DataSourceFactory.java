package io.sedai.evaluation.geo2ascii.datasource.factory;

import io.sedai.evaluation.geo2ascii.datasource.DataSource;
import io.sedai.evaluation.geo2ascii.datasource.LocalFileDataSource;

public class DataSourceFactory {

    /**
     * Factory method to get a DataSource instance based on the type.
     *
     * @param type Type of data source (e.g., "local")
     * @return DataSource instance
     * @throws IllegalArgumentException if the type is unsupported
     */
    public DataSource getDataSource(String type) {
        if ("local".equalsIgnoreCase(type)) {
            return new LocalFileDataSource();
        }
        // Add more data source types as needed
        throw new IllegalArgumentException("Unsupported data source type: " + type);
    }
}
