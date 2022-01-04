package com.fernanda.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

}
