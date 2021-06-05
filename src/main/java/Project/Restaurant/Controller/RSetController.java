package Project.Restaurant.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Project.Restaurant.Model.Product;
import Project.Restaurant.Model.RSet;
import Project.Restaurant.Model.RSetData;
import Project.Restaurant.Repository.ProductRepository;
import Project.Restaurant.Repository.RSetRepository;

@Controller
public class RSetController {

    @Autowired
    RSetRepository rSetRepository;
    @Autowired
    ProductRepository productRepository;

    //WYSWIETLANIE LISTY ZESTAWOW
    @RequestMapping(value = "/set_list", method = RequestMethod.GET)
    public String mcSetList(Model model){
        try {

            List<RSet> setList = rSetRepository.findAll();
            model.addAttribute("setList", setList);
            
            return "set_list";
            
        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak");
            return "errorMessage";
        }
    }

    //USUWANIE ZESTAWU
    @RequestMapping(value = "/deleteSet", method = RequestMethod.GET)
    public String deleteMcSet(@RequestParam(name = "Id")String id, Model model)
    {
        Long longId = Long.parseLong(id);
        rSetRepository.deleteById(longId);
        List<RSet> setList = rSetRepository.findAll();
        model.addAttribute("setList", setList);

        return "set_list";
    }

    //DODAWANIE ZESTAWU
    @RequestMapping(value = "/add_set", method = RequestMethod.GET)
    public String addMcSet(Model model)
    {
        RSetData rSetData = new RSetData();
        //glownie dania
        List<Product> productsMainList = productRepository.findByCategory("Ramen");
        ArrayList<String> productsMain = new ArrayList<String>();
        for(Product p : productsMainList) productsMain.add(p.getName());
        //dodatek
        List<Product> productsSecList = productRepository.findByCategory("Glowne");
        ArrayList<String> productsSec = new ArrayList<String>();
        for(Product p : productsSecList) productsSec.add(p.getName());
        //napoj
        List<Product> productsDrinkList = productRepository.findByCategory("Napoj");
        ArrayList<String> productsDrink = new ArrayList<String>();
        for(Product p : productsDrinkList) productsDrink.add(p.getName());

        model.addAttribute("rSetData", rSetData);
        model.addAttribute("productsMain", productsMain);
        model.addAttribute("productsSec", productsSec);
        model.addAttribute("productsDrink", productsDrink);
        model.addAttribute("header", "Dodaj Zestaw");

        return "set_edit";
    }

    @RequestMapping(value = "/add_set", method = RequestMethod.POST)
    public String addMcSet(Model model, RSetData rSetData)
    {
        try {
            String name = rSetData.getName();
            double price = Double.parseDouble(rSetData.getPrice());

            String mainProd = rSetData.getMainProduct();
            String secProd = rSetData.getSecProduct();
            String drink = rSetData.getDrink();

            RSet rSet = new RSet(name, price);

            List<Product> mProd = productRepository.findByName(mainProd);
            List<Product> sProd = productRepository.findByName(secProd);
            List<Product> dProd = productRepository.findByName(drink);

            rSet.getProduct().add(mProd.get(0));
            rSet.getProduct().add(sProd.get(0));
            rSet.getProduct().add(dProd.get(0));
            mProd.get(0).getRSet().add(rSet);
            sProd.get(0).getRSet().add(rSet);
            dProd.get(0).getRSet().add(rSet);

            rSetRepository.save(rSet);
            productRepository.save(mProd.get(0));
            productRepository.save(sProd.get(0));
            productRepository.save(dProd.get(0));

            List<RSet> setList = rSetRepository.findAll();
            model.addAttribute("setList", setList);
            return "set_list";
        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak podczas dodawania");
            return "errorMessage";
        }
    }

    //EDYTOWANIE ZESTAWU
    @RequestMapping(value = "/edit_set", method = RequestMethod.GET)
    public String editSet(@RequestParam(name = "setId")String id, 
                            @RequestParam(name = "setName")String name,
                            @RequestParam(name = "setPrice")String price, 
                            Model model)
    {
        RSetData rSetData = new RSetData(name, price);
        List<Product> productsMainList = productRepository.findByCategory("Ramen");
        ArrayList<String> productsMain = new ArrayList<String>();
        for(Product p : productsMainList) productsMain.add(p.getName());
        //dodatek
        List<Product> productsSecList = productRepository.findByCategory("Glowne");
        ArrayList<String> productsSec = new ArrayList<String>();
        for(Product p : productsSecList) productsSec.add(p.getName());
        //napoj
        List<Product> productsDrinkList = productRepository.findByCategory("Napoj");
        ArrayList<String> productsDrink = new ArrayList<String>();
        for(Product p : productsDrinkList) productsDrink.add(p.getName());

        model.addAttribute("rSetData", rSetData);
        model.addAttribute("productsMain", productsMain);
        model.addAttribute("productsSec", productsSec);
        model.addAttribute("productsDrink", productsDrink);
        model.addAttribute("header", "Edytuj Zestaw");

        return "set_edit";
    }

    @RequestMapping(value = "/edit_set", method = RequestMethod.POST)
    public String editSet(@RequestParam(name = "setId")String id, Model model, RSetData rSetData)
    {
        try {
            Long setId = Long.parseLong(id);
            Optional<RSet> mcSet = rSetRepository.findById(setId);

            if(mcSet.isPresent()){
            String name = rSetData.getName();
            Double price = Double.parseDouble(rSetData.getPrice());

            String mainProd = rSetData.getMainProduct();
            String secProd = rSetData.getSecProduct();
            String drink = rSetData.getDrink();

            List<Product> mProd = productRepository.findByName(mainProd);
            List<Product> sProd = productRepository.findByName(secProd);
            List<Product> dProd = productRepository.findByName(drink);

            mcSet.get().setName(name);
            mcSet.get().setPrice(price);

            mcSet.get().getProduct().clear();
            mcSet.get().getProduct().add(mProd.get(0));
            mcSet.get().getProduct().add(sProd.get(0));
            mcSet.get().getProduct().add(dProd.get(0));
            mProd.get(0).getRSet().add(mcSet.get());
            sProd.get(0).getRSet().add(mcSet.get());
            dProd.get(0).getRSet().add(mcSet.get());

            rSetRepository.save(mcSet.get());
            productRepository.save(mProd.get(0));
            productRepository.save(sProd.get(0));
            productRepository.save(dProd.get(0));

            List<RSet> setList = rSetRepository.findAll();
            model.addAttribute("setList", setList);

            return "set_list"; 

            }else {
                model.addAttribute("header", "Wynik");
                model.addAttribute("message", "Nie znaleziono takiego produktu");
                return "viewmessage";
            }
        } catch (Exception e) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Ups... Coś poszło nie tak podczas edtowania");
            return "errorMessage";
        }
    }
    
}
