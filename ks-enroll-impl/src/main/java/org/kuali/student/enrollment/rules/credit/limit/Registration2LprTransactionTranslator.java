package org.kuali.student.enrollment.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This defines the methods to translate registration transactions into lpr transactions.
 */
public interface Registration2LprTransactionTranslator {

    /**
     * Make registration actions merging the request and existing registrations
     *
     * @param registrations
     * @param contextInfo
     * @return 
     * @throws OperationFailedException
     */
    public LprTransactionInfo translate(List<CourseRegistrationTransaction> registrations,  ContextInfo contextInfo)
            throws OperationFailedException;

}
