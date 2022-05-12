package Sender;

import java.util.Map;

import Geo.Country;
import Geo.Location;
import Geo.GeoService;
import i18n.LocalizationService;

import static Geo.GeoServiceImpl.MOSCOW_IP;

public class MessageSenderImpl implements MessageSender {

    public static final String IP_ADDRESS_HEADER = "x-real-ip";
    private final GeoService geoService;

    private final LocalizationService localizationService;

    public MessageSenderImpl(GeoService geoService, LocalizationService localizationService) {
        this.geoService = geoService;
        this.localizationService = localizationService;
    }

    public String send(Map<String, String> headers) {
        String ipAddress = String.valueOf(headers.get(IP_ADDRESS_HEADER));
        if (ipAddress != null && !ipAddress.isEmpty()) {
            Location location = geoService.byIp(ipAddress);
            System.out.printf("Отправлено сообщение: %s", localizationService.locale(location.getCountry(MOSCOW_IP)) + '\n');
            return localizationService.locale(location.getCountry(MOSCOW_IP));
        }
        return localizationService.locale(Country.USA);
    }
}

