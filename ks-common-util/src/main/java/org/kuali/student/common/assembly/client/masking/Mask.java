package org.kuali.student.common.assembly.client.masking;

import java.io.Serializable;

public class Mask implements Serializable {
    public static final String DEFAULT_MASK = "*";
    public String mask(String field) {
        return field;
    }
}
