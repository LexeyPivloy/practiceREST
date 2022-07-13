package ru.pavlov.practiceREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavlov.practiceREST.models.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
