package org.kuali.student.enrollment.academicrecord.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StudentTestScoreRecord extends IdEntity {

    /**
     * Test title
     *
     * @name Test Title
     * @readOnly
     */
    public String getTestTitle();

    /**
     * Code used to refer to this type or component
     * 
     * @name Test Code
     * @readOnly
     */
    public String getTestCode();

    /**
     * Type of the test
     * 
     * @name Test Type key
     * @readOnly
     */
    public String getTestTypeKey();

    /**
     * Date when the test was taken
     * 
     * @name Date Taken
     * @readOnly
     */
    public Date getDateTaken();

    /**
     * Score attained in the test
     * 
     * @name Score Value
     * @readOnly
     */
    public String getScoreValue();

    /**
     * Score percentage
     * 
     * @name Score Percent
     * @readOnly
     */
    public String getScorePercent();

    /**
     * Any subcomponents associated with this test score
     *
     * @name Sub Components
     * @readOnly
     */
    public List<? extends StudentTestScoreRecord> getSubComponents();

}

