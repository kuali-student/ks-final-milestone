package org.kuali.student.r2.common.infc;

/**
 * A generic display object where a binding between the key and value is desired
 * (The need to support duplicate keys in some KS scenarios precludes map usage)
 *
 * @Version 2.0
 * @Author Kuali Student
 */
public interface KeyName {

    /**
     * The key of interest
     * @name Key
     * @required
     */
    public String getKey();

    /**
     * Information associated with the key
     *
     * @name Value
     * @required
     */
    public String getName();
}
