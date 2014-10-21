/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.dto.AgendaEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AORuleManagementWrapper extends CORuleManagementWrapper {

    private String aoDescription;
    private boolean copyEditCORuleDirty;

    public String getAoDescription() {
        return aoDescription;
    }

    public void setAoDescription(String aoDescription) {
        this.aoDescription = aoDescription;
    }

    public boolean isCopyEditCORuleDirty() {
        return copyEditCORuleDirty;
    }

    public void setCopyEditCORuleDirty(boolean copyEditCORuleDirty) {
        this.copyEditCORuleDirty = copyEditCORuleDirty;
    }
}
