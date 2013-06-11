package org.kuali.student.r2.core.acal.service;

import org.kuali.student.r2.core.acal.dto.TermInfo;

/**
 * The TermCodeGenerator is used to generate term codes. For example, an institution might want the
 * Spring 2012 term code to be 201201, with the 01 meaning "spring". Or maybe they want it to be
 * 20121. This is a generic interface that will allow institutions to configure the term codes as they see fit.
 */
public interface TermCodeGenerator {

    /**
     * Generate the term code based off the TermInfo object passed into the method.
     *
     * @param term
     * @return A String with the new term code or null.
     */
    String generateTermCode(TermInfo term);
}
