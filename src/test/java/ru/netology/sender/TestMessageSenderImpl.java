package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TestMessageSenderImpl {
    GeoService geoService;
    LocalizationService localizationService;
    Map<String, String> headers;
    MessageSenderImpl messageSender;

    @BeforeEach
    void startTest(){
        geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.anyString()))
                .thenReturn(new Location("New York", Country.USA, null,  0));
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        headers = new HashMap<String, String>();
        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    void testLanguageTextFromIpRussia() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.44.183.149");
        String preferences = messageSender.send(headers);
        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, preferences);
    }

    @Test
    void testLanguageTextFromIpEnglish() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");
        String preferences = messageSender.send(headers);
        String expected= "Welcome";
        Assertions.assertEquals(expected, preferences);
    }

}
