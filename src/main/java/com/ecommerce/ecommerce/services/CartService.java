package com.ecommerce.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.entities.CartItem;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.payload.ApiResponse;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.CartitemRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CartitemRepository cartitemRepository;

    public ResponseEntity<ApiResponse> addItemToCart(Long cartId, Long productId, int quantity) {
        Product product = productRepository.findById(quantity)
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        CartItem existingItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(quantity);
            existingItem.setTotalPrice(product.getPrice() * quantity);
            cartitemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setTotalPrice(product.getPrice() * quantity);

            cart.getItems().add(newItem);
            cartitemRepository.save(newItem);
        }
        Cart cart2 = cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(true, "Cart created", cart2));
    }

    public ResponseEntity<ApiResponse> removeItemFromCart(Long cartId, int productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem itemToRemove = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));
        cart.getItems().remove(itemToRemove);
        cartitemRepository.delete(itemToRemove);

        cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(true, "Product removed"));
    }

    public ResponseEntity<ApiResponse> updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem itemToUpdate = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in the cart"));

        itemToUpdate.setQuantity(quantity);
        itemToUpdate.setTotalPrice(itemToUpdate.getProduct().getPrice() * quantity);
        cartitemRepository.save(itemToUpdate);
        Cart cart2 = cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(true, "Cart item updated", cart2));
    }

    public Double calculateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        return cart.getItems().stream().mapToDouble(cartItem -> cartItem.getTotalPrice()).sum();
    }
}
