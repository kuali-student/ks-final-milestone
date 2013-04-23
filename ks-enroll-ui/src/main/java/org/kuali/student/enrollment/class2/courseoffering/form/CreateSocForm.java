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
 * Created by Charles on 4/23/13
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CreateSocForm  extends UifFormBase {
    private String socTermCode;
    private TermInfo termInfo;
    private SocInfo socInfo;
    private boolean termAlreadyHasSoc;
    private String socMessage = "";

    public CreateSocForm(){
        socTermCode = "";
        _resetForm();
    }

    public String getSocMessage() {
        return socMessage;
    }

    public void setSocMessage(String socMessage) {
        this.socMessage = socMessage;
    }

    private void _resetForm() {
        // TODO
    }

    public String getSocTermCode() {
        return socTermCode;
    }

    public void setSocTermCode(String socTermCode) {
        this.socTermCode = socTermCode;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

    public SocInfo getSocInfo() {
        return socInfo;
    }

    public void setSocInfo(SocInfo socInfo) {
        this.socInfo = socInfo;
    }

    public void setTermAlreadyHasSoc(boolean b) {
        this.termAlreadyHasSoc = b;
    }

    public boolean getTermAlreadyHasSoc() {
        return termAlreadyHasSoc;
    }
}
