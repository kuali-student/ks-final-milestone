package org.kuali.student.core.assembly.data.masking;

public class SimpleMask extends Mask {
    private static final long serialVersionUID = 1L;

    @Override
    public String mask(String field) {
        StringBuilder sb = new StringBuilder(field.length());
        for(int i = 0; i < field.length(); i++) {
            sb.append(DEFAULT_MASK);
        }
        return sb.toString();
    }

}
