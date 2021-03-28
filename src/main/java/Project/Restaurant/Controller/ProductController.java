package Project.Restaurant.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Project.Restaurant.Repository.ProductRepository;

@Controller
public class ProductController{

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/products_list", method = RequestMethod.GET)
    public String allproduct(Model model){
        return "products_list";
    }

}