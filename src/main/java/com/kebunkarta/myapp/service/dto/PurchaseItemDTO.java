package com.kebunkarta.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.kebunkarta.myapp.domain.PurchaseItem} entity.
 */
public class PurchaseItemDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer typeId;

    @NotNull
    private BigDecimal qty;

    @NotNull
    private BigDecimal price;

    private BigDecimal disc;

    private Boolean discPercentStatus;

    @NotNull
    private BigDecimal tax;

    @Size(max = 20)
    private String note;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @NotNull
    private Integer recordStatusId;


    private Long catalogId;

    private Long unitId;

    private Long taxId;

    private Long purchaseId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDisc() {
        return disc;
    }

    public void setDisc(BigDecimal disc) {
        this.disc = disc;
    }

    public Boolean isDiscPercentStatus() {
        return discPercentStatus;
    }

    public void setDiscPercentStatus(Boolean discPercentStatus) {
        this.discPercentStatus = discPercentStatus;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getRecordStatusId() {
        return recordStatusId;
    }

    public void setRecordStatusId(Integer recordStatusId) {
        this.recordStatusId = recordStatusId;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long barangId) {
        this.catalogId = barangId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseItemDTO)) {
            return false;
        }

        return id != null && id.equals(((PurchaseItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseItemDTO{" +
            "id=" + getId() +
            ", typeId=" + getTypeId() +
            ", qty=" + getQty() +
            ", price=" + getPrice() +
            ", disc=" + getDisc() +
            ", discPercentStatus='" + isDiscPercentStatus() + "'" +
            ", tax=" + getTax() +
            ", note='" + getNote() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", recordStatusId=" + getRecordStatusId() +
            ", catalogId=" + getCatalogId() +
            ", unitId=" + getUnitId() +
            ", taxId=" + getTaxId() +
            ", purchaseId=" + getPurchaseId() +
            "}";
    }
}
