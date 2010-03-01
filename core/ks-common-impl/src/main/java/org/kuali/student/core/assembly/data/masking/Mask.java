package org.kuali.student.core.assembly.data.masking;

import java.io.Serializable;

public class Mask implements Serializable {
    public static final String DEFAULT_MASK = "*";
    public String mask(String field) {
        return field;
    }
}
