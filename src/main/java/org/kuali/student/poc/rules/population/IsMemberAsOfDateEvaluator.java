package org.kuali.student.poc.rules.population;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Date;

/**
 * This defines the interface that needs to be implemented to execute a isMemberAsOfDate evaluation
 * User: mahtabme
 * Date: 12/10/13
 * Time: 10:25 AM
 */
public interface IsMemberAsOfDateEvaluator {
    public Boolean isMemberAsOfDate(PopulationPocPersonEnum populationPocPersonEnum, String populationId, Date date, ContextInfo contextInfo)
            throws OperationFailedException;
}
