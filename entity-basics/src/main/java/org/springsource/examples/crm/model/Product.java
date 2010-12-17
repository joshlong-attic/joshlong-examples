package org.springsource.examples.crm.model;
// Generated Dec 16, 2010 1:06:09 PM by Hibernate Tools 3.2.0.CR1


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {


    private Long id;
    private String description;
    private String name;
    private double price;
    private Set<LineItem> lineItems = new HashSet<LineItem>(0);

    public Product() {
    }


    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(long id, String description, String name, double price, Set<LineItem> lineItems) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
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


    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false, precision = 17, scale = 17)
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    public Set<LineItem> getLineItems() {
        return this.lineItems;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }


}


