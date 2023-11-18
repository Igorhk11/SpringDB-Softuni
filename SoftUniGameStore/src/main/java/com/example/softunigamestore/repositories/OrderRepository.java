package com.example.softunigamestore.repositories;

import com.example.softunigamestore.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
