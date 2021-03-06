package Project.Restaurant.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    // private int quantity;

    @ManyToOne
    private ROrder order;

    @ManyToOne
    private Product product;

    @ManyToOne
    private ReSet reSet;

    // protected OrderItem(){}

    public OrderItem() {
        order = null;
        product = null;
        reSet = null;
        // this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public ROrder getOrder() {
		return order;
	}

	public void setOrder(ROrder order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ReSet getReSet() {
		return reSet;
	}

	public void setReSet(ReSet reSet) {
		this.reSet = reSet;
	}

    // public int getQuantity() {
    //     return quantity;
    // }

    // public void setQuantity(int quantity) {
    //     this.quantity = quantity;
    // }

    
}
