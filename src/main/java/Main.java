import java.util.HashMap;
import java.util.Map;
import Geo.GeoService;
import Geo.GeoServiceImpl;
import Sender.MessageSender;
import Sender.MessageSenderImpl;
import i18n.LocalizationService;
import i18n.LocalizationServiceImpl;

public class Main{

    //Тестовый пример
    public static void main(String[] args) {
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        messageSender.send(headers);
    }
}