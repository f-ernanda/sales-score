package com.fernanda.dto;

import com.fernanda.entities.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerDTO {

    private String name;

    public SellerDTO(Seller seller) {
        this.name = seller.getName();
    }
}
