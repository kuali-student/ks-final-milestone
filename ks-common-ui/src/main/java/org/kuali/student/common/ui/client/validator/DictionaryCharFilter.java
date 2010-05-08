/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.validator;

import org.kuali.student.core.dictionary.dto.FieldDescriptor;
@Deprecated
public class DictionaryCharFilter implements CharFilter {

    public DictionaryCharFilter(FieldDescriptor descriptor) {
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
