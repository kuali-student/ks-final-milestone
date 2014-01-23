package org.kuali.student.common.krms.exceptions;

/**
 * Created with IntelliJ IDEA.
 * Author: Kuali Student team
 * Date: 2013/11/21
 */
public class KRMSOptimisticLockingException extends RuntimeException {
    public KRMSOptimisticLockingException() {
    }

    public KRMSOptimisticLockingException(String message) {
        super(message);
    }

}
