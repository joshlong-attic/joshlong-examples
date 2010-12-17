package org.springsource.examples.crm.model;


import javax.persistence.*;

@Entity
@Table(name = "line_item")
public class LineItem implements java.io.Serializable {


    private Long id;
    private Purchase purchase;
    private Product product;

    public LineItem() {
    }

    public LineItem(long id, Purchase purchase, Product product) {
        this.id = id;
        this.purchase = purchase;
        this.product = product;
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
    @JoinColumn(name = "purchase_id", nullable = false)
    public Purchase getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}


