package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.infc.State;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_STATE")
// TODO: Uncomment when/if we figure out if we store xxx_KEY as such in the DB instead of as ID
//@AttributeOverrides({
//    @AttributeOverride(name = "id", column =
//    @Column(name = "STATE_KEY"))})
public class StateEntity extends MetaEntity implements AttributeOwner<StateAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable=false)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    // TODO: consider storing this as a related JPA entity instead of as a string
    @Column(name = "LIFECYCLE_KEY")
    private String lifecycleKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @Column(name = "IS_INITIAL_STATE", nullable = false)
    private boolean initialState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<StateAttributeEntity> attributes = new HashSet<StateAttributeEntity>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getLifecycleKey() {
        return lifecycleKey;
    }

    public void setLifecycleKey(String lifecycleKey) {
        this.lifecycleKey = lifecycleKey;
    }

    
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public void setInitialState(boolean initialState) {
        this.initialState = initialState;
    }

    public Set<StateAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<StateAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public StateEntity() {
    }

    public StateEntity(State state) {
        super();
        this.setId(state.getKey());
        this.lifecycleKey = state.getLifecycleKey();
        this.fromDto(state);
    }

    public void fromDto(State state) {
        this.setName(state.getName());
        if (state.getDescr() == null) {
            this.descrPlain = null;
            this.descrFormatted = null;
        } else {
            this.descrPlain = state.getDescr().getPlain();
            this.descrFormatted = state.getDescr().getFormatted();
        }
        this.effectiveDate = state.getEffectiveDate();
        this.expirationDate = state.getExpirationDate();
        this.initialState = (state.getIsInitialState() == null ? false : state.getIsInitialState() );
        this.setAttributes(new HashSet<StateAttributeEntity>());
        for (Attribute att : state.getAttributes()) {
            StateAttributeEntity attEntity = new StateAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public StateInfo toDto() {
        StateInfo info = new StateInfo();
        info.setKey(getId());
        info.setLifecycleKey(lifecycleKey);
        info.setName(name);
        info.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);
        info.setIsInitialState(initialState);
        info.setMeta(super.toDTO());
        for (StateAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        };
        return info;
    }
}
