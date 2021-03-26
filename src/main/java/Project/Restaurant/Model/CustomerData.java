package Project.Restaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerData {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String city;
    private String address;

}
