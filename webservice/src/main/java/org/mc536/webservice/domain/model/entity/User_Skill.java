package org.mc536.webservice.domain.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "User_Skill")
public class User_Skill {

    @EmbeddedId
    UserSkillId id;

    @Column(name = "UserId", insertable = false, updatable = false)
    private Integer UserId;
    
    @Column(name = "SkillId", insertable = false, updatable = false)
    private Integer SkillId;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getSkillId() {
        return SkillId;
    }

    public void setSkillId(Integer SkillId) {
        this.SkillId = SkillId;
    }
}

@Embeddable
class UserSkillId implements Serializable {
    Integer UserId;
    Integer SkillId;
}
