package library.libraryManager.service;

import library.libraryManager.dto.BookDTO;
import library.libraryManager.entity.Book;
import library.libraryManager.entity.Order;
import library.libraryManager.repository.BookRepository;
import library.libraryManager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders=orderRepository.findAll();
        return orders.stream().collect(Collectors.toList());

    }

}
