package org.kuali.student.r2.core.acal.service;

import org.kuali.student.r2.core.acal.dto.TermInfo;

/**
 * Created by IntelliJ IDEA.
 * Date: 8/6/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TermCodeGenerator {
    String generateTermCode(TermInfo term);
}
