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
        productRepository.save(new Product("Shoyu Ramen","Ramen",550,22));
        productRepository.save(new Product("Tantanmen Ramen","Ramen",450,23));
        productRepository.save(new Product("Gyudon","Glowne",200,19));
        productRepository.save(new Product("Tori Teriyaki","Glowne",100,20));
        productRepository.save(new Product("Coca Cola","Napoj",100,5));
        productRepository.save(new Product("Sake","Napoj",100,5));

        List<Product> productList = productRepository.findAll();

        ReSet set1 = new ReSet("MisoSet",45);
        ReSet set2 = new ReSet("ShoyuSet",45);
        ReSet set3 = new ReSet("TantanmenSet",45);
        ReSet set4 = new ReSet("MisoSet", 45);
        ReSet set5 = new ReSet("MisoSet", 45);
        ReSet set6 = new ReSet("MisoSet", 45);
        ReSet set7 = new ReSet("ShoyuSet", 45);
        ReSet set8 = new ReSet("ShoyuSet", 45);
        ReSet set9 = new ReSet("ShoyuSet", 45);
        ReSet set10 = new ReSet("TantanmenSet", 45);

        //miso-gyudon-cola
        set1.getProduct().add(productList.get(0));
        productList.get(0).getReSet().add(set1);
        set1.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set1);
        set1.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set1);
        //shoyu-gyudon-cola
        set2.getProduct().add(productList.get(1));
        productList.get(1).getReSet().add(set2);
        set2.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set2);
        set2.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set2);
        //Tantanment-gyudon-sake
        set3.getProduct().add(productList.get(2));
        productList.get(2).getReSet().add(set3);
        set3.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set3);
        set3.getProduct().add(productList.get(6));
        productList.get(6).getReSet().add(set3);
        //miso-tori-cola
        set4.getProduct().add(productList.get(0));
        productList.get(0).getReSet().add(set4);
        set4.getProduct().add(productList.get(4));
        productList.get(4).getReSet().add(set4);
        set4.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set4);
        //miso-tori-sake
        set5.getProduct().add(productList.get(0));
        productList.get(0).getReSet().add(set5);
        set5.getProduct().add(productList.get(4));
        productList.get(4).getReSet().add(set5);
        set5.getProduct().add(productList.get(6));
        productList.get(5).getReSet().add(set5);
        //miso-gyudon-sake
        set6.getProduct().add(productList.get(0));
        productList.get(0).getReSet().add(set6);
        set6.getProduct().add(productList.get(3));
        productList.get(4).getReSet().add(set6);
        set6.getProduct().add(productList.get(6));
        productList.get(5).getReSet().add(set6);
        //shoyu-tori-cola
        set7.getProduct().add(productList.get(1));
        productList.get(1).getReSet().add(set7);
        set7.getProduct().add(productList.get(4));
        productList.get(3).getReSet().add(set7);
        set7.getProduct().add(productList.get(5));
        productList.get(5).getReSet().add(set7);
        //shoyu-tori-sake
        set8.getProduct().add(productList.get(1));
        productList.get(1).getReSet().add(set8);
        set8.getProduct().add(productList.get(4));
        productList.get(3).getReSet().add(set8);
        set8.getProduct().add(productList.get(6));
        productList.get(5).getReSet().add(set8);
        //shoyu-gyudon-sake
        set9.getProduct().add(productList.get(1));
        productList.get(1).getReSet().add(set9);
        set9.getProduct().add(productList.get(3));
        productList.get(3).getReSet().add(set9);
        set9.getProduct().add(productList.get(6));
        productList.get(5).getReSet().add(set9);
        //Tantanment-tori-sake
        set10.getProduct().add(productList.get(2));
        productList.get(2).getReSet().add(set10);
        set10.getProduct().add(productList.get(4));
        productList.get(3).getReSet().add(set10);
        set10.getProduct().add(productList.get(6));
        productList.get(6).getReSet().add(set10);

        for(int i=0; i<productList.size(); i++) productRepository.save(productList.get(i));

        reSetRepository.save(set1);
        reSetRepository.save(set2);
        reSetRepository.save(set3);
        reSetRepository.save(set4);
        reSetRepository.save(set5);
        reSetRepository.save(set6);
        reSetRepository.save(set7);
        reSetRepository.save(set8);
        reSetRepository.save(set9);
        reSetRepository.save(set10);

    }
}
