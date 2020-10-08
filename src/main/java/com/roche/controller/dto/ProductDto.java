package com.roche.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ProductDto {

    private String id;

    private String name;

    private Double price;

    private Instant created;
}
