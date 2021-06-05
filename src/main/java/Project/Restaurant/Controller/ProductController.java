package Project.Restaurant.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import Project.Restaurant.Model.Product;
import Project.Restaurant.Model.ProductData;
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

    //USUWANIE PRODUKTU
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam(name = "Id")String id, Model model)
    {
        try {
            Long longId = Long.parseLong(id);
        productRepository.deleteById(longId);
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        
        return "products_list";
            
        } catch (Exception e) {
            model.addAttribute("header", "Błąd");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
        
    }

    //EDYTOWANIE PRODUKTU
    @RequestMapping(value ="/editProduct", method = RequestMethod.GET)
    public String editProduct(@RequestParam(name = "productId")String id,
                                @RequestParam(name = "productName")String name,
                                @RequestParam(name = "productCategory")String category,
                                @RequestParam(name = "productCalories")String calories,
                                @RequestParam(name = "productPrice")String price,
                                Model model)
    {
        ProductData productData = new ProductData(name, category, calories, price);
        model.addAttribute("productData", productData);
        model.addAttribute("header", "Edytuj Produkt");

        return "product_edit";
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editProduct(@RequestParam(name = "productId")String id, Model model, ProductData productData)
    {
        try {
            Long productId = Long.parseLong(id);
            Optional<Product> product = productRepository.findById(productId);

            String name = productData.getName();
            String category = productData.getCategory();
            int calories = Integer.parseInt(productData.getCalories());
            double price = Double.parseDouble(productData.getPrice());

            List<Product> productList = productRepository.findAll();
            model.addAttribute("productList", productList);

            if(product.isPresent()) {
                product.get().setName(name);
                product.get().setCategory(category);
                product.get().setCalories(calories);
                product.get().setPrice(price);
                productRepository.save(product.get());

                return "products_list";
            } else {
                model.addAttribute("header", "Wynik");
                model.addAttribute("message", "Nie odnaleziono produktu o takim id");
                return "errorMessage";
            }
        }catch (Exception e){
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

      //DODAWANIE PRODUKTÓW
      @RequestMapping(value = "/add_product", method = RequestMethod.GET)
      public String addProduct(Model model){
  
          ProductData productData = new ProductData();
          model.addAttribute("header", "Dodaj Produkt");
          model.addAttribute("productData", productData);
          return "product_edit";
  
      }
  
      @RequestMapping(value = "/add_product", method = RequestMethod.POST)
      public String addProduct(Model model, ProductData productData){
  
          String name = productData.getName();
          String category = productData.getCategory();
          int calories = Integer.parseInt(productData.getCalories());
          Double price = Double.parseDouble(productData.getPrice());
  
          productRepository.save(new Product(name,category,calories,price));
  
          List<Product> productList = productRepository.findAll();
          model.addAttribute("productList", productList);
          
          return "products_list";
  
      }

}