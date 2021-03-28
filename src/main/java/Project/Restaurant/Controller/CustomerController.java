package Project.Restaurant.Controller;

import Project.Restaurant.Login.PasswordEncoder;
import Project.Restaurant.Model.Customer;
import Project.Restaurant.Model.CustomerData;
import Project.Restaurant.Repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class CustomerController {
    
    @Autowired
    CustomerRepository customerRepository;

    // DODAWANIE KLIENTOW
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String addCustomer(Model model){

        CustomerData customerData = new CustomerData();
        model.addAttribute("header", "Rejestracja");
        model.addAttribute("customerData", customerData);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addCustomer(Model model, CustomerData customerData){
        
        try{
            String fName = customerData.getFirstName();
            String sName = customerData.getLastName();
            String phone = customerData.getPhone();
            String email = customerData.getEmail();
            String city = customerData.getCity();
            String address = customerData.getAddress();
            String password = PasswordEncoder.coding(customerData.getPassword());

            customerRepository.save(new Customer(fName,sName,phone,email, password,city,address));

            List<Customer> customerList = customerRepository.findAll();
            model.addAttribute("index", customerList);

            return "index";
        }catch(Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

}
