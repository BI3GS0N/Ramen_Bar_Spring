package Project.Restaurant.DataLoader;

import Project.Restaurant.Model.RSet;
import Project.Restaurant.Model.Product;
import Project.Restaurant.Repository.RSetRepository;
import Project.Restaurant.Repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader implements ApplicationRunner{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RSetRepository rSetRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        productRepository.save(new Product("Miso Ramen","Ramen",350,21));
        productRepository.save(new Product("Shoyu Ramen","Ramen",550,15));
        productRepository.save(new Product("Tantanmen Ramen","Ramen",450,10));
        productRepository.save(new Product("Gyudon","Glowne",200,5));
        productRepository.save(new Product("Tori Teriyaki","Glowne",10,3));
        productRepository.save(new Product("Coca Cola","Napoj",100,5));
        productRepository.save(new Product("Sake","Napoj",100,5));

        List<Product> productList = productRepository.findAll();

        RSet set1 = new RSet("MisoSet",45);
        RSet set2 = new RSet("ShoyuSet",45);
        RSet set3 = new RSet("TantanmenSet",45);
        RSet set4 = new RSet("MisoSet", 45);

        rSetRepository.save(set1);
        rSetRepository.save(set2);
        rSetRepository.save(set3);

        set1.getProduct().add(productList.get(0));
        productList.get(0).getRSet().add(set1);
        set1.getProduct().add(productList.get(3));
        productList.get(3).getRSet().add(set1);
        set1.getProduct().add(productList.get(5));
        productList.get(5).getRSet().add(set1);

        set2.getProduct().add(productList.get(1));
        productList.get(1).getRSet().add(set2);
        set2.getProduct().add(productList.get(3));
        productList.get(3).getRSet().add(set2);
        set2.getProduct().add(productList.get(5));
        productList.get(5).getRSet().add(set2);

        set3.getProduct().add(productList.get(2));
        productList.get(2).getRSet().add(set3);
        set3.getProduct().add(productList.get(3));
        productList.get(3).getRSet().add(set3);
        set3.getProduct().add(productList.get(6));
        productList.get(6).getRSet().add(set3);

        set4.getProduct().add(productList.get(0));
        productList.get(0).getRSet().add(set4);
        set4.getProduct().add(productList.get(4));
        productList.get(4).getRSet().add(set4);
        set4.getProduct().add(productList.get(5));
        productList.get(5).getRSet().add(set4);

        for(int i=0; i<productList.size(); i++) productRepository.save(productList.get(i));

        rSetRepository.save(set1);
        rSetRepository.save(set2);
        rSetRepository.save(set3);
        rSetRepository.save(set4);

    }
}
