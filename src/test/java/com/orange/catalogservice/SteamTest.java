package com.orange.catalogservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SteamTest {

    @Test
    public void test() {
        String[] names = {"Modern", "Java", "In", "Action"};

        System.out.println(Arrays.stream(names)
            .flatMap(it -> Arrays.stream(it.split("")))
            .toList());;
    }

    private static final List<Car> CARS = List.of(
        new Car("Mercedes Benz", "124563987", LocalDate.of(2022, 12, 25), List.of(
            new Owner("Mohamed", "Aassou"),
            new Owner("Karim", "El Berkani"),
            new Owner("Abdelilah", "Aassou")
        )),
        new Car("Mercedes Benz", "124563987", LocalDate.of(2007, 9, 25), List.of(
            new Owner("Mohamed", "Aassou"),
            new Owner("Karim", "El Berkani"),
            new Owner("Abdelilah", "Aassou")
        )),
        new Car("BYD", "124563987", LocalDate.of(2025, 5, 25), List.of(
            new Owner("Mohamed", "Aassou"),
            new Owner("Abdelilah", "Aassou")
        )),
        new Car("BMW", "124563987", LocalDate.of(2026, 4, 25), List.of(
            new Owner("Mohamed", "Aassou"),
            new Owner("Karim", "El Berkani")
        )),
        new Car("BMW", "124563987", LocalDate.of(2021, 3, 25), List.of(
            new Owner("Mohamed", "Ajwaw"),
            new Owner("Karim", "El Berkani")
        )),
        new Car("Audi", "124563987", LocalDate.of(2019, 12, 25), List.of(
            new Owner("Mohamed", "Aassou"),
            new Owner("Karim", "El Berkani"),
            new Owner("Abdelilah", "Aassou")
        ))
    );

    @Test
    public void selectAllCarsOwnedByAassou() {
        var owners = CARS.stream()
            .filter(car -> car.owners().stream()
                .map(Owner::lastName)
                .toList().contains("Aassou")
            )
            .toList();

        owners.forEach(System.out::println);
    }

    @Test
    public void testAllMercedesBenzCars() {
        var mercedesBenzCars = CARS.stream()
            .filter(car -> car.brand().equals("Mercedes Benz"))
            .toList();

        Assertions.assertEquals(2, mercedesBenzCars.size());
        Assertions.assertEquals("Mercedes Benz", mercedesBenzCars.get(0).brand());
    }

    @Test
    public void testSelectUniqueBrands() {
        var uniqueBrands = CARS.stream()
            .map(Car::brand)
            .distinct()
            .toList();

        Assertions.assertNotNull(uniqueBrands);
        Assertions.assertEquals(4, uniqueBrands.size());
        Assertions.assertLinesMatch(List.of("Mercedes Benz", "BYD", "BMW", "Audi"), uniqueBrands);
        Assertions.assertArrayEquals(new String[]{"Mercedes Benz", "BYD", "BMW", "Audi"}, uniqueBrands.toArray());
        System.out.println(uniqueBrands);
    }
}
