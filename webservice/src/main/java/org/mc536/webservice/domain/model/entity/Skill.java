package org.mc536.webservice.domain.model.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.mc536.webservice.domain.model.entity.User;

@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @Column(name = "SName", length = 30, unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "User_Skill",
        joinColumns = { @JoinColumn(name = "SkillId", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "UserId", nullable = false)}
    )
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}