package Project.Restaurant.DataLoader;

// import Project.Restaurant.Model.RSet;
import Project.Restaurant.Model.Product;
// import Project.Restaurant.Repository.RSetRepository;
import Project.Restaurant.Repository.ProductRepository;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader implements ApplicationRunner{
    @Autowired
    private ProductRepository productRepository;
    // @Autowired
    // private RSetRepository rSetRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        productRepository.save(new Product("McChicken","Główne",350,10));
        productRepository.save(new Product("Big Mac","Główne",650,15));
        productRepository.save(new Product("McDouble","Główne",400,10));
        productRepository.save(new Product("Frytki","Dodatek",200,5));
        productRepository.save(new Product("Jabłko","Dodatek",10,3));
        productRepository.save(new Product("Coca Cola","Napój",100,5));
        productRepository.save(new Product("Sprite","Napój",100,5));

        // List<Product> productList = productRepository.findAll();

        // McSet set1 = new McSet("3ForU Chicken",20);
        // McSet set2 = new McSet("3ForU Big",20);
        // McSet set3 = new McSet("3ForU Double",20);
        // McSet set4 = new McSet("3ForU Chicken", 20);

        // mcSetRepository.save(set1);
        // mcSetRepository.save(set2);
        // mcSetRepository.save(set3);

        // set1.getProduct().add(productList.get(0));
        // productList.get(0).getMcSet().add(set1);
        // set1.getProduct().add(productList.get(3));
        // productList.get(3).getMcSet().add(set1);
        // set1.getProduct().add(productList.get(5));
        // productList.get(5).getMcSet().add(set1);

        // set2.getProduct().add(productList.get(1));
        // productList.get(1).getMcSet().add(set2);
        // set2.getProduct().add(productList.get(3));
        // productList.get(3).getMcSet().add(set2);
        // set2.getProduct().add(productList.get(5));
        // productList.get(5).getMcSet().add(set2);

        // set3.getProduct().add(productList.get(2));
        // productList.get(2).getMcSet().add(set3);
        // set3.getProduct().add(productList.get(3));
        // productList.get(3).getMcSet().add(set3);
        // set3.getProduct().add(productList.get(6));
        // productList.get(6).getMcSet().add(set3);

        // set4.getProduct().add(productList.get(0));
        // productList.get(0).getMcSet().add(set4);
        // set4.getProduct().add(productList.get(4));
        // productList.get(4).getMcSet().add(set4);
        // set4.getProduct().add(productList.get(5));
        // productList.get(5).getMcSet().add(set4);

        // for(int i=0; i<productList.size(); i++) productRepository.save(productList.get(i));

        // mcSetRepository.save(set1);
        // mcSetRepository.save(set2);
        // mcSetRepository.save(set3);
        // mcSetRepository.save(set4);

    }
}
