package com.example.listycity;

import java.io.Serializable;

/**
 * This is a class that defines a City.
 */
public class City implements Serializable, Comparable<City> {
    /** The name of the city */
    private String name;
    /** The province where the city is located */
    private String province;

    /**
     * Constructs a new City object
     * @param name The name of the city
     * @param province The province where the city is located
     */
    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    /**
     * Gets the name of the city
     * @return The city name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the province of the city
     * @return The province name
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the name of the city
     * @param name The new city name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the province of the city
     * @param province The new province name
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Compares this city with another city by name (lexicographically)
     * @param city The city to compare with
     * @return A negative integer, zero, or a positive integer as this city's name
     *         is less than, equal to, or greater than the specified city's name
     */
    @Override
    public int compareTo(City city) {
        return this.name.compareTo(city.getName());
    }

    /**
     * Checks if this city is equal to another object
     * Two cities are considered equal if they have the same name and province
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name) && province.equals(city.province);
    }

    /**
     * Generates a hash code for this city
     * @return The hash code based on name and province
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + province.hashCode();
        return result;
    }
}
