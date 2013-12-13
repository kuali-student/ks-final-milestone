package org.kuali.student.poc.rules.population;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Date;

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
    public String getYearOfStudy (PopulationPocStudentEnum populationPocStudentEnum, ContextInfo contextInfo) throws OperationFailedException;
}
