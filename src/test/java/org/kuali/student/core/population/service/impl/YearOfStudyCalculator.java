package org.kuali.student.enrollment.academicrecord.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Date;
import org.kuali.student.core.population.service.impl.PopulationTestStudentEnum;

/**
 * An interface that abstracts the year of study calculator.
 * User: mahtabme
 * Date: 12/9/13
 * Time: 3:43 PM
 */
public interface YearOfStudyCalculator {

    /**
     * Gets the year of study of a student now.
     */
    public String getYearOfStudy (PopulationTestStudentEnum populationTestStudentEnum, ContextInfo contextInfo) throws OperationFailedException;
}
