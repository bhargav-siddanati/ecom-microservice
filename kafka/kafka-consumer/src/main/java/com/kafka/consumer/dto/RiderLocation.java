package com.kafka.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderLocation {
    private int id;
    private double latitude;
    private double longitude;
}*/

public record RiderLocation(int id, double latitude, double longitude) {
}
