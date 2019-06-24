package com.myretail.product.entity;

/**
 * Created by Satish Garine 6/22/2019
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Data
@ToString
@EqualsAndHashCode
@Table("Product")
public class ProductEntity {

    @PrimaryKey("id")
    @Column(value = "id")
    private Integer id;

    @Column(value = "price")
    private BigDecimal price;

    @Column(value = "currency")
    private String currency;

}
