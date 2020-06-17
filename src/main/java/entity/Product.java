package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private Integer count;
    private Double price;

    public Product(Integer id, String name, Integer count, Double price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }
}