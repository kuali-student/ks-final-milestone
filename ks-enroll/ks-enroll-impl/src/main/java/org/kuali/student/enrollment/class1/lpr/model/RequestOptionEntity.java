package org.kuali.student.enrollment.class1.lpr.model;

import java.util.List;

import javax.persistence.Column;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

public class RequestOptionEntity extends MetaEntity implements AttributeOwner<LuiPersonRelationAttributeEntity> {


    @Column(name="OPTION_KEY")
    private String optionKey;

    @Column(name="OPTION_VALUE")
    private String optionValue;

    @Override
    public void setAttributes(List<LuiPersonRelationAttributeEntity> attributes) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS

    }

    @Override
    public List<LuiPersonRelationAttributeEntity> getAttributes() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
