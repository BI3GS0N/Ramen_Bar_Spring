package Project.Restaurant.Repository;

import Project.Restaurant.Model.OrderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderIdAndProductNotNull(long id);
    List<OrderItem> findByProductNotNull();
    List<OrderItem> findByOrderIdAndReSetNotNull(long id);
    List<OrderItem> findByReSetNotNull();
    List<OrderItem> findByOrderId(long id);

    @Query(value ="select OrderItem.reSet, sum(Product.reSet.price) " +
            "from ReSet " +
            "group by Product.reSet", nativeQuery = true)
    List<String> findBySQLQuery();
}
