package Geo;

public interface GeoService {

    Geo.Location byIp(String ip);

    Geo.Location byCoordinates();
}
