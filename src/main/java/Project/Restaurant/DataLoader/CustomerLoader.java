package Project.Restaurant.DataLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import Project.Restaurant.Login.PasswordEncoder;
import Project.Restaurant.Model.Customer;
import Project.Restaurant.Repository.CustomerRepository;

@Component
public class CustomerLoader implements ApplicationRunner{
    private CustomerRepository customerRepository;

    @Autowired
	public CustomerLoader(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Customer customer = new Customer("Sylwester","Biega","123456657",
											"biega@gmail.com", PasswordEncoder.coding("haslo"),
											"Liszna","69");
										customer.setRole("ROLE_ADMIN");
										customerRepository.save(customer);

		customerRepository.save(new Customer("Bartosz","Siudak","654321565",
											"siudak@gmail.com", PasswordEncoder.coding("haslo"),
											"Zarszyn","Krakowska 1"));
											

		customerRepository.save(new Customer("Piotr","Toropolski","152346533",
											"toropolski@gmail.com", PasswordEncoder.coding("haslo"),
											"Kraków","Jana Pawła II"));


	}

}
