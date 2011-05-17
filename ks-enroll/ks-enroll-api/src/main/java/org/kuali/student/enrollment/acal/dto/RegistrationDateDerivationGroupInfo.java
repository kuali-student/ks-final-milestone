/*
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
package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.RegistrationDateDerivationGroup;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationDateDerivationGroupInfo", propOrder = {"registrationStartDateTermKey", "registrationEndDateTermKey", "classStartDateTermKey", "classEndDateTermKey", "addDateTermKey", "dropDateTermKey", "finalExamStartDateTermKey", "finalExamEndDateTermKey", "gradingStartDateTermKey", "gradingEndDateTermKey", "_futureElements"})
public class RegistrationDateDerivationGroupInfo implements RegistrationDateDerivationGroup, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String registrationStartDateTermKey;
    @XmlElement
    private String registrationEndDateTermKey;
    @XmlElement
    private String classStartDateTermKey;
    @XmlElement
    private String classEndDateTermKey;
    @XmlElement
    private String addDateTermKey;
    @XmlElement
    private String dropDateTermKey;
    @XmlElement
    private String finalExamStartDateTermKey;
    @XmlElement
    private String finalExamEndDateTermKey;
    @XmlElement
    private String gradingStartDateTermKey;
    @XmlElement
    private String gradingEndDateTermKey;
    @XmlAnyElement
    private List<Element> _futureElements;


    public static RegistrationDateDerivationGroupInfo getInstance() {
        return new RegistrationDateDerivationGroupInfo();
    }
    
    public static RegistrationDateDerivationGroupInfo getInstance(RegistrationDateDerivationGroup registrationDateDerivationGroup) {
        return new RegistrationDateDerivationGroupInfo(registrationDateDerivationGroup);
    }
    
    public static RegistrationDateDerivationGroupInfo getInstance(String registrationStartDateTermKey, String registrationEndDateTermKey,
                                                                  String classStartDateTermKey, String classEndDateTermKey,
														          String addDateTermKey, String dropDateTermKey,
														          String finalExamStartDateTermKey, String finalExamEndDateTermKey,
														          String gradingStartDateTermKey, String gradingEndDateTermKey) {
        return new RegistrationDateDerivationGroupInfo(registrationStartDateTermKey, registrationEndDateTermKey,
                                                       classStartDateTermKey, classEndDateTermKey,
											           addDateTermKey, dropDateTermKey,
											           finalExamStartDateTermKey, finalExamEndDateTermKey,
											           gradingStartDateTermKey, gradingEndDateTermKey);
        
    }
    
    private RegistrationDateDerivationGroupInfo() {
        registrationStartDateTermKey = null;
        registrationEndDateTermKey = null;
        classStartDateTermKey = null;
        classEndDateTermKey = null;
        addDateTermKey = null;
        dropDateTermKey = null;
        finalExamStartDateTermKey = null;
        finalExamEndDateTermKey = null;
        gradingStartDateTermKey = null;
        gradingEndDateTermKey = null;
        _futureElements = null;
    }

    private RegistrationDateDerivationGroupInfo(String registrationStartDateTermKey, String registrationEndDateTermKey,
                                                String classStartDateTermKey, String classEndDateTermKey,
									            String addDateTermKey, String dropDateTermKey,
									            String finalExamStartDateTermKey, String finalExamEndDateTermKey,
									            String gradingStartDateTermKey, String gradingEndDateTermKey) {
        this.registrationStartDateTermKey = registrationStartDateTermKey;
        this.registrationEndDateTermKey = registrationEndDateTermKey;
        this.classStartDateTermKey = classStartDateTermKey;
        this.classEndDateTermKey = classEndDateTermKey;
        this.addDateTermKey = addDateTermKey;
        this.dropDateTermKey = dropDateTermKey;
        this.finalExamStartDateTermKey = finalExamStartDateTermKey;
        this.finalExamEndDateTermKey = finalExamEndDateTermKey;
        this.gradingStartDateTermKey = gradingStartDateTermKey;
        this.gradingEndDateTermKey = gradingEndDateTermKey;
        _futureElements = null;
    }
    
    /**
     * Constructs a new RegistrationDateDerivationGroupInfo from another
     * RegistrationDateDerivationGroup.
     *
     * @param dateDerivationGroup the RegistrationDateDerivationGroup to copy
     */
    private RegistrationDateDerivationGroupInfo(RegistrationDateDerivationGroup dateDerivationGroup) {
        registrationStartDateTermKey = dateDerivationGroup.getRegistrationStartDateTermKey();
        registrationEndDateTermKey = dateDerivationGroup.getRegistrationEndDateTermKey();
        classStartDateTermKey = dateDerivationGroup.getClassStartDateTermKey();
        classEndDateTermKey = dateDerivationGroup.getRegistrationEndDateTermKey();
        addDateTermKey = dateDerivationGroup.getAddDateTermKey();
        dropDateTermKey = dateDerivationGroup.getDropDateTermKey();
        finalExamStartDateTermKey = dateDerivationGroup.getFinalExamStartDateTermKey();
        finalExamEndDateTermKey = dateDerivationGroup.getFinalExamEndDateTermKey();
        gradingStartDateTermKey = dateDerivationGroup.getGradingStartDateTermKey();
        gradingEndDateTermKey = dateDerivationGroup.getGradingEndDateTermKey();
        _futureElements = null;
    }

    @Override
    public String getRegistrationStartDateTermKey() {
        return registrationStartDateTermKey;
    }

    @Override
    public void setRegistrationStartDateTermKey(String registrationStartDateTermKey) {
        this.registrationStartDateTermKey = registrationStartDateTermKey;
    }

    @Override
    public String getRegistrationEndDateTermKey() {
        return registrationEndDateTermKey;
    }

    @Override
    public void setRegistrationEndDateTermKey(String registrationEndDateTermKey) {
        this.registrationEndDateTermKey = registrationEndDateTermKey;
    }

    @Override
    public String getClassStartDateTermKey() {
        return classStartDateTermKey;
    }

    @Override
    public void setClassStartDateTermKey(String classStartDateTermKey) {
        this.classStartDateTermKey = classStartDateTermKey;
    }

    @Override
    public String getClassEndDateTermKey() {
        return classEndDateTermKey;
    }

    @Override
    public void setClassEndDateTermKey(String classEndDateTermKey) {
        this.classEndDateTermKey = classEndDateTermKey;
    }

    @Override
    public String getAddDateTermKey() {
        return addDateTermKey;
    }

    @Override
    public void setAddDateTermKey(String addDateTermKey) {
        this.addDateTermKey = addDateTermKey;
    }

    @Override
    public String getDropDateTermKey() {
        return dropDateTermKey;
    }

    @Override
    public void setDropDateTermKey(String dropDateTermKey) {
        this.dropDateTermKey = dropDateTermKey;
    }

    @Override
    public String getFinalExamStartDateTermKey() {
        return finalExamStartDateTermKey;
    }

    @Override
    public void setFinalExamStartDateTermKey(String finalExamStartDateTermKey) {
        this.finalExamStartDateTermKey = finalExamStartDateTermKey;
    }

    @Override
    public String getFinalExamEndDateTermKey() {
        return finalExamEndDateTermKey;
    }

    @Override
    public void setFinalExamEndDateTermKey(String finalExamEndDateTermKey) {
        this.finalExamEndDateTermKey = finalExamEndDateTermKey;
    }

    @Override
    public String getGradingStartDateTermKey() {
        return gradingStartDateTermKey;
    }

    @Override
    public void setGradingStartDateTermKey(String gradingStartDateTermKey) {
        this.gradingStartDateTermKey = gradingStartDateTermKey;
    }

    @Override
    public String getGradingEndDateTermKey() {
        return gradingEndDateTermKey;
    }

    @Override
    public void setGradingEndDateTermKey(String gradingEndDateTermKey) {
        this.gradingEndDateTermKey = gradingEndDateTermKey;
    }
}
