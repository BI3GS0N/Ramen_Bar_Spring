package Project.Restaurant.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import Project.Restaurant.Model.Customer;
import Project.Restaurant.Repository.CustomerRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<Customer> customer = customerRepository.findByEmail(s);
        System.out.println(customer.get(0).getFirstName());
        if(customer.size() == 0){
            throw new UsernameNotFoundException("Nie można znaleźć klienta");
        }
        return new MyUserDetails(customer.get(0));
    }
    
}
