package com.example.listycity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityListTest {

    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    @Test
    void testAdd() {
        CityList cityList = mockCityList();

        assertEquals(1, cityList.getCities().size());

        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);

        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains(city));
    }

    @Test
    void testAddException() {
        CityList cityList = mockCityList();

        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);

        assertThrows(IllegalArgumentException.class, () -> {
            cityList.add(city);
        });
    }

    @Test
    void testGetCities() {
        CityList cityList = mockCityList();

        assertEquals(0, mockCity().compareTo(cityList.getCities().get(0)));

        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        assertEquals(0, city.compareTo(cityList.getCities().get(0)));
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(1)));
    }

    @Test
    void testHasCity() {
        CityList cityList = mockCityList();
        City city = mockCity();

        // Test that the city is in the list
        assertTrue(cityList.hasCity(city));

        // Test that a different city is not in the list
        City newCity = new City("Calgary", "Alberta");
        assertFalse(cityList.hasCity(newCity));

        // Add the new city and test again
        cityList.add(newCity);
        assertTrue(cityList.hasCity(newCity));
    }

    @Test
    void testDelete() {
        CityList cityList = mockCityList();
        City city = mockCity();

        // Verify city is in the list
        assertTrue(cityList.hasCity(city));
        assertEquals(1, cityList.countCities());

        // Delete the city
        cityList.delete(city);

        // Verify city is removed
        assertFalse(cityList.hasCity(city));
        assertEquals(0, cityList.countCities());
    }

    @Test
    void testDeleteException() {
        CityList cityList = mockCityList();
        City city = new City("Vancouver", "British Columbia");

        // Try to delete a city that doesn't exist
        assertThrows(IllegalArgumentException.class, () -> {
            cityList.delete(city);
        });
    }

    @Test
    void testCountCities() {
        CityList cityList = new CityList();

        // Test empty list
        assertEquals(0, cityList.countCities());

        // Add one city
        cityList.add(mockCity());
        assertEquals(1, cityList.countCities());

        // Add more cities
        cityList.add(new City("Toronto", "Ontario"));
        assertEquals(2, cityList.countCities());

        cityList.add(new City("Montreal", "Quebec"));
        assertEquals(3, cityList.countCities());

        // Delete a city
        cityList.delete(mockCity());
        assertEquals(2, cityList.countCities());
    }
}