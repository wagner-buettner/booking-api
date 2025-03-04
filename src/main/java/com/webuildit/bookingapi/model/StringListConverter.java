package com.webuildit.bookingapi.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "{}"; // keeping the same format as we have currently in the database
        }
        return "{" + String.join(",", attribute) + "}"; // return using the following format: {value1,value2}
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank() || dbData.equals("{}")) {
            return List.of();
        }
        // Remove the keys `{}` and split the string in a list of values
        return Arrays.stream(dbData.replaceAll("[{}]", "").split(","))
            .map(String::trim) // avoiding extra spaces
            .filter(str -> !str.isEmpty()) // filtering empty values
            .toList();

    }
}

