package Project.Restaurant.Repository;

import Project.Restaurant.Model.Product;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // List<Product> findByRSetName(String name);
    // List<Product> findByRSetId(long id);
    // List<Product> findByCategory(String name);
    // List<Product> findByCategoryAndRSetName(String name,String nameSet);
    // List<Product> findByOrderItemOrderId(long id);
    // List<Product> findByName(String name);
    
}
