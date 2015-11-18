package org.mc536.webservice.domain.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @Column(name = "SName", length = 20, unique = true, nullable = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}