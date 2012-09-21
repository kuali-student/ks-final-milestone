package org.kuali.student.r2.common.infc;

/**
 * A generic display object where a binding between the key and value is desired
 * (Duplicate keys precludes map usage)
 *
 * @Version 2.0
 * @Author Kuali Student
 */
public interface KeyName {

    /**
     * @name Key
     * @required
     */
    public String getKey();

    /**
     * @name Value
     * @required
     */
    public String getName();
}
