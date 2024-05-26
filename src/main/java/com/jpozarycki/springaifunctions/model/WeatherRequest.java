package com.jpozarycki.springaifunctions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather request")
public record WeatherRequest(@JsonProperty(required = true, value = "location")
                             @JsonPropertyDescription("The city name") String location,
                             @JsonProperty
                             @JsonPropertyDescription("The country name") String country) {
}
