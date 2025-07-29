# ASCII Sketcher

A Java application that converts geographical location data into ASCII art visualizations. The project reads geolocation data from CSV files and renders them as simple ASCII grid plots on the console.

## Features

- **Geolocation Data Processing**: Reads latitude and longitude coordinates from CSV files
- **ASCII Art Generation**: Converts geographical coordinates into visual ASCII grid representations
- **Flexible Data Sources**: Modular data source architecture supporting local file input
- **Configurable Grid Size**: Customizable ASCII grid dimensions (default: 120x120)
- **ZIP File Support**: Automatically handles compressed CSV files

## Project Structure

```
src/main/java/io/sedai/evaluation/geo2ascii/
├── AsciiSketcher.java              # Main entry point
├── Geo2AsciiController.java        # Main controller orchestrating the process
├── datasource/
│   ├── DataSource.java             # Interface for data sources
│   ├── LocalFileDataSource.java    # CSV file data source implementation
│   └── factory/
│       └── DataSourceFactory.java  # Factory for creating data sources
├── plotter/
│   ├── Plotter.java                # Interface for plotting implementations
│   └── SimplePlotter.java          # ASCII grid plotter implementation
└── vo/
    └── GeoLocation.java            # Value object for latitude/longitude pairs
```

## Requirements

- **Java 17+** (uses record types)
- **Gradle** (build system)

## Dependencies

- [FastCSV](https://github.com/osiegmar/FastCSV) (4.0.0) - For efficient CSV parsing

## Getting Started

### Installation

Build the project:
   ```bash
   ./gradlew build
   ```

### Running the Application

Run the application using Gradle:
```bash
./gradlew run
```

Optionally, you can specify the grid width and height as command line arguments. For example, to set a grid size of 200x200:

```bash
./gradlew run --args="200 200"
```

Or run the compiled JAR:
```bash
java -cp build/libs/AsciiSketcher-1.0-SNAPSHOT.jar io.sedai.evaluation.geo2ascii.AsciiSketcher
```

### Data Format

The application expects geolocation data in CSV format. By default, it looks for `data/ukpostcodes.csv.zip` which should contain:
- Latitude values in one column
- Longitude values in another column

The CSV parser will automatically extract latitude and longitude coordinates from the file.

## Usage Example

When you run the application, you'll see output similar to:

```
ASCII Sketcher
================================
Getting location data...
Generating ASCII art...
Lat 50.12 to 60.85, Lon -7.64 to 1.77
                    *
                 *     *
              *           *
           *                 *
        *                       *
     *                             *
  *                                   *
*                                       *

Total geo locations rendered: 1000000
```

Each `*` represents one or more geographical locations plotted on the ASCII grid.

## Architecture

### Data Flow

1. **Data Loading**: `LocalFileDataSource` reads and parses CSV data from compressed files
2. **Data Processing**: Geographic coordinates are extracted into `GeoLocation` objects
3. **Plotting**: `SimplePlotter` normalizes coordinates and maps them to ASCII grid positions
4. **Rendering**: The grid is printed to console with `*` characters representing data points

### Key Components

- **DataSource Interface**: Abstraction for different data input methods
- **Plotter Interface**: Abstraction for different visualization techniques
- **Factory Pattern**: Used for creating appropriate data source implementations
- **Record Types**: Modern Java records for immutable data transfer objects

### Assumptions

1. The file format of the source data is consistent with ukpostcodes.csv.zip file structure
2. The application is run in a console that supports ASCII output
3. The grid size is adjustable but defaults to 120x120 for optimal display
4. The sample size is currently set to 1,000,000 records for demonstration purposes

## Customization

### Custom Grid Size

You can modify the grid dimensions by instantiating `SimplePlotter` with custom parameters:

```java
Plotter plotter = new SimplePlotter(80, 40); // 80x40 grid
```

### Adding New Data Sources

Implement the `DataSource` interface and register it in `DataSourceFactory`:

```java
public class CustomDataSource implements DataSource {
    @Override
    public List<GeoLocation> getGeoLocationData(int limit) throws IOException {
        // Your implementation here
    }
}
```

### Custom Plotting

Implement the `Plotter` interface for different visualization styles:

```java
public class CustomPlotter implements Plotter {
    @Override
    public void plot(List<GeoLocation> geoLocations) {
        // Your custom plotting logic here
    }
}
```

## Building and Testing

### Build
```bash
./gradlew build
```

### Clean Build
```bash
./gradlew clean build
```

### Generate JAR
```bash
./gradlew jar
```

## License

This project is part of an evaluation exercise for geographical data visualization.

## Troubleshooting

### Common Issues

- **File Not Found**: Ensure `data/ukpostcodes.csv.zip` exists in the project root
- **Memory Issues**: For large datasets, consider increasing JVM heap size: `-Xmx2g`
- **Grid Display**: If the output appears misaligned, ensure your console supports the grid width

### Performance Notes

- The application loads up to 1,000,000 records by default
- Grid rendering performance scales with grid size and data points
- Large datasets may require increased memory allocation
