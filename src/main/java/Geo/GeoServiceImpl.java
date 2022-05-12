package Geo;

public class GeoServiceImpl implements GeoService {

    public static final String LOCALHOST = "127.0.0.1";
    public static final String MOSCOW_IP = "172.0.32.11";
    public static final String NEW_YORK_IP = "96.44.183.149";

    /**
     * Метод определяет локацию по ip
     */
    public Geo.Location byIp(String ip) {
        if (LOCALHOST.equals(ip)) {
            return new Geo.Location(null, null, null, 0);
        } else if (MOSCOW_IP.equals(ip)) {
            return new Geo.Location("Moscow", Country.RUSSIA, "Lenina", 15);
        } else if (NEW_YORK_IP.equals(ip)) {
            return new Geo.Location("New York", Country.USA, " 10th Avenue", 32);
        } else if (ip.startsWith("172.")) {
            return new Geo.Location("Moscow", Country.RUSSIA, null, 0);
        } else if (ip.startsWith("96.")) {
            return new Geo.Location("New York", Country.USA, null,  0);
        }
        return null;
    }

    /**
     * Метод определяет локацию по координатам
     */
    public Geo.Location byCoordinates() {
        throw new RuntimeException("Not implemented");
    }
}

