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
package org.kuali.student.lum.lu.ui.krms.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a lightweight wrapper for Clu Set Information used in the KRMS UI.
 *
 * @author Kuali Student Team
 */
public class CluSetRangeInformation implements Serializable {

    private String cluSetRangeLabel;
    private MembershipQueryInfo membershipQueryInfo;
    private List<CluInformation> clusInRange;

    public CluSetRangeInformation() {
        this.cluSetRangeLabel = StringUtils.EMPTY;
    }

    public String getCluSetRangeLabel() {
        return cluSetRangeLabel;
    }

    public void setCluSetRangeLabel(String cluSetRangeLabel) {
        this.cluSetRangeLabel = cluSetRangeLabel;
    }

    public MembershipQueryInfo getMembershipQueryInfo() {
        return membershipQueryInfo;
    }

    public void setMembershipQueryInfo(MembershipQueryInfo membershipQueryInfo) {
        this.membershipQueryInfo = membershipQueryInfo;
    }

    public List<CluInformation> getClusInRange() {
        if (clusInRange == null) {
            this.clusInRange = new ArrayList<CluInformation>();
        }
        return this.clusInRange;
    }

    public void setClusInRange(List<CluInformation> clusInRange) {
        this.clusInRange = clusInRange;
    }

}
