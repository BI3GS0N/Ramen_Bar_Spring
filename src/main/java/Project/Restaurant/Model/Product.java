package Project.Restaurant.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String category;
    private int calories;
    private double price;
    
    @ManyToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<RSet> rSet;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<OrderItem> orderItem;

    protected Product(){}

    public Product(String name, String category, int calories, double price) {
        this.name = name;
        this.category = category;
        this.calories = calories;
        this.price = price;
        rSet = new HashSet<RSet>();
        orderItem = new HashSet<OrderItem>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	public Set<RSet> getRSet() {
		return rSet;
	}

	public void setRSet(Set<RSet> rSet) {
		this.rSet = rSet;
	}

	public Set<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Set<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

    

}
