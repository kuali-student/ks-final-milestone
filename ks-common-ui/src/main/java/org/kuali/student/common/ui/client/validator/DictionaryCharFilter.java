package org.kuali.student.common.ui.client.validator;

import org.kuali.student.core.dictionary.dto.FieldDescriptor;
@Deprecated
public class DictionaryCharFilter implements CharFilter {
    private final FieldDescriptor descriptor;
    
    public DictionaryCharFilter(FieldDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public String filter(String s) {
        String result = null;
        
        if (s != null) {
            StringBuilder sb = new StringBuilder();
            String invalid = "";// descriptor.getInvalidChars();
            String valid = "";//descriptor.getValidChars();
            for (char c : s.toCharArray()) {
                boolean append = true;
                if (invalid != null && invalid.indexOf(c) != -1) {
                    append = false;
                } else if (valid != null && valid.indexOf(c) == -1) {
                    append = false;
                }
                if (append) {
                    sb.append(c);
                }
            }
        }
        
        return result;
    }

}
