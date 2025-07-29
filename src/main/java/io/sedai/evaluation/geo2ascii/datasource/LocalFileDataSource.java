package io.sedai.evaluation.geo2ascii.datasource;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;
import io.sedai.evaluation.geo2ascii.vo.GeoLocation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Data source implementation that reads geolocation data from a local CSV file.
 */
public class LocalFileDataSource implements DataSource {

    private static final String CSV_FILE_PATH = "data/ukpostcodes.csv.zip";

    /**
     * Retrieves geolocation data from the local CSV file.
     *
     * @param limit Maximum number of records to read. Use -1 for no limit.
     * @return List of GeoLocation objects containing latitude and longitude data
     * @throws IOException if file reading fails
     */
    public List<GeoLocation> getGeoLocationData(int limit) throws IOException {
        List<GeoLocation> geoLocations = new ArrayList<>(limit > 0 ? limit : 1000);
        Path zipPath = Paths.get(CSV_FILE_PATH);

        if (limit < -1 || limit == 0) {
            throw new IllegalArgumentException("Limit must be -1 or positive");
        }

        if (!Files.exists(zipPath)) {
            throw new IOException("CSV file not found at: " + CSV_FILE_PATH);
        }

        try (InputStream fileInputStream = Files.newInputStream(zipPath);
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {

            // Assuming the ZIP contains only one csv file
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry != null) {
                try (java.io.InputStreamReader isr = new java.io.InputStreamReader(zipInputStream, java.nio.charset.StandardCharsets.UTF_8);
                    CsvReader<CsvRecord> csvReader = CsvReader.builder().ofCsvRecord(isr);) {
                    csvReader.skipLines(1); // Skip header line

                    Stream<CsvRecord> csvStream = csvReader.stream();
                    if (limit > 0) {
                        csvStream = csvStream.limit(limit);
                    }

                    csvStream.forEach(csvRecord -> {
                        try {
                            if (csvRecord.getFieldCount() >= 3) {
                                // Assuming format: postcode, latitude, longitude, ...
                                double latitude = Double.parseDouble(csvRecord.getField(2).trim());
                                double longitude = Double.parseDouble(csvRecord.getField(3).trim());

                                geoLocations.add(new GeoLocation(latitude, longitude));
                            }
                        } catch (NumberFormatException e) {
                            // Skip invalid lines and continue processing
                            System.err.printf("Skipping invalid row: Invalid number format - %s%n", e.getMessage());
                        } catch (Exception e) {
                            // Skip other invalid lines and continue processing
                            System.err.printf("Skipping invalid row: %s%n", e.getMessage());
                        }
                    });
                }
            } else {
                throw new IOException("No entries found in the file: " + CSV_FILE_PATH);
            }
        }

        if (geoLocations.isEmpty()) {
            throw new IOException("No CSV records found in the ZIP file");
        }

        System.out.printf("Successfully loaded %d geolocation records%n", geoLocations.size());
        return geoLocations;
    }
}
