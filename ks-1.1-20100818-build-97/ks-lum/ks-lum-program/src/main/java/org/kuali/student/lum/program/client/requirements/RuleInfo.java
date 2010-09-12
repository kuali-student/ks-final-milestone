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

package org.kuali.student.lum.program.client.requirements;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import java.io.Serializable;
import java.util.List;

public class RuleInfo implements Idable, Serializable {

	private static final long serialVersionUID = 1L;
    private String id = "0";
    private String cluId;
    private StatementVO statementVO;       //top-level statement (tree ROOT)
    private String rationale;
    private String naturalLanguageForRuleEdit;
    private String naturalLanguageForRuleView;    
    private String expression; // current state of rule expression
    private String previewedExpression; // the state of the expression when it was previewed
    private EditHistory editHistory;
    private boolean simplifying;
    private String selectedStatementType;
    
    public String getSelectedStatementType() {
        return selectedStatementType;
    }
    public void setSelectedStatementType(String selectedStatementType) {
        this.selectedStatementType = selectedStatementType;
    }
    public StatementVO createNewStatementVO() {
        if (selectedStatementType == null) {
            throw new IllegalStateException("selectedStatementType must not be null");
        }
        StatementInfo newStatementTree = new StatementInfo();
        newStatementTree.setOperator(StatementOperatorTypeKey.AND);
        newStatementTree.setType(selectedStatementType);
        StatementVO statementVO = new StatementVO();                            
        statementVO.setStatementInfo(newStatementTree);
        return statementVO;
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }
    public String getCluId() {
        return cluId;
    } 
    public void setCluId(String cluId) {
        this.cluId = cluId;
    }
    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }   
    public String getNaturalLanguageForRuleEdit() {
        return naturalLanguageForRuleEdit;
    }
    public void setNaturalLanguageForRuleEdit(String naturalLanguageForRuleEdit) {
        this.naturalLanguageForRuleEdit = naturalLanguageForRuleEdit;
    }
    public String getNaturalLanguageForRuleView() {
        return naturalLanguageForRuleView;
    }
    public void setNaturalLanguageForRuleView(String naturalLanguageForRuleView) {
        this.naturalLanguageForRuleView = naturalLanguageForRuleView;
    }
    public StatementVO getStatementVO() {
        return statementVO;
    }    
    public void setStatementVO(StatementVO statementVO) {
        this.statementVO = statementVO;
    }        
    public String getStatementTypeKey() {
        String statementType = (statementVO == null || statementVO.getStatementInfo() == null)? null :
            statementVO.getStatementInfo().getType();
    	return statementType;
    }
    
    public Node getStatementTree() {
        Node tree = null;
        if (statementVO != null) {          
            tree = statementVO.getTree();
        }
        return tree;
    }
    
    public EditHistory getEditHistory() {
        return editHistory;
    }
    public void setEditHistory(EditHistory editHistory) {
        this.editHistory = editHistory;
    }
    
    private boolean statementVOIsGroupAble(
            List<StatementVO> selectedStatementVOs,
            List<ReqComponentVO> selectedReqComponentVOs) {
        boolean orable = false;
        StatementVO enclosingStatement = null;
        boolean reqComponentsInSameGroup = true;
        int numStatementsSelection = (selectedStatementVOs == null)? 0 : 
            selectedStatementVOs.size();
        int numReqComponentSelection = (selectedReqComponentVOs == null)? 0 :
            selectedReqComponentVOs.size();
        int numSelection = numStatementsSelection + numReqComponentSelection;
        
        // At least 2 items (StatementVO or RC) must be selected.
        if (numSelection >= 2) {
            if (numStatementsSelection > 0) {
                enclosingStatement = this.statementVO.getParentStatementVO(this.statementVO,
                        selectedStatementVOs.get(0));
            } else {
                enclosingStatement = this.statementVO.getEnclosingStatementVO(this.statementVO,
                        selectedReqComponentVOs.get(0));
            }
            
            if (numStatementsSelection > 0) {
                for (StatementVO selectedStatementVO : selectedStatementVOs) {
                    StatementVO enclosingStatement2 = this.statementVO.getParentStatementVO(
                            this.statementVO, selectedStatementVO);
                    if (enclosingStatement != enclosingStatement2) {
                        reqComponentsInSameGroup = false;
                        break;
                    }
                }
            }
            if (numReqComponentSelection > 0) {
                for (ReqComponentVO selectedReqCompoentVO : selectedReqComponentVOs) {
                    StatementVO enclosingStatement2 = this.statementVO.getEnclosingStatementVO(
                            this.statementVO, selectedReqCompoentVO);
                    if (enclosingStatement != enclosingStatement2) {
                        reqComponentsInSameGroup = false;
                        break;
                    }
                }
            }
            
            // the items selected must all belong to the same group
            if (!reqComponentsInSameGroup) return false;
            
            int childCount = (enclosingStatement == null)? 0 : enclosingStatement
                        .getChildCount();
            
            // number of selected requirement components must be less the the total number of
            // requirement components of the enclosing statement
            if (selectedReqComponentVOs != null && !selectedReqComponentVOs.isEmpty() &&
                    childCount > numSelection) {
                orable = true;
            } else {
                orable = false;
            }
        } else {
            orable = false;
        }
        return orable;
    }

    public boolean statementVOIsGroupAble() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        return statementVOIsGroupAble(selectedStatementVOs, selectedReqComponentVOs);
    }
    
    public void insertOR() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        // check if the selections can be grouped by OR operator.
        if (!statementVOIsGroupAble(selectedStatementVOs, selectedReqComponentVOs)) return;
        StatementVO enclosingStatementVO = statementVO.getEnclosingStatementVO(statementVO,
                selectedReqComponentVOs.get(0));
        // create new statement to hold the new OR group
        StatementVO newStatementVO = createNewStatementVO();
        StatementInfo newLuStatementInfo = newStatementVO.getStatementInfo();
        newLuStatementInfo.setOperator(StatementOperatorTypeKey.OR);
        newStatementVO.setStatementInfo(newLuStatementInfo);
        // remove the selected RCs from original statement and move them into the new
        // StatementVO
        for (ReqComponentVO selectedReqComponentVO : selectedReqComponentVOs) {
            enclosingStatementVO.removeReqComponentVO(selectedReqComponentVO);
            newStatementVO.addReqComponentVO(selectedReqComponentVO);
        }
        // remove the selected StatementVOs from original statement and move them into
        // the new StatementVO
        for (StatementVO selectedStatementVO : selectedStatementVOs) {
            enclosingStatementVO.removeStatementVO(selectedStatementVO);
            newStatementVO.addStatementVO(selectedStatementVO);
        }
        enclosingStatementVO.addStatementVO(newStatementVO);
    }
    
    public void insertAND() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        // check if the selections can be grouped by OR operator.
        if (!statementVOIsGroupAble(selectedStatementVOs, selectedReqComponentVOs)) return;
        StatementVO enclosingStatementVO = statementVO.getEnclosingStatementVO(statementVO,
                selectedReqComponentVOs.get(0));
        // create new statement to hold the new OR group
        StatementVO newStatementVO = createNewStatementVO();
        StatementInfo newLuStatementInfo = newStatementVO.getStatementInfo();
        newLuStatementInfo.setOperator(StatementOperatorTypeKey.AND);
        newStatementVO.setStatementInfo(newLuStatementInfo);
        // remove the selected RCs from original statement and move them into the new
        // StatementVO
        for (ReqComponentVO selectedReqComponentVO : selectedReqComponentVOs) {
            enclosingStatementVO.removeReqComponentVO(selectedReqComponentVO);
            newStatementVO.addReqComponentVO(selectedReqComponentVO);
        }
        // remove the selected StatementVOs from original statement and move them into
        // the new StatementVO
        for (StatementVO selectedStatementVO : selectedStatementVOs) {
            enclosingStatementVO.removeStatementVO(selectedStatementVO);
            newStatementVO.addStatementVO(selectedStatementVO);
        }
        enclosingStatementVO.addStatementVO(newStatementVO);
    }
    
    public boolean statementVOIsDegroupAble() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        return statementVOIsDegroupAble(selectedStatementVOs, selectedReqComponentVOs);
    }
    
    private boolean statementVOIsDegroupAble(
            List<StatementVO> selectedStatementVOs,
            List<ReqComponentVO> selectedReqComponentVOs) {
        boolean degroupAble = false;
        boolean selectedRootStatementVO = false;
        boolean otherItemsExist = false;
        // at least one item is selected
        if ((selectedStatementVOs != null && !selectedStatementVOs.isEmpty()) || 
                (selectedReqComponentVOs != null && !selectedReqComponentVOs.isEmpty())) {
            degroupAble = true;
        } else {
            return false;
        }
        
        // root statement should not be selected if there are other items.
        for (StatementVO selectedStatement : selectedStatementVOs) {
            if (selectedStatement == this.statementVO) {
                selectedRootStatementVO = true;
                break;
            }
        }
        otherItemsExist = this.statementVO.getChildCount() > 0;
        if (selectedRootStatementVO && otherItemsExist) {
            degroupAble = degroupAble && false;
        } else{
            degroupAble = degroupAble && true;
        }
        
        // must leave at least 2 children in a group unselected or
        // all children are RCs and are selected.
        // Except for root.  For root, if root statement does not have
        // any sub statements then it is okay to delete
        for (ReqComponentVO selectedRC : selectedReqComponentVOs) {
            StatementVO parentStatementVO = 
                this.statementVO.getEnclosingStatementVO(this.statementVO,
                        selectedRC);
            int numSelectedChildren = 0;
            int numSelectedChildrenRC = 0;
            if (parentStatementVO.getStatementVOCount() > 0) {
                for (StatementVO subStatementVO : 
                    parentStatementVO.getStatementVOs()) {
                    if (subStatementVO.isWrapperStatementVO() &&
                            subStatementVO.getReqComponentVOs().get(0).isCheckBoxOn()) {
                        numSelectedChildren++;
                    } else if (subStatementVO.isCheckBoxOn()) {
                        numSelectedChildren++;
                    }
                }
            } else if (parentStatementVO.getReqComponentVOCount() > 0) {
                for (ReqComponentVO rcVO : parentStatementVO.getReqComponentVOs()) {
                    if (rcVO.isCheckBoxOn()) {
                        numSelectedChildren++;
                        numSelectedChildrenRC++;
                    }
                }
            }
            if (parentStatementVO.getChildCount() - numSelectedChildren > 1) {
                degroupAble = degroupAble && true;
            } else if (numSelectedChildrenRC == parentStatementVO.getChildCount()) {
                degroupAble = degroupAble && true;
            } else if (parentStatementVO == this.statementVO &&
                    parentStatementVO.getStatementVOCount() == 0) {
                degroupAble = degroupAble && true;
            } else {
                degroupAble = false;
            }
        }
        return degroupAble;
    }
    
    public void deleteItem() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        if (!statementVOIsDegroupAble(selectedStatementVOs, selectedReqComponentVOs)) return;
        // remove the selected RCs
        for (ReqComponentVO selectedReqComponent : selectedReqComponentVOs) {
            StatementVO parentStatementVO = null;
            parentStatementVO =
                this.statementVO.getEnclosingStatementVO(this.statementVO, selectedReqComponent);
            parentStatementVO.removeReqComponentVO(selectedReqComponent);
        }
        // remove the selected operator cells/statements.
        for (StatementVO selectedStatement : selectedStatementVOs) {
            StatementVO parentStatementVO = null; 
            parentStatementVO = 
                this.statementVO.getParentStatementVO(this.statementVO, selectedStatement);
            
            if (selectedStatement == statementVO) {
                statementVO = null;
            } else if (parentStatementVO != null) {
                parentStatementVO.removeStatementVO(selectedStatement);
                if (selectedStatement.getStatementVOCount() > 0) {
                    for (StatementVO subStatement : selectedStatement.getStatementVOs()) {
                        parentStatementVO.addStatementVO(subStatement);
                    }
                } else if (selectedStatement.getReqComponentVOCount() > 0) {
                    for (ReqComponentVO leafReqComponent : selectedStatement.getReqComponentVOs()) {
                        parentStatementVO.addReqComponentVO(leafReqComponent);
                    }
                }
            }
        }
        // clears off the root if the root no longer has any children after the deletions
        if (statementVO.getChildCount() == 0) {
            statementVO = null;
        }
    }
    
    public boolean isAddToGroupOK() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        return isAddToGroupOK(selectedStatementVOs, selectedReqComponentVOs);
    }
    
    private boolean isAddToGroupOK(List<StatementVO> selectedStatementVOs,
            List<ReqComponentVO> selectedReqComponentVOs) {
        boolean addToGroupOk = false;
        int numSelectedS = (selectedStatementVOs == null)? 0 : selectedStatementVOs.size();
        int numSelectedRC = (selectedReqComponentVOs == null)? 0 :
            selectedReqComponentVOs.size();
        // one or more rules are selected as well as a single AND or OR cell
        addToGroupOk = numSelectedS == 1 && numSelectedRC > 0;
        return addToGroupOk;
    }
    
    public void addToGroup() {
        List<StatementVO> selectedStatementVOs = getSelectedStatementVOs();
        List<ReqComponentVO> selectedReqComponentVOs = getSelectedReqComponentVOs();
        StatementVO selectedS = null;
        if (!isAddToGroupOK(selectedStatementVOs, selectedReqComponentVOs)) return;
        selectedS = selectedStatementVOs.get(0);
        if (selectedReqComponentVOs != null &&
                !selectedReqComponentVOs.isEmpty()) {
            for (ReqComponentVO selectedRC : selectedReqComponentVOs) {
                StatementVO parentS = this.statementVO.getEnclosingStatementVO(
                    this.statementVO, selectedRC);
                if (parentS == null) continue;
                parentS.removeReqComponentVO(selectedRC);
                // The following if else statement cleans up parent statements that
                // has no more children other than the selectedS
                if (parentS.getChildCount() == 1 &&
                        parentS.getSelectedStatementVOs() != null &&
                        parentS.getSelectedStatementVOs().contains(selectedS)) {
                    // if parent is root replace the current root with
                    // the selected statement.
                    // otherwise remove the parent from grand parent and add the selected
                    // statement
                    if (parentS == statementVO) {
                        this.statementVO = selectedS;
                    } else {
                        StatementVO grandParentS = this.statementVO.getParentStatementVO(
                                this.statementVO, parentS);
                        grandParentS.removeStatementVO(parentS);
                        grandParentS.addStatementVO(selectedS);
                    }
                }
                selectedS.addReqComponentVO(selectedRC);
            }
            statementVO.simplify();
        }
    }
    
    public List<StatementVO> getSelectedStatementVOs() {
        return (statementVO == null)? null : statementVO.getSelectedStatementVOs();
    }
    
    public List<ReqComponentVO> getSelectedReqComponentVOs() {
        return (statementVO == null)? null : statementVO.getSelectedReqComponentVOs();
    }
    
    public void populateExpression() {
        expression = (statementVO == null)? null : statementVO.convertToExpression();
    }
    
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public String getPreviewedExpression() {
        return previewedExpression;
    }
    public void setPreviewedExpression(String previewedExpression) {
        this.previewedExpression = previewedExpression;
    }
    public boolean isSimplifying() {
        return simplifying;
    }
    public void setSimplifying(boolean simplifying) {
        this.simplifying = simplifying;
    }
    
}
