package org.mc536.webservice.domain.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Title", length = 100, nullable = false)
    private String title;

    @Column(name = "Description", length = 1000, nullable = false)
    private String description;

    @Column(name = "Location", length = 50, nullable = false)
    private String location;

    @Column(name = "Url", length = 200, unique = true, nullable = false)
    private String url;

    @Column(name = "PubDate", nullable = false)
    private Date publicationDate;

    @Column(name = "Updated")
    private Date updated;

    @Column(name = "CompanyId")
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
