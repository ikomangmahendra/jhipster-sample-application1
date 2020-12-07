package com.kebunkarta.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sales.
 */
@Entity
@Table(name = "sales")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Size(max = 20)
    @Column(name = "ref_no", length = 20, nullable = false, unique = true)
    private String refNo;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "other_cost", precision = 21, scale = 2)
    private BigDecimal otherCost;

    @NotNull
    @Column(name = "subtotal", precision = 21, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "total_tax", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalTax;

    @Column(name = "disc", precision = 21, scale = 2)
    private BigDecimal disc;

    @Column(name = "disc_percent_status")
    private Boolean discPercentStatus;

    @Size(max = 2)
    @Column(name = "status", length = 2)
    private String status;

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

    @OneToMany(mappedBy = "sales")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SalesItem> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Contact contact;

    @ManyToOne
    @JsonIgnoreProperties(value = "sales", allowSetters = true)
    private Warehouse warehouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Sales typeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Sales date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRefNo() {
        return refNo;
    }

    public Sales refNo(String refNo) {
        this.refNo = refNo;
        return this;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getDescription() {
        return description;
    }

    public Sales description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public Sales otherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public Sales subtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public Sales totalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
        return this;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getDisc() {
        return disc;
    }

    public Sales disc(BigDecimal disc) {
        this.disc = disc;
        return this;
    }

    public void setDisc(BigDecimal disc) {
        this.disc = disc;
    }

    public Boolean isDiscPercentStatus() {
        return discPercentStatus;
    }

    public Sales discPercentStatus(Boolean discPercentStatus) {
        this.discPercentStatus = discPercentStatus;
        return this;
    }

    public void setDiscPercentStatus(Boolean discPercentStatus) {
        this.discPercentStatus = discPercentStatus;
    }

    public String getStatus() {
        return status;
    }

    public Sales status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Sales createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Sales createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Sales lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Sales lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getRecordStatusId() {
        return recordStatusId;
    }

    public Sales recordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
        return this;
    }

    public void setRecordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
    }

    public Set<SalesItem> getItems() {
        return items;
    }

    public Sales items(Set<SalesItem> salesItems) {
        this.items = salesItems;
        return this;
    }

    public Sales addItems(SalesItem salesItem) {
        this.items.add(salesItem);
        salesItem.setSales(this);
        return this;
    }

    public Sales removeItems(SalesItem salesItem) {
        this.items.remove(salesItem);
        salesItem.setSales(null);
        return this;
    }

    public void setItems(Set<SalesItem> salesItems) {
        this.items = salesItems;
    }

    public Contact getContact() {
        return contact;
    }

    public Sales contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Sales warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sales)) {
            return false;
        }
        return id != null && id.equals(((Sales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sales{" +
            "id=" + getId() +
            ", typeId=" + getTypeId() +
            ", date='" + getDate() + "'" +
            ", refNo='" + getRefNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", otherCost=" + getOtherCost() +
            ", subtotal=" + getSubtotal() +
            ", totalTax=" + getTotalTax() +
            ", disc=" + getDisc() +
            ", discPercentStatus='" + isDiscPercentStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", recordStatusId=" + getRecordStatusId() +
            "}";
    }
}
