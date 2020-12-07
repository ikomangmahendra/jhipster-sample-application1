package com.kebunkarta.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Gudang.
 */
@Entity
@Table(name = "gudang")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gudang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "warehouse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Purchase> purchases = new HashSet<>();

    @OneToMany(mappedBy = "warehouse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Sales> sales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Gudang name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public Gudang purchases(Set<Purchase> purchases) {
        this.purchases = purchases;
        return this;
    }

    public Gudang addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
        purchase.setWarehouse(this);
        return this;
    }

    public Gudang removePurchase(Purchase purchase) {
        this.purchases.remove(purchase);
        purchase.setWarehouse(null);
        return this;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Set<Sales> getSales() {
        return sales;
    }

    public Gudang sales(Set<Sales> sales) {
        this.sales = sales;
        return this;
    }

    public Gudang addSales(Sales sales) {
        this.sales.add(sales);
        sales.setWarehouse(this);
        return this;
    }

    public Gudang removeSales(Sales sales) {
        this.sales.remove(sales);
        sales.setWarehouse(null);
        return this;
    }

    public void setSales(Set<Sales> sales) {
        this.sales = sales;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gudang)) {
            return false;
        }
        return id != null && id.equals(((Gudang) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gudang{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
