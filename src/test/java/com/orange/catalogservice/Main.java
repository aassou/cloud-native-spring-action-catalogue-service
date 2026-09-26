//package com.orange.catalogservice;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
////        demo1();
//        List<Car> cars = List.of(
//            new Car("Mercedes Benz", "124563987", LocalDate.of(2022, 12, 25)),
//            new Car("Mercedes Benz", "124563987", LocalDate.of(2007, 9, 25)),
//            new Car("BYD", "124563987", LocalDate.of(2025, 5, 25)),
//            new Car("BMW", "124563987", LocalDate.of(2026, 4, 25)),
//            new Car("BMW", "124563987", LocalDate.of(2021, 3, 25)),
//            new Car("Audi", "124563987", LocalDate.of(2019, 12, 25))
//            );
//
//        // select all Mercedes Benz cars:
////        System.out.println(cars.stream()
////            .filter(car -> car.brand.equals("Mercedes Benz"))
////            .toList());
//
//        // select all cars released after 2015
//        System.out.println(cars.stream()
//            .filter(car -> car.releaseDate().getYear() > 2015)
//            .toList());
//    }
//
//    private static void demo1() {
//        List<String> numbers = List.of("One", "Two", "Three");
//
//        List<String> transformedList = numbers.stream()
//            .filter(item -> item.length() > 3) // intermediate operation
//            .map(item -> "This is: " + item) // intermediate operation
//            .toList(); // terminal operation
//
//        System.out.println(transformedList);
//    }
//
//}
