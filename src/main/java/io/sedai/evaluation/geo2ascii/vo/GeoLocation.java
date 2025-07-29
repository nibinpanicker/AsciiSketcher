package io.sedai.evaluation.geo2ascii.vo;

/**
 * Represents a geographical location with latitude and longitude.
 * This record is immutable and provides a simple way to store and access
 * geographic coordinates.
 *
 * @param latitude  The latitude of the location.
 * @param longitude The longitude of the location.
 */
public record GeoLocation (
    double latitude,
    double longitude) {
}
