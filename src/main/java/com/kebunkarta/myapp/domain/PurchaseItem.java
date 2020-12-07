package com.kebunkarta.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A PurchaseItem.
 */
@Entity
@Table(name = "purchase_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    @NotNull
    @Column(name = "qty", precision = 21, scale = 2, nullable = false)
    private BigDecimal qty;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "disc", precision = 21, scale = 2)
    private BigDecimal disc;

    @Column(name = "disc_percent_status")
    private Boolean discPercentStatus;

    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Size(max = 20)
    @Column(name = "note", length = 20)
    private String note;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "purchaseItems", allowSetters = true)
    private CatalogItem catalog;

    @ManyToOne
    @JsonIgnoreProperties(value = "purchaseItems", allowSetters = true)
    private Unit unit;

    @ManyToOne
    @JsonIgnoreProperties(value = "purchaseItems", allowSetters = true)
    private Tax tax;

    @ManyToOne
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private Purchase purchase;

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

    public PurchaseItem typeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public PurchaseItem qty(BigDecimal qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PurchaseItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDisc() {
        return disc;
    }

    public PurchaseItem disc(BigDecimal disc) {
        this.disc = disc;
        return this;
    }

    public void setDisc(BigDecimal disc) {
        this.disc = disc;
    }

    public Boolean isDiscPercentStatus() {
        return discPercentStatus;
    }

    public PurchaseItem discPercentStatus(Boolean discPercentStatus) {
        this.discPercentStatus = discPercentStatus;
        return this;
    }

    public void setDiscPercentStatus(Boolean discPercentStatus) {
        this.discPercentStatus = discPercentStatus;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public PurchaseItem taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getNote() {
        return note;
    }

    public PurchaseItem note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PurchaseItem createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PurchaseItem createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PurchaseItem lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PurchaseItem lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getRecordStatusId() {
        return recordStatusId;
    }

    public PurchaseItem recordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
        return this;
    }

    public void setRecordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
    }

    public CatalogItem getCatalog() {
        return catalog;
    }

    public PurchaseItem catalog(CatalogItem catalogItem) {
        this.catalog = catalogItem;
        return this;
    }

    public void setCatalog(CatalogItem catalogItem) {
        this.catalog = catalogItem;
    }

    public Unit getUnit() {
        return unit;
    }

    public PurchaseItem unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Tax getTax() {
        return tax;
    }

    public PurchaseItem tax(Tax tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public PurchaseItem purchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseItem)) {
            return false;
        }
        return id != null && id.equals(((PurchaseItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseItem{" +
            "id=" + getId() +
            ", typeId=" + getTypeId() +
            ", qty=" + getQty() +
            ", price=" + getPrice() +
            ", disc=" + getDisc() +
            ", discPercentStatus='" + isDiscPercentStatus() + "'" +
            ", taxAmount=" + getTaxAmount() +
            ", note='" + getNote() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", recordStatusId=" + getRecordStatusId() +
            "}";
    }
}
