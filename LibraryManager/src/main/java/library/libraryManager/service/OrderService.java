package library.libraryManager.service;

import library.libraryManager.dto.BookDTO;
import library.libraryManager.entity.Order;

import java.util.List;

public interface OrderService {

    void saveOrder(Order order);
    void deleteOrderById(Long id);
    Order findOrderById(Long id);

    List<Order> findAllOrders();
}
