package Project.Restaurant.Repository;

import Project.Restaurant.Model.RSet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RSetRepository extends JpaRepository<RSet, Long> {

    List<RSet> findByOrderItemOrderId(long id);

    @Query(value ="select rSet.name,rSet.price " +
            "from rSet " +
            "group by rSet.name,rSet.price", nativeQuery = true)
    List<String> findBySQLQuery();

    @Query(value ="select s.product from RSet", nativeQuery = true)
    List<String> findRSet();

    List<RSet> findByProductName(String productName);
    
}
