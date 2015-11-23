package org.mc536.webservice.domain.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 100, nullable = false)
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
