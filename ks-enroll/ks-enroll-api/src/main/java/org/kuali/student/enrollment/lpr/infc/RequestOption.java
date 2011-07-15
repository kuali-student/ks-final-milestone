package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.r2.common.infc.Entity;

public interface RequestOption extends Entity{

    public String getOptionType();
    
    public String getOptionValue();
    
}
