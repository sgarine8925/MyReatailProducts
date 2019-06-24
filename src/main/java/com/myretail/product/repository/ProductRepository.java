package com.myretail.product.repository;

import com.myretail.product.entity.ProductEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Satish Garine
 */
public interface ProductRepository extends CassandraRepository<ProductEntity, Integer> {
    ProductEntity findById(@Param("id") int id);
}
