package com.kafka.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiderLocation {
    private int id;
    private double latitude;
    private double longitude;
}
