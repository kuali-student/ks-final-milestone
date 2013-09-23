package org.kuali.student.common.util;

import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/23/13
 * Time: 9:24 AM
 *
 * This is a common utility class for the KS project.
 *
 */
public class CommonUtil {

    public static <T> T getZeroElement(List<T> list) throws OperationFailedException{
        T t;
        if (list.size() == 1) {
            t = list.get(0);
        } else {
            throw new OperationFailedException("list size exceeds limit of 1");
        }

        return t;
    }

}
