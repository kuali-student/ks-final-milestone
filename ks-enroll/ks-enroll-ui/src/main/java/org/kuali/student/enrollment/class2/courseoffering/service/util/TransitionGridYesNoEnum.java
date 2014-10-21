/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 5/23/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import java.util.ArrayList;
import java.util.List;

public enum TransitionGridYesNoEnum {
    YES("yes"), NO("no"), INVALID("invalid");
    public static final List<String> ALLOWED_VALUES;
    static {
        ALLOWED_VALUES = new ArrayList<String>();
        ALLOWED_VALUES.add("yes");
        ALLOWED_VALUES.add("no");
        ALLOWED_VALUES.add("invalid");
    }

    private String name;
    private TransitionGridYesNoEnum(String val) {
        name = val;
    }

    public String getName() {
        return name;
    }
}
