package org.mc536.webservice.domain.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Demands")
public class Demands {
    
    @EmbeddedId
    DemandsId id;

    @Column(name = "OfferId", insertable = false, updatable = false)
    private Integer OfferId;
    
    @Column(name = "SkillId", insertable = false, updatable = false)
    private Integer SkillId;

    public Integer getOfferId() {
        return OfferId;
    }

    public void setOfferId(Integer OfferId) {
        this.OfferId = OfferId;
    }

    public Integer getSkillId() {
        return SkillId;
    }

    public void setSkillId(Integer SkillId) {
        this.SkillId = SkillId;
    }
}

@Embeddable
class DemandsId implements Serializable {
    Integer OfferId;
    Integer SkillId;
}