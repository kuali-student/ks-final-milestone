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
 * Created by Paul on 2012/11/22
 */
package org.kuali.student.enrollment.class1.krms.form;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RequisitesForm extends UifFormBase {

    private String courseName;

    private String agendaType;

    private String ruleType;

    private List<String> activeSelections;

    private RuleEditor ruleEditor;

    public RequisitesForm() {
        this.ruleEditor = new RuleEditor();
    }

    public RuleEditor getRuleEditor() {
        return ruleEditor;
    }

    public void setRuleEditor(RuleEditor ruleEditor) {
        this.ruleEditor = ruleEditor;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAgendaType() {
        return agendaType;
    }

    public void setAgendaType(String agendaType) {
        this.agendaType = agendaType;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getPropositionSummary(){
        return ruleEditor.getPropositionSummary();
    }

    public Tree getPropositionTree() {
        return ruleEditor.getPropositionTree();
    }

    public String getSelectedPropositionId() {
        return ruleEditor.getSelectedPropositionId();
    }

    public String getCutPropositionId() {
        return ruleEditor.getCutPropositionId();
    }

    public List<String> getActiveSelections() {
        return activeSelections;
    }

    public void setActiveSelections(List<String> activeSelections) {
        this.activeSelections = activeSelections;
    }

}
