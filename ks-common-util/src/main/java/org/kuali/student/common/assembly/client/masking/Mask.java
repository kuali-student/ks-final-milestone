package org.kuali.student.common.assembly.client.masking;

import java.io.Serializable;

public interface Mask extends Serializable {
    public static final String DEFAULT_MASK = "*";
    String mask(String field);
}
