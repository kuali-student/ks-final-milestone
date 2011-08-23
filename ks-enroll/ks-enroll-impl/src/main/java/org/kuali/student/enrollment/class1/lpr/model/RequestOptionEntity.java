package org.kuali.student.enrollment.class1.lpr.model;

import java.util.List;

import javax.persistence.Column;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

public class RequestOptionEntity extends MetaEntity  {


    @Column(name="OPTION_KEY")
    private String optionKey;

    @Column(name="OPTION_VALUE")
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
