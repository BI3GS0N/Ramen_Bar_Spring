package Project.Restaurant.Repository;

import Project.Restaurant.Model.OrderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // List<OrderItem> findByOrderIdAndProductNotNull(long id);
    // List<OrderItem> findByProductNotNull();
    // List<OrderItem> findByOrderIdAndMcSetNotNull(long id);
    // List<OrderItem> findByRSetNotNull();
    List<OrderItem> findByOrderId(long id);

    // @Query(value ="select OrderItem.rSet, sum(Product.rSet.price) " +
    //         "from RSet " +
    //         "group by Product.rSet", nativeQuery = true)
    // List<String> findBySQLQuery();
}
