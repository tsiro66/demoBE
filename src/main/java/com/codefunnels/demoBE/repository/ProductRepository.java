package com.codefunnels.demoBE.repository;

import com.codefunnels.demoBE.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
