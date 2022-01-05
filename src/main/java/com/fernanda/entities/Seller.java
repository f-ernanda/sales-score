package com.fernanda.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Seller extends PanacheEntity {

    public String name;

}
