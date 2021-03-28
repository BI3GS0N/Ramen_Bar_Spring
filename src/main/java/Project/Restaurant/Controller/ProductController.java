package Project.Restaurant.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Project.Restaurant.Model.Product;
import Project.Restaurant.Repository.ProductRepository;

@Controller
public class ProductController{

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/products_list", method = RequestMethod.GET)
    public String allproduct(Model model)
    {
        try {
            List<Product> productList = productRepository.findAll();
            model.addAttribute("productList", productList);
        return "products_list";
        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
        
        
    }

}