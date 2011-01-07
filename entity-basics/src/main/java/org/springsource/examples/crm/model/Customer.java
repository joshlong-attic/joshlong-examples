package org.springsource.examples.crm.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements java.io.Serializable {

  private Long id;
  private String firstName;
  private String lastName;
  private Set<Purchase> purchases = new HashSet<Purchase>(0);

  public Customer() {
  }

  public Customer(String fn, String ln) {
    this.firstName = fn;
    this.lastName = ln;
  }

  public Customer(long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Customer(long id, String firstName, String lastName, Set<Purchase> purchases) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.purchases = purchases;
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

  @Column(name = "first_name", nullable = false)
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "last_name", nullable = false)
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
  public Set<Purchase> getPurchases() {
    return this.purchases;
  }

  public void setPurchases(Set<Purchase> purchases) {
    this.purchases = purchases;
  }
}


