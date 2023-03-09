package com.example.crmapi.model;

import com.example.crmapi.model.auth.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Account Name cannot be null")
    @NotBlank(message = "Account Name cannot be blank")
    @Column(name = "account_name")
    private String accountName;

    @NotNull(message = "Website cannot be null")
    @NotBlank(message = "Website cannot be blank")
    @Column(name = "website")
    private String website;

    @NotBlank(message = "Ticker Symbol cannot be blank")
    @NotNull(message = "Ticker Symbol cannot be null")
    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    @NotBlank(message = "Parent Account cannot be blank")
    @NotNull(message = "Parent Account cannot be null")
    @Column(name = "parent_account")
    private String parentAccount;

    @NotBlank(message = "Employees cannot be blank")
    @NotNull(message = "Employees cannot be null")
    @Column(name = "employees")
    private String employees;

    @NotBlank(message = "Ownership cannot be blank")
    @NotNull(message = "Ownership cannot be null")
    @Column(name = "ownership")
    private String ownership;

    @NotBlank(message = "Industry cannot be blank")
    @NotNull(message = "Industry cannot be null")
    @Column(name = "industry")
    private String industry;

    @NotBlank(message = "Account Type cannot be blank")
    @NotNull(message = "Account Type cannot be null")
    @Column(name = "account_type")
    private String accountType;

    @NotBlank(message = "Account Number cannot be blank")
    @NotNull(message = "Account Number cannot be null")
    @Column(name = "account_number")
    private String accountNumber;

    @NotBlank(message = "Account Site cannot be blank")
    @NotNull(message = "Account Site cannot be null")
    @Column(name = "account_site")
    private String accountSite;

    @NotBlank(message = "Phone cannot be blank")
    @NotNull(message = "Phone cannot be null")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Fax cannot be blank")
    @NotNull(message = "Fax cannot be null")
    @Column(name = "fax")
    private String fax;

    @NotBlank(message = "Rating cannot be blank")
    @NotNull(message = "Rating cannot be null")
    @Column(name = "rating")
    private String rating;

    @NotBlank(message = "Sic Code cannot be blank")
    @NotNull(message = "Sic Code cannot be null")
    @Column(name = "sic_code")
    private String sicCode;

    @NotBlank(message = "Annual Revenue cannot be blank")
    @NotNull(message = "Annual Revenue cannot be null")
    @Column(name = "annual_revenue")
    private String annualRevenue;

    @NotBlank(message = "Description cannot be blank")
    @NotNull(message = "Description cannot be null")
    @Column(name = "description")
    private String description;


    @Column(name = "created_by")
    private Date createdBy;

    @Column(name = "modified_by")
    private Date modifiedBy;

    @JoinColumn(name = "billing_address", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.ALL})
    private BillingAddress billingAddress;

    @JoinColumn(name = "shipping_address", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.ALL})
    private ShippingAddress shippingAddress;

    @JoinColumn(name = "account_owner", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private User accountOwner;


    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public User getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(User accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(String parentAccount) {
        this.parentAccount = parentAccount;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountSite() {
        return accountSite;
    }

    public void setAccountSite(String accountSite) {
        this.accountSite = accountSite;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSicCode() {
        return sicCode;
    }

    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    public String getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(String annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Date createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Date modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
