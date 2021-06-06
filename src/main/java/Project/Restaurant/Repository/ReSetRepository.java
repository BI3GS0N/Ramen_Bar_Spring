package Project.Restaurant.Repository;

import Project.Restaurant.Model.ReSet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReSetRepository extends JpaRepository<ReSet, Long> {

    List<ReSet> findByOrderItemOrderId(long id);

    @Query(value ="select re_Set.name,re_Set.price " +
            "from re_Set " +
            "group by re_Set.name,re_Set.price", nativeQuery = true)
    List<String> findBySQLQuery();

    @Query(value ="select s.product from Re_Set", nativeQuery = true)
    List<String> findReSet();

    List<ReSet> findByProductName(String productName);
    
}
