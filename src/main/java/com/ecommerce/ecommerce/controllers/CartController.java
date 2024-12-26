package com.ecommerce.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.payload.ApiResponse;
import com.ecommerce.ecommerce.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/{cartId}/add/{productId}/{quantity}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable("cartId") Long cartId, @PathVariable Long productId,
            @PathVariable int quantity) {
        return cartService.addItemToCart(cartId, productId, quantity);
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable("cartId") Long cartId,
            @PathVariable int productId) {
        return cartService.removeItemFromCart(cartId, productId);
    }

    @PutMapping("/{cartId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable("cartId") Long cartId,
            @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.updateItemQuantity(cartId, productId, quantity);
    }

    @GetMapping("/{cartId}/total")
    public Double getCartTotal(@PathVariable("cartId") Long cartId) {
        return cartService.calculateCartTotal(cartId);
    }
}
