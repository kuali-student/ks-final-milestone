package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.r2.common.infc.HasId;

/**
 * Specifies the request options in a LPR transaction. The options can be used
 * to create specific kinds of LPR relation.
 * 
 * @author Kuali Student Team (sambitpatnaik)
 */
public interface RequestOption extends HasId{
    /**
     * The option value data type of the request option e.g., Boolean, String
     * etc.
     * 
     * @return
     */
    public String getOptionKey();

    /**
     * This value of the option
     * 
     * @return
     */
    public String getOptionValue();

}
