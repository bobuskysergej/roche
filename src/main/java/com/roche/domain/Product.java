package com.roche.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@EqualsAndHashCode
@Builder
@Getter
@Entity
public class Product {

    @Id
    @Column
    @NonNull
    private String sku;
    @Column
    @NonNull
    private String name;
    @Column
    @NonNull
    private Double price;
    @Column
    @NonNull
    private Instant created;

}
