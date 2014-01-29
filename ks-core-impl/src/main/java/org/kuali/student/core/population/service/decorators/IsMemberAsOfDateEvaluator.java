package org.kuali.student.core.population.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Date;

/**
 * This defines a specific way to evaluate if a personId is a member of a population.
 * User: mahtabme
 * Date: 12/10/13
 * Time: 10:25 AM
 */
public interface IsMemberAsOfDateEvaluator {
    public Boolean isMemberAsOfDate(String personId, String populationId, Date date, ContextInfo contextInfo)
            throws OperationFailedException;
}
