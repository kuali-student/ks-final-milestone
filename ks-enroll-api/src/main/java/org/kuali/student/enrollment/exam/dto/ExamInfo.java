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
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 1/24/13
 */
package org.kuali.student.enrollment.exam.dto;

import org.kuali.student.enrollment.exam.infc.Exam;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * This class represents information about a canonical Exam.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExamInfo", propOrder = {"id", "typeKey", "stateKey", "name",
        "descr", "meta", "attributes", "_futureElements"})
public class ExamInfo extends IdEntityInfo implements Exam, Serializable {

    /////////////////////////////
    // DATA VARIABLES
    /////////////////////////////

    @XmlAnyElement
    private List<Element> _futureElements;

    //////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////

    /**
     * Constructs an empty Exam.
     */
    public ExamInfo () { }

    /**
     * Constructs a new ExamInfo from another
     * Exam.
     *
     * @param infc the exam to copy
     */
    public ExamInfo (Exam infc) {
        super(infc);

        if (infc == null) {
            return;
        }

    }

}
