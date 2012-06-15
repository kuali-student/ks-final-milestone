/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 6/15/12
 */
package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditOptions", propOrder = {"id", "type", "credits"})

public class CreditOptionInfo extends IdEntityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String type;

    @XmlElement
    List<String> credits;

    /**
     * Constructs a new CreditOptions.
     */
    public CreditOptionInfo() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCredits() {
        if (null == credits) {
            credits = new ArrayList<String>();
        }
        return credits;
    }

    public void setCredits(List<String> credits) {
        this.credits = credits;
    }

}
