package com.myserver.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveProductDTO {
    private String name;
    private Integer count;
    private Double price;
}
