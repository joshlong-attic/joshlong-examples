package org.springsource.examples.crm.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchase")
public class Purchase implements java.io.Serializable {


    private Long id;
    private Customer customer;
    private double total;
    private Set<LineItem> lineItems = new HashSet<LineItem>(0);

    public Purchase() {
    }


    public Purchase(long id, Customer customer, double total) {
        this.id = id;
        this.customer = customer;
        this.total = total;
    }

    public Purchase(long id, Customer customer, double total, Set<LineItem> lineItems) {
        this.id = id;
        this.customer = customer;
        this.total = total;
        this.lineItems = lineItems;
    }


    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column(name = "total", nullable = false, precision = 17, scale = 17)
    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchase")
    public Set<LineItem> getLineItems() {
        return this.lineItems;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }


}


