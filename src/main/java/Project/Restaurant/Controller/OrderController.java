package Project.Restaurant.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Project.Restaurant.Model.Customer;
import Project.Restaurant.Model.DetailsSet;
import Project.Restaurant.Model.OrderItem;
import Project.Restaurant.Model.Product;
import Project.Restaurant.Model.ROrder;
import Project.Restaurant.Model.RSetData;
import Project.Restaurant.Repository.CustomerRepository;
import Project.Restaurant.Repository.OrderItemRepository;
import Project.Restaurant.Repository.ProductRepository;
import Project.Restaurant.Repository.ROrderRepository;
import Project.Restaurant.Repository.RSetRepository;

import static java.util.Objects.isNull;

@Controller
public class OrderController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    RSetRepository rSetRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ROrderRepository rOrderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    ROrder rOrder;


    @RequestMapping("/order")
    public String menu(Model model)
    { 
        try {
            List<Product> productList = productRepository.findAll();
            // Group by
            ArrayList<DetailsSet> setList = new ArrayList<>();
            for (String details : rSetRepository.findBySQLQuery()) {
                String[] setNamePrice = details.split(",");
                DetailsSet ds = new DetailsSet(setNamePrice[0], setNamePrice[1]);
                setList.add(ds);
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) user).getUsername();
            List<Customer> customerLogin = customerRepository.findByEmail(username);

            // Stworzenie Zamówienia i przypięcie do niego klienta
            List<ROrder> notFinishedOrder = rOrderRepository.findByFinishedAndCustomerId
                    (false, customerLogin.get(0).getId());
            if (notFinishedOrder.size() == 0) {
                //Przypięcie klienta do zamówienia
                customerRepository.save(customerLogin.get(0));
                //-----------------------------
                //Tworzenie nowego zamówienia
                rOrder = new ROrder(dtf.format(now), false);
                rOrder.setCustomer(customerLogin.get(0));
                System.out.println(customerLogin.get(0).getEmail());
                rOrderRepository.save(rOrder);
                //-------------------------------
                //Wprowadzenie zamówienia do klienta
                customerLogin.get(0).getOrder().add(rOrder);
                customerRepository.save(customerLogin.get(0));
                //--------------------------
            } else {
                rOrder = notFinishedOrder.get(0);
            }
            model.addAttribute("productList", productList);
            model.addAttribute("setList", setList);

            return "order";
        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak.");
            return "errorMessage";
        }
    }

    @RequestMapping(value = "/addProduct",method = RequestMethod.GET)
    public String addProductToOrder(@RequestParam(name = "productId")String id, Model model)
    {
        try{
        List<Product> productList =  productRepository.findAll();
        ArrayList<DetailsSet> setList = new ArrayList<>();
        for(String details:rSetRepository.findBySQLQuery()){
            String[] setNamePrice = details.split(",");
            DetailsSet ds = new DetailsSet(setNamePrice[0],setNamePrice[1]);
            setList.add(ds);
        }

        //Szukanie dodawanego produktu
        Long productId = Long.parseLong(id);
        Optional<Product> addProduct = productRepository.findById(productId);
        //Tworzenie wiersza z produktem
        OrderItem orderItem = new OrderItem();
        //Dodanie produktu do koszyka
        orderItem.setOrder(rOrder);
        orderItem.setProduct(addProduct.get());

        rOrder.getOrderItem().add(orderItem);
        addProduct.get().getOrderItem().add(orderItem);

        orderItemRepository.save(orderItem);
        productRepository.save(addProduct.get());
        rOrderRepository.save(rOrder);
        //------------------------------------------

            List<OrderItem> proInOrd = orderItemRepository.findByOrderId(rOrder.getId());
            int amount = 0;
            amount = proInOrd.size();
            model.addAttribute("amount", Integer.toString(amount));
        model.addAttribute("productList",productList);
        model.addAttribute("setList",setList);

        return "order";
        }catch(Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

    @RequestMapping(value = "/addSet")
    public String addMcSet(@RequestParam(name = "nameSet")String name, Model model){

            RSetData postData = new RSetData();

            String setList = name;

            //Kontener zawierający informacje o produktach w danych zestawach
            HashMap<String, ArrayList<String>> productInSet = new HashMap<String, ArrayList<String>>();
            //GETLISTPRODUCT TO WŁASAN METODA! JEST NA DOLE (usuwa dupliktaty)
            //Lista produktów które znajdują się w tym zestawie
            productInSet = getListProduct(setList, "Ramen");

            HashMap<String, ArrayList<String>> secondproductInSet = new HashMap<String, ArrayList<String>>();
            secondproductInSet = getListProduct(setList, "Glowne");

            HashMap<String, ArrayList<String>> drinkInSet = new HashMap<String, ArrayList<String>>();
            drinkInSet = getListProduct(setList, "Napoj");

            List<OrderItem> proInOrd = orderItemRepository.findByOrderId(rOrder.getId());
            int amount = 0;
            amount = proInOrd.size();
            model.addAttribute("amount", Integer.toString(amount));
            model.addAttribute("setList", setList);
            model.addAttribute("productInSet", productInSet);
            model.addAttribute("secondproductInSet", secondproductInSet);
            model.addAttribute("drinkInSet", drinkInSet);
            model.addAttribute("postData", postData);

            System.out.println("siema");
            return "selectSet";

    }

    //Metoda służąca do pobrania listy produktów w danym zestawie
    public HashMap<String, ArrayList<String>> getListProduct(String nameSets, String category){

        HashMap<String, ArrayList<String>> productInSet = new HashMap<String, ArrayList<String>>();
        ArrayList<String> mainProduct = new ArrayList<>();
            for(Product p : productRepository.findByCategoryAndRSetName(category,nameSets)){
                mainProduct.add(p.getName());
            }
            //USUWANIE DUPLIKATÓW
            Set<String> set = new HashSet<>(mainProduct);
            mainProduct.clear();
            mainProduct.addAll(set);
            //--------------------
            productInSet.put(nameSets,(ArrayList<String>) mainProduct.clone());
            mainProduct.clear();

        return productInSet;
    }

    
}
