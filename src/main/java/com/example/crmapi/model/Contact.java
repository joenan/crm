package com.example.crmapi.model;

import com.example.crmapi.model.auth.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "salutation cant be blank")
    @NotNull(message = "salutation cannot be null")
    @Column(name = "salutation")
    private String salutation;

    @NotBlank(message = "firstName cant be blank")
    @NotNull(message = "firstName cannot be null")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "lastName cant be blank")
    @NotNull(message = "lastName cannot be null")
    @Column(name = "last_name")
    private String lastName;

//    @NotBlank(message = "accountName cant be blank")
//    @NotNull(message = "accountName cannot be null")
//    @Column(name = "account_name")
//    private String accountName;

    @NotBlank(message = "vendorName cant be blank")
    @NotNull(message = "vendorName cannot be null")
    @Column(name = "vendor_name")
    private String vendorName;

    @NotBlank(message = "campaignSource cannot be blank")
    @NotNull(message = "campaignSource cannot be null")
    @Column(name = "campaign_source")
    private String campaignSource;

    @NotBlank(message = "leadSource cant be blank")
    @NotNull(message = "leadSource cannot be null")
    @Column(name = "lead_source")
    private String leadSource;

    @NotBlank(message = "title cant be blank")
    @NotNull(message = "title cannot be null")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "department cant be blank")
    @NotNull(message = "department cannot be null")
    @Column(name = "department")
    private String department;

    @NotNull(message = "dateOfBirth cant be null")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotBlank(message = "reportsTo cant be blank")
    @NotNull(message = "reportsTo cannot be null")
    @Column(name = "reports_to")
    private String reportsTo;

    @Column(name = "created_by")
    private Date createdBy;

    @Column(name = "modified_by")
    private Date modifiedBy;

    @NotNull(message = "emailOptOut cannot be null")
    @Column(name = "email_opt_out")
    private boolean emailOptOut;

    @NotBlank(message = "skypeId cant be blank")
    @NotNull(message = "skypeId cannot be null")
    @Column(name = "skype_id")
    private String skypeId;

    @NotBlank(message = "phone cannot be blank")
    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "other_phone")
    private String otherPhone;

    @Column(name = "fax")
    private String fax;

    @NotBlank(message = "email cant be blank")
    @NotNull(message = "email cannot be null")
    @Email(message = "Enter a valid Email Address")
    @Column(name = "email")
    private String email;

    @Column(name = "secondary_email")
    private String secondaryEmail;

    @NotBlank(message = "assistant cant be blank")
    @NotNull(message = "assistant cannot be null")
    @Column(name = "assistant")
    private String assistant;

    @Column(name = "asst_phone")
    private String asstPhone;

    @NotBlank(message = "description cant be blank")
    @NotNull(message = "description cannot be null")
    @Column(name = "description")
    private String description;


    @JoinColumn(name = "mailing_address", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.ALL})
    private MailingAddress mailingAddress;
//

    @JoinColumn(name = "other_address", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.ALL})
    private OtherAddress otherAddress;
//

    @JoinColumn(name = "contact_owner", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private User contactOwner;

    @JoinColumn(name = "account_name", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private Account accountName;


    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCampaignSource() {
        return campaignSource;
    }

    public void setCampaignSource(String campaignSource) {
        this.campaignSource = campaignSource;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
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

    public boolean isEmailOptOut() {
        return emailOptOut;
    }

    public void setEmailOptOut(boolean emailOptOut) {
        this.emailOptOut = emailOptOut;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getAsstPhone() {
        return asstPhone;
    }

    public void setAsstPhone(String asstPhone) {
        this.asstPhone = asstPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MailingAddress getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(MailingAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public OtherAddress getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(OtherAddress otherAddress) {
        this.otherAddress = otherAddress;
    }

    public User getContactOwner() {
        return contactOwner;
    }

    public void setContactOwner(User contactOwner) {
        this.contactOwner = contactOwner;
    }

    public Account getAccountName() {
        return accountName;
    }

    public void setAccountName(Account accountName) {
        this.accountName = accountName;
    }
}
