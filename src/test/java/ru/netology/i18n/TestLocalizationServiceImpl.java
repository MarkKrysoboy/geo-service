package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class TestLocalizationServiceImpl {

    @ParameterizedTest
    @MethodSource("argumentsStream")
    public void testLocationFromIp(Country country, String expected) {
        //arrange
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        //act
        String result = localizationService.locale(country);
        //assert
        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> argumentsStream() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"));
    }
}
