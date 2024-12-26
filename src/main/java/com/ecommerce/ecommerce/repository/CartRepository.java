package com.ecommerce.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
