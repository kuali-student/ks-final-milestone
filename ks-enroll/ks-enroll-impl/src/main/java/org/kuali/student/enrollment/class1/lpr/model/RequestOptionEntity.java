package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.kuali.student.r2.common.entity.MetaEntity;

@Embeddable
public class RequestOptionEntity extends MetaEntity {

    @Column(name = "OPTION_KEY")
    private String optionKey;

    @Column(name = "OPTION_VALUE")
    private String optionValue;

    public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

}
