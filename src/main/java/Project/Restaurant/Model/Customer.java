package Project.Restaurant.Model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String firstName;
    private String lastName;
    private String phone;
    private String city;
    private String address;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
    private boolean enabled;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<ROrder> order;

    protected Customer(){}

    public Customer(String firstName, String lastName, String phone, String email, String password, String city, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.city = city;
        this.address = address;
        this.role = "ROLE_USER";
        this.enabled = true;
        order = new HashSet<ROrder>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<ROrder> getOrder() {
		return order;
	}

	public void setOrder(Set<ROrder> order) {
		this.order = order;
	}
    

    

}
