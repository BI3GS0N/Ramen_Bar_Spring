package Project.Restaurant.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Project.Restaurant.Model.Customer;
import Project.Restaurant.Model.CustomerData;
import Project.Restaurant.Model.DetailsSet;
import Project.Restaurant.Model.OrderItem;
import Project.Restaurant.Model.Product;
import Project.Restaurant.Model.ROrder;
import Project.Restaurant.Model.ReSet;
import Project.Restaurant.Model.ReSetData;
import Project.Restaurant.Repository.CustomerRepository;
import Project.Restaurant.Repository.OrderItemRepository;
import Project.Restaurant.Repository.ProductRepository;
import Project.Restaurant.Repository.ROrderRepository;
import Project.Restaurant.Repository.ReSetRepository;

import static java.util.Objects.isNull;

@Controller
public class OrderController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReSetRepository rSetRepository;
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

            ReSetData postData = new ReSetData();

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

    @RequestMapping(value = "/addSet", method = RequestMethod.POST)
    public String addMcSet(ReSetData postData,Model model)
    {
        try{
        List<Product> productList =  productRepository.findAll();
        ArrayList<DetailsSet> setList = new ArrayList<>();
        for(String details:rSetRepository.findBySQLQuery()){
            String[] setNamePrice = details.split(",");
            DetailsSet ds = new DetailsSet(setNamePrice[0],setNamePrice[1]);
            setList.add(ds);
        }

        //-------------------------WYSZUKANIE ODPOWIEDNIEGO ZESTAWU ----------------------------
        //WYSZUKIWANIE PRODUKTÓW W DANYCH ZESTAWACH
        List<ReSet> setList_Main = rSetRepository.findByProductName(postData.getMainProduct());
        ArrayList<Long> setMain_id = new ArrayList<Long>();
        for(ReSet re:setList_Main){
            setMain_id.add(re.getId());
        }

        List<ReSet> setList_Second = rSetRepository.findByProductName(postData.getSecProduct());
        ArrayList<Long> setSecond_id = new ArrayList<Long>();
        for(ReSet re:setList_Second){
            setSecond_id.add(re.getId());
        }

        List<ReSet> setList_Drink = rSetRepository.findByProductName(postData.getDrink());
        ArrayList<Long> setDrink_id = new ArrayList<Long>();
        for(ReSet re:setList_Drink){
            setDrink_id.add(re.getId());
        }
        //WYSZUKANIE NASZEGO ZESTAWU
        setMain_id.retainAll(setSecond_id);
        setMain_id.retainAll(setDrink_id);

        //POBRANIE ZESTAWU
        Optional<ReSet> addSet = rSetRepository.findById(setMain_id.get(0));
        System.out.println(addSet.get().getName()+" "+setMain_id);

        //Tworzenie wiersza z produktem
        OrderItem productInOrder = new OrderItem();
        //Dodanie produktu do koszyka
        productInOrder.setOrder(rOrder);
        productInOrder.setReSet(addSet.get());

        rOrder.getOrderItem().add(productInOrder);
        addSet.get().getOrderItem().add(productInOrder);

        orderItemRepository.save(productInOrder);
        rSetRepository.save(addSet .get());
        rOrderRepository.save(rOrder);

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

    @RequestMapping(value = "/basket",method = RequestMethod.GET)
    public String basket( Model model)
    {
        try{
            //Sprawdzanie użytkowanika
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            //Jeśli użytkownik przyciśnie koszyk tworzy się nowe zamówienie
            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) user).getUsername();
            List<Customer> customerLogin = customerRepository.findByEmail(username);
            List<ROrder> notFinishedOrder = rOrderRepository.findByFinishedAndCustomerId
                    (false,customerLogin.get(0).getId());

            if(notFinishedOrder.size() == 0){
                //Tworzenie klienta do testów
                customerRepository.save(customerLogin.get(0));
                //-----------------------------
                //Tworzenie nowego zamówienia
                rOrder = new ROrder(dtf.format(now),false);
                rOrder.setCustomer(customerLogin.get(0));
                rOrderRepository.save(rOrder);
                //-------------------------------
                //Wprowadzenie zamówienia do klienta
                customerLogin.get(0).getOrder().add(rOrder);
                customerRepository.save(customerLogin.get(0));
                //--------------------------
            }else{
                rOrder = notFinishedOrder.get(0);
            }
            //--------------------------------------------------------
            if(isNull(rOrder)){
                model.addAttribute("header", "Koszyk jest pusty");
                return "errorMessage";
            }else{
            List<OrderItem> productsInOrder = orderItemRepository.
                                                    findByOrderIdAndProductNotNull(rOrder.getId());
            List<OrderItem> setsInOrder = orderItemRepository.
                                                    findByOrderIdAndReSetNotNull(rOrder.getId());

            if(productsInOrder.size()==0 & setsInOrder.size()==0){
                model.addAttribute("header", "Koszyk jest pusty");
                return "errorMessage";
            }else{
                double priceFinal = 0;
                for(OrderItem pInO : productsInOrder) priceFinal += pInO.getProduct().getPrice();
                for(OrderItem sInO : setsInOrder) priceFinal += sInO.getReSet().getPrice();

                //Ilość produktów w koszyku
                List<OrderItem> proInOrd = orderItemRepository.findByOrderId(rOrder.getId());
                int amount = 0;
                amount = proInOrd.size();
                //--------------------
                model.addAttribute("amount", Integer.toString(amount));
                model.addAttribute("header","Lista wszystkich produktow"); //Dodanie obiektu do pamieci lokalnej modelu
                model.addAttribute("priceFinal",Double.toString(priceFinal)); //Dodanie obiektu do pamieci lokalnej modelu
                model.addAttribute("productsInOrder",productsInOrder );
                model.addAttribute("setsInOrder",setsInOrder );

                return "basket";
                }
            }
        }catch(Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(name = "productId")String id, Model model) {

        try{
        Long longId = Long.parseLong(id);

        orderItemRepository.deleteById(longId);

        List<OrderItem> productsInOrder = orderItemRepository.findByOrderIdAndProductNotNull(rOrder.getId());
        List<OrderItem> setsInOrder = orderItemRepository.findByOrderIdAndReSetNotNull(rOrder.getId());

        if(productsInOrder.isEmpty() & setsInOrder.isEmpty()){
            model.addAttribute("header", "Koszyk jest pusty");
            return "errorMessage";
        }else{
            double priceFinal = 0;
            for(OrderItem pInO : productsInOrder) priceFinal += pInO.getProduct().getPrice();
            for(OrderItem sInO : setsInOrder) priceFinal += sInO.getReSet().getPrice();

            List<OrderItem> proInOrd = orderItemRepository.findByOrderId(rOrder.getId());
            int amount = 0;
            amount = proInOrd.size();
            model.addAttribute("amount", Integer.toString(amount));

            model.addAttribute("header","Lista wszystkich produktow"); //Dodanie obiektu do pamieci lokalnej modelu
            model.addAttribute("priceFinal",Double.toString(priceFinal));
            model.addAttribute("productsInOrder",productsInOrder );
            model.addAttribute("setsInOrder",setsInOrder );
            return "basket";
        }
        }catch(Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

    @RequestMapping(value = "/accept")
    public String accept(Model model)
    {
        try{
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) user).getUsername();
        List<Customer> customerLogin = customerRepository.findByEmail(username);

        CustomerData customerData = new CustomerData(customerLogin.get(0).getFirstName(), customerLogin.get(0).getLastName(),
                                                    customerLogin.get(0).getEmail(),customerLogin.get(0).getPassword(),
                                                    customerLogin.get(0).getPhone(),
                                                    customerLogin.get(0).getCity(),customerLogin.get(0).getAddress());
        // model.addAttribute("customerData", customerData);
        List<Customer> customer = customerRepository.findByEmail(username);
        List<ROrder> notFinishedOrder = rOrderRepository.findByFinishedAndCustomerId
                    (false,customer.get(0).getId());
        rOrder = notFinishedOrder.get(0);

        String phoneNumber = customerData.getPhone();
        System.out.println("telefon "+phoneNumber);
        String city = customerData.getCity();
        String adres = customerData.getAddress();

        rOrder.setPhone(phoneNumber);
        rOrder.setCity(city);
        rOrder.setAddress(adres);
        rOrder.setFinished(true);
        rOrderRepository.save(rOrder);

        model.addAttribute("header", "Zrealizowano operacje");
        model.addAttribute("message","Zamówienie " + rOrder.getId() + " zostało zrealizowane");
        return "errorMessage";

        }catch(Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

    @RequestMapping(value = "/printOrders",method = RequestMethod.GET)
    public String printOrders(Model model)
    {
        try {
            List<ROrder> listOrder = rOrderRepository.findAll();
            List<ROrder> listOrder_amountOrderNotFinish = rOrderRepository.findByFinished(false);
            int amountOrderNotFinish = listOrder_amountOrderNotFinish.size();

            if (listOrder.isEmpty()) {
                model.addAttribute("header", "Pusto");
                model.addAttribute("message", "Nie zrobiono żadnych zamówień");
                return "errorMessage";
            } else {
                model.addAttribute("amountOrderNotFinish", Integer.toString(amountOrderNotFinish));
                model.addAttribute("header", "Lista wszystkich produktow"); //Dodanie obiektu do pamieci lokalnej modelu
                model.addAttribute("listOrder", listOrder);
                return "list_order";
            }
        }catch (Exception e){
            model.addAttribute("header", "Ups...");
            model.addAttribute("message", "Coś poszło nie tak");
            return "errorMessage";
        }
    }


    //Metoda służąca do pobrania listy produktów w danym zestawie
    public HashMap<String, ArrayList<String>> getListProduct(String nameSets, String category){

        HashMap<String, ArrayList<String>> productInSet = new HashMap<String, ArrayList<String>>();
        ArrayList<String> mainProduct = new ArrayList<>();
            for(Product p : productRepository.findByCategoryAndReSetName(category,nameSets)){
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
