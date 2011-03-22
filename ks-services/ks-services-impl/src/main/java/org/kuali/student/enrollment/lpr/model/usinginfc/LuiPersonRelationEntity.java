package org.kuali.student.enrollment.lpr.model.usinginfc;

import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.common.dto.AttributeInfo;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationEntity implements LuiPersonRelationInfc, Serializable {

    private static final long serialVersionUID = -8711908979901134064L;
    @Id
    @GeneratedValue
    private Long id;
    private String personId;
    private String luiId;
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @OneToMany
    @JoinColumn(name = "lui_person_relation_id")
    private List<AttributeInfc> dynamicAttributes;
    private String type;
    private String state;

    public Long getLprId() {
        return id;
    }

    public void setLprId(Long id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return "" + id;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public List<AttributeInfc> getAttributes() {
        if (this.dynamicAttributes == null) {
            return null;
        }
        List<AttributeInfc> list = new ArrayList<AttributeInfc>(dynamicAttributes.size());
        for (AttributeInfc dae : this.dynamicAttributes) {
            list.add(new AttributeInfo.Builder().setId(dae.getId()).setKey(dae.getKey()).setValue(dae.getValue()).build());
        }
        return list;
    }

    @Override
    public MetaInfc getMetaInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
