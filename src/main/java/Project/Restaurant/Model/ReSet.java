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
public class ReSet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private double price;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> product;

    @OneToMany(mappedBy = "reSet", fetch = FetchType.EAGER)
    private Set<OrderItem> orderItem;

    protected ReSet(){}

    public ReSet(String name, double price) {
        this.name = name;
        this.price = price;
        product = new HashSet<Product>();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
    }

	public Set<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Set<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

    
}
