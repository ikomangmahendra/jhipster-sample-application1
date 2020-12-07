package com.kebunkarta.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "customer_status")
    private Boolean customerStatus;

    @Column(name = "supplier_status")
    private Boolean supplierStatus;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 20)
    @Column(name = "phone_1", length = 20)
    private String phone1;

    @Size(max = 20)
    @Column(name = "phone_2", length = 20)
    private String phone2;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 30)
    @Column(name = "city", length = 30)
    private String city;

    @Size(max = 50)
    @Column(name = "province", length = 50)
    private String province;

    @Size(max = 10)
    @Column(name = "post_code", length = 10)
    private String postCode;

    @Size(max = 50)
    @Column(name = "sales_name", length = 50)
    private String salesName;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @NotNull
    @Column(name = "record_status_id", nullable = false)
    private Integer recordStatusId;

    @OneToMany(mappedBy = "contact")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Purchase> suppliers = new HashSet<>();

    @OneToMany(mappedBy = "contact")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Sales> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCustomerStatus() {
        return customerStatus;
    }

    public Contact customerStatus(Boolean customerStatus) {
        this.customerStatus = customerStatus;
        return this;
    }

    public void setCustomerStatus(Boolean customerStatus) {
        this.customerStatus = customerStatus;
    }

    public Boolean isSupplierStatus() {
        return supplierStatus;
    }

    public Contact supplierStatus(Boolean supplierStatus) {
        this.supplierStatus = supplierStatus;
        return this;
    }

    public void setSupplierStatus(Boolean supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getCode() {
        return code;
    }

    public Contact code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Contact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone1() {
        return phone1;
    }

    public Contact phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Contact phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Contact address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Contact city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public Contact province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostCode() {
        return postCode;
    }

    public Contact postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getSalesName() {
        return salesName;
    }

    public Contact salesName(String salesName) {
        this.salesName = salesName;
        return this;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Contact createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Contact createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Contact lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Contact lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getRecordStatusId() {
        return recordStatusId;
    }

    public Contact recordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
        return this;
    }

    public void setRecordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
    }

    public Set<Purchase> getSuppliers() {
        return suppliers;
    }

    public Contact suppliers(Set<Purchase> purchases) {
        this.suppliers = purchases;
        return this;
    }

    public Contact addSupplier(Purchase purchase) {
        this.suppliers.add(purchase);
        purchase.setContact(this);
        return this;
    }

    public Contact removeSupplier(Purchase purchase) {
        this.suppliers.remove(purchase);
        purchase.setContact(null);
        return this;
    }

    public void setSuppliers(Set<Purchase> purchases) {
        this.suppliers = purchases;
    }

    public Set<Sales> getCustomers() {
        return customers;
    }

    public Contact customers(Set<Sales> sales) {
        this.customers = sales;
        return this;
    }

    public Contact addCustomer(Sales sales) {
        this.customers.add(sales);
        sales.setContact(this);
        return this;
    }

    public Contact removeCustomer(Sales sales) {
        this.customers.remove(sales);
        sales.setContact(null);
        return this;
    }

    public void setCustomers(Set<Sales> sales) {
        this.customers = sales;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", customerStatus='" + isCustomerStatus() + "'" +
            ", supplierStatus='" + isSupplierStatus() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", postCode='" + getPostCode() + "'" +
            ", salesName='" + getSalesName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", recordStatusId=" + getRecordStatusId() +
            "}";
    }
}
