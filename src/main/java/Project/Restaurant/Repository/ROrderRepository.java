package Project.Restaurant.Repository;

import Project.Restaurant.Model.ROrder;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ROrderRepository extends JpaRepository<ROrder, Long> {
    // List<ROrder> findByFinishedAndCustomerId(boolean finished, long id);
    // List<ROrder> findByFinished(boolean finished);
}
