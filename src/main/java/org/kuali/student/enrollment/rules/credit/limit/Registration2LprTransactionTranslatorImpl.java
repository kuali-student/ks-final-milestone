package org.kuali.student.enrollment.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Implementation of the translator
 */
public class Registration2LprTransactionTranslatorImpl implements Registration2LprTransactionTranslator {

    @Override
    public LprTransactionInfo translate(List<CourseRegistrationTransaction> registrations, ContextInfo contextInfo)
            throws OperationFailedException {
        LprTransactionInfo lprTrans = new LprTransactionInfo();
        for (CourseRegistrationTransaction regTrans : registrations) {
            switch (regTrans.getAction()) {
                case CREATE:
                    LprTransactionItemInfo item = constructNewTransactionItem(regTrans.getRegistration(), contextInfo);
                    item.setLuiId(regTrans.getRegistration().getCourseOfferingId());
            }
            for (ActivityRegistrationTransaction actTrans : regTrans.getActivityRegistrationTransactions()) {
            }
        }
        return lprTrans;
    }

    protected LprTransactionItemInfo constructNewTransactionItem(CourseRegistrationInfo reg, ContextInfo contextInfo)
            throws OperationFailedException {
        LprTransactionItemInfo item = new LprTransactionItemInfo();
        item.setLuiId(reg.getCourseOfferingId());
        item.setPersonId(reg.getPersonId());
        item.setTypeKey(reg.getTypeKey());
        return item;
    }
}
