import Geo.Country;
import Geo.GeoService;
import Geo.GeoServiceImpl;
import Geo.Location;
import Sender.MessageSender;
import Sender.MessageSenderImpl;
import i18n.LocalizationService;
import i18n.LocalizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MessageSenderTest{
    @ParameterizedTest
    @MethodSource("testSource0")
    void sendTest(Location location, String ip, Country country, String expected) {
        Map<String, String> headers = new HashMap<>();
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(expected);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSender.send(headers);
        assertEquals(expected, message);
    }
    private static Stream<Arguments> testSource0(){
        Map<String, String> headers = new HashMap<>();
        return Stream.of(
                Arguments.of(
                        new Location("Moscow", Country.RUSSIA, null, 0),
                        "172.112.27.16",
                        Country.RUSSIA,
                        "Добро пожаловать"),
                Arguments.of(
                        new Location(null, Country.USA, null, 0),
                        "96.55.183.149",
                        Country.USA,
                        "Welcome"),
                Arguments.of(
                        new Location(null, Country.BRAZIL, null, 0),
                        "32.123.456.65",
                        Country.BRAZIL,
                        "Welcome")
        );
    }
    //    @Test
//    void testSendRussia(){
//        Location location = new Location("Moscow", Country.RUSSIA, null, 0);
//        Map<String, String> headers = new HashMap<>();
//        String ip = "172.112.27.16";
//        String expected = "Добро пожаловать";
//
//        GeoService geoService = Mockito.mock(GeoService.class);
//        Mockito.when(geoService.byIp(ip)).thenReturn(location);
//
//        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
//        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn(expected);
//
//        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
//        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
//        String result = messageSender.send(headers);
//
//        assertEquals(expected, result);
//    }


    @ParameterizedTest
    @MethodSource("testSource1")
    void byIpTest(String ip, Country expected){
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp(ip);
        Country result = location.getCountry(ip);
        assertEquals(expected, result);
    }
    private static Stream<Arguments> testSource1(){
        return Stream.of(
                Arguments.of("172.3.32.11", Country.RUSSIA),
                Arguments.of("96.55.183.149", Country.USA)
        );
    }

    @ParameterizedTest
    @MethodSource("testSource2")
    void localeTest(Country country, String expected){
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String result = localizationService.locale(country);
        assertEquals(expected, result);
    }
    private static Stream<Arguments> testSource2(){
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }

    @Test
    public void byCoordinatesTestThrowsExceptions(){
        GeoService geoService = new GeoServiceImpl();
        Class<RuntimeException> expected = RuntimeException.class;
        assertThrowsExactly(expected, ()-> geoService.byCoordinates());
    }
}