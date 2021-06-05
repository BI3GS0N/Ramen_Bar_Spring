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

    // KLIENCI //////////////////////////////////////////////////////////
    
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

    // WYSWIETLENIE WSZYSTKICH KLIENTOW
    @RequestMapping(value="/customer_list", method = RequestMethod.GET)
    public String customerList(Model model, CustomerData customerData){
        try {
            List<Customer> customerList = customerRepository.findAll();
            model.addAttribute("customerList", customerList);
            return "customer_list";

        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

    //USUWANIE KLIENTOW
    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam(name = "Id")String id, Model model)
    {
        Long longId = Long.parseLong(id);

        customerRepository.deleteById(longId);
        List<Customer> customerList = customerRepository.findAll();

        model.addAttribute("customerList", customerList);
        
        return "customer_list";
    }

    //EDYTOWANIE KLIENTOW
    @RequestMapping(value ="/edit_customer", method = RequestMethod.GET)
    public String editCustomer(@RequestParam(name = "customerId")String id,
                                @RequestParam(name = "customerFirstName")String firstName,
                                @RequestParam(name = "customerLastName")String lastName,
                                @RequestParam(name = "customerPhone")String phone,
                                @RequestParam(name = "customerEmail")String email,
                                @RequestParam(name = "customerPassword")String password,
                                @RequestParam(name = "customerCity")String city,
                                @RequestParam(name = "customerAddress")String address,
                                Model model)
    {
        // Long longID = Long.parseLong(ID);
        // customerRepository.findById(longID);
        // CustomerData customerData = new CustomerData();
        // model.addAttribute("customerData", customerData);
        CustomerData customerData = new CustomerData(firstName, lastName,phone,email, password,city,address);
        model.addAttribute("customerData", customerData);
        model.addAttribute("header", "Edytuj Klienta");
        return "customer_edit";
    }

    @RequestMapping(value = "/edit_customer", method = RequestMethod.POST)
    public String editPerson(@RequestParam(name = "customerId")String id, Model model, CustomerData customerData)
    {
        try {
            Long customerId = Long.parseLong(id);
            Optional<Customer> customer = customerRepository.findById(customerId);

            String firstName = customerData.getFirstName();
            String lastName = customerData.getLastName();
            String phone = customerData.getPhone();
            String email =  customerData.getEmail();
            String city = customerData.getCity();
            String address = customerData.getAddress();

            if (customer.isPresent()){
                customer.get().setFirstName(firstName);
                customer.get().setLastName(lastName);
                customer.get().setPhone(phone);
                customer.get().setEmail(email);
                customer.get().setCity(city);
                customer.get().setAddress(address);
                customerRepository.save(customer.get());

                List<Customer> customerList = customerRepository.findAll();
                model.addAttribute("customerList", customerList);
                
                return "customer_list";
            }else{
                model.addAttribute("header", "Wynik");
                model.addAttribute("message", "Nie odnaleziono klienta o takim id");
                return "errorMessage";
            }
        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }


    //Edycja własnych danych
    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(Model model) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) user).getUsername();
        List<Customer> customerLogin = customerRepository.findByEmail(username);
        Optional<Customer> userCustomer = customerRepository.findById(customerLogin.get(0).getId());

        CustomerData customerData = new CustomerData(userCustomer.get().getFirstName(),
                userCustomer.get().getLastName(),
                userCustomer.get().getPhone(),
                userCustomer.get().getEmail(),
                userCustomer.get().getPassword(),
                userCustomer.get().getCity(),
                userCustomer.get().getAddress());
        model.addAttribute("customerData", customerData);
        model.addAttribute("header", "Edycja danych");

        return "customer_edit";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser( Model model, CustomerData customerData)
    {
        try {
            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) user).getUsername();
            List<Customer> customerLogin = customerRepository.findByEmail(username);
            Optional<Customer> customer = customerRepository.findById(customerLogin.get(0).getId());

            String firstName = customerData.getFirstName();
            String lastName = customerData.getLastName();
            String email = customerData.getEmail();
            String password = customerData.getPassword();
            String phoneNumber = customerData.getPhone();
            String city = customerData.getCity();
            String adres = customerData.getAddress();

            if (customer.isPresent()) {
                customer.get().setFirstName(firstName);
                customer.get().setLastName(lastName);
                customer.get().setEmail(email);
                customer.get().setPassword(PasswordEncoder.coding(password));
                customer.get().setPhone(phoneNumber);
                customer.get().setCity(city);
                customer.get().setAddress(adres);
                customerRepository.save(customer.get());
                model.addAttribute("header", "Wynik");
                model.addAttribute("message", "Edycja uzytkownika: " + customer.get().getEmail());
                return "errorMessage";
            } else {
                model.addAttribute("header", "Wynik");
                model.addAttribute("message", "Nie odnaleziono klienta o takim id");
                return "errorMessage";
            }
        }catch(Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... coś poszło nie tak");
            return "errorMessage";
        }
    }


}
