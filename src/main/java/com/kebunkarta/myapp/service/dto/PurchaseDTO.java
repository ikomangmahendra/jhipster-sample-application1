package com.kebunkarta.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.kebunkarta.myapp.domain.Purchase} entity.
 */
public class PurchaseDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer typeId;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(max = 20)
    private String refNo;

    @Size(max = 100)
    private String description;

    private BigDecimal otherCost;

    @NotNull
    private BigDecimal subtotal;

    @NotNull
    private BigDecimal totalTax;

    private BigDecimal disc;

    private Boolean discPercentStatus;

    @Size(max = 2)
    private String status;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @NotNull
    private Integer recordStatusId;


    private Long contactId;

    private Long warehouseId;
    
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long gudangId) {
        this.warehouseId = gudangId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseDTO)) {
            return false;
        }

        return id != null && id.equals(((PurchaseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseDTO{" +
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
            ", contactId=" + getContactId() +
            ", warehouseId=" + getWarehouseId() +
            "}";
    }
}
