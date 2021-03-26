package Project.Restaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RSetData {
    String name;
    String price;
    String mainProduct;
    String secProduct;
    String drink;

    public RSetData(String name, String price){
        this.name = name;
        this.price = price;
    }
    
}
