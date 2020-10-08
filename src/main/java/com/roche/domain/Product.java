package com.roche.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
