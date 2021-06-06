package Project.Restaurant.DataLoader;

import Project.Restaurant.Model.ReSet;
import Project.Restaurant.Model.Product;
import Project.Restaurant.Repository.ReSetRepository;
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
    private ReSetRepository reSetRepository;

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

        ReSet set1 = new ReSet("MisoSet",45);
        ReSet set2 = new ReSet("ShoyuSet",45);
        ReSet set3 = new ReSet("TantanmenSet",45);
        ReSet set4 = new ReSet("MisoSet", 45);

        reSetRepository.save(set1);
        reSetRepository.save(set2);
        reSetRepository.save(set3);

        set1.getProduct().add(productList.get(0));
        productList.get(0).getReSet().add(set1);
        set1.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set1);
        set1.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set1);

        set2.getProduct().add(productList.get(1));
        productList.get(1).getReSet().add(set2);
        set2.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set2);
        set2.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set2);

        set3.getProduct().add(productList.get(2));
        productList.get(2).getReSet().add(set3);
        set3.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set3);
        set3.getProduct().add(productList.get(6));
        productList.get(6).getReSet().add(set3);

        set4.getProduct().add(productList.get(0));
        productList.get(0).getReSet().add(set4);
        set4.getProduct().add(productList.get(4));
        productList.get(4).getReSet().add(set4);
        set4.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set4);

        for(int i=0; i<productList.size(); i++) productRepository.save(productList.get(i));

        reSetRepository.save(set1);
        reSetRepository.save(set2);
        reSetRepository.save(set3);
        reSetRepository.save(set4);

    }
}
