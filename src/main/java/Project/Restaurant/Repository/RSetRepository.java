package Project.Restaurant.Repository;

import Project.Restaurant.Model.RSet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RSetRepository extends JpaRepository<RSet, Long> {

    List<RSet> findByOrderItemOrderId(long id);

    @Query(value ="select R_Set.name,R_Set.price " +
            "from R_Set " +
            "group by R_Set.name,R_Set.price", nativeQuery = true)
    List<String> findBySQLQuery();

    @Query(value ="select s.product from RSet", nativeQuery = true)
    List<String> findRSet();

    List<RSet> findByProductName(String productName);
    
}
