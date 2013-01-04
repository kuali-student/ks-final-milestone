package org.kuali.student.r2.core.document.infc;

/**
 * The encoded document. The expectation is that this could be a
 * base64 encoding.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface DocumentBinary {
    /**
     * Encoded document
     *
     * @name Binary
     * @required
     *
     */
    public String getBinary();
}
