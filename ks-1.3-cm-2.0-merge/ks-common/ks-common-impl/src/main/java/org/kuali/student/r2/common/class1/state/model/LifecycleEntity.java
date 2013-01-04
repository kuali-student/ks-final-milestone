package org.kuali.student.r2.common.class1.state.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.state.dto.LifecycleInfo;
import org.kuali.student.r2.common.state.infc.Lifecycle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_STATE_LIFECYCLE")
// TODO: Uncomment when/if we figure out if we store xxx_KEY as such in the DB instead of as ID
//@AttributeOverrides({
//    @AttributeOverride(name = "id", column =
//    @Column(name = "LIFECYCLE_KEY"))})
public class LifecycleEntity extends MetaEntity implements AttributeOwner<LifecycleAttributeEntity> {
	
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_PLAIN")
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Column(name = "REF_OBJECT_URI", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable=false)
    private String refObjectUri;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<LifecycleAttributeEntity> attributes = new HashSet<LifecycleAttributeEntity>();
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LifecycleAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LifecycleAttributeEntity> attributes) {
        this.attributes = attributes;
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

    public String getRefObjectUri() {
        return refObjectUri;
    }

    public void setRefObjectUri(String refObjectUri) {
        this.refObjectUri = refObjectUri;
    }

    public LifecycleEntity() {
    }

    public LifecycleEntity(Lifecycle lifecycle) {
        super();
        this.setId(lifecycle.getKey());
        this.fromDto(lifecycle);
    }

    public void fromDto(Lifecycle lifecycle) {
        this.setName(lifecycle.getName());
        this.refObjectUri = lifecycle.getRefObjectUri();
        if (lifecycle.getDescr() == null) {
            this.descrPlain = null;
            this.descrFormatted = null;
        } else {
            this.descrPlain = lifecycle.getDescr().getPlain();
            this.descrFormatted = lifecycle.getDescr().getFormatted();
        }
        this.setAttributes(new HashSet<LifecycleAttributeEntity>());
        for (Attribute att : lifecycle.getAttributes()) {
            LifecycleAttributeEntity attEntity = new LifecycleAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public LifecycleInfo toDto() {
        LifecycleInfo info = new LifecycleInfo();
        info.setKey(getId());
        info.setName(name);
        info.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        info.setRefObjectUri(this.refObjectUri);
        info.setMeta(super.toDTO());
        if (attributes != null) {
            for (LifecycleAttributeEntity att : attributes) {
                AttributeInfo attInfo = att.toDto();
                info.getAttributes().add(attInfo);
            }
        }
        return info;
    }
}
