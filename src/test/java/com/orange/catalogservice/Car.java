package com.orange.catalogservice;

import java.time.LocalDate;
import java.util.List;

public record Car(String brand,
           String licensePlate,
           LocalDate releaseDate,
                  List<Owner> owners){}
record Owner(String firstName, String lastName){}

