package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.student.enrollment.acal.dto.TermInfo;

/**
 * Created by IntelliJ IDEA.
 * Date: 8/6/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TermCodeGenerator {
    String generateTermCode(TermInfo term);
}
