package com.myretail.product.mapper;

import com.myretail.product.entity.ProductEntity;
import com.myretail.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by Satish Garine 6/22/2019
 */
@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "price", target = "currentPrice.value"),
            @Mapping(source = "currency", target = "currentPrice.currencyCode")
    })
    Product convertToModel(ProductEntity productEntity);
}
