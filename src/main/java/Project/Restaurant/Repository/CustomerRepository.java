package Project.Restaurant.Repository;

import Project.Restaurant.Model.Customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByEmail(String email);
    List<Customer> findByRole(String role);
}
