package com.ecommerce.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.entities.CartItem;

public interface CartitemRepository extends JpaRepository<CartItem, Long> {
    
}
