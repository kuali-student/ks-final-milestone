package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Embeddable;

import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.r2.common.entity.MetaEntity;

@Embeddable
public class ResultOptionEntity extends MetaEntity {

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ResultOptionEntity(ResultOptionInfo lprTransactionItem) {

    }

}
