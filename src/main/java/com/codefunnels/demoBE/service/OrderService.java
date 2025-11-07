package com.codefunnels.demoBE.service;

import com.codefunnels.demoBE.dto.OrderRequest;
import com.codefunnels.demoBE.model.*;
import com.codefunnels.demoBE.repository.CustomerRepository;
import com.codefunnels.demoBE.repository.OrderRepository;
import com.codefunnels.demoBE.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Order placeOrder(OrderRequest request) {
        // 1. Fetch the single product (ID 1)
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        // 2. Stock Check
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for quantity: " + request.getQuantity());
        }

        // 3. Create a new Customer record or find an old one for this transaction
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    // Customer does NOT exist, create a new one
                    Customer newCustomer = new Customer();
                    newCustomer.setEmail(request.getEmail());
                    newCustomer.setFirstName(request.getFirstName());
                    newCustomer.setLastName(request.getLastName());
                    newCustomer.setStreetAddress(request.getStreetAddress());
                    newCustomer.setCity(request.getCity());
                    newCustomer.setZipCode(request.getZipCode());
                    return customerRepository.save(newCustomer);
                });

        // 4. Calculate amounts
        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        // 5. Create Order Header
        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatusEnum.PROCESSING);

        // 6. Create and Link Order Item
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setUnitPrice(unitPrice);
        orderItem.setOrder(order);

        order.setItems(List.of(orderItem));
        Order savedOrder = orderRepository.save(order);

        // 7. Update Stock (Decrement)
        product.setStockQuantity(product.getStockQuantity() - request.getQuantity());
        productRepository.save(product);

        return savedOrder;
    }

}