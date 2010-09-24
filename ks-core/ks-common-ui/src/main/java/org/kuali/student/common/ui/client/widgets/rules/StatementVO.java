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

package org.kuali.student.common.ui.client.widgets.rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class StatementVO extends Token implements Serializable {

    private static final long serialVersionUID = 1L;
    private StatementInfo statementInfo;
    private List<ReqComponentVO> reqComponentVOs = new ArrayList<ReqComponentVO>();
    private List<StatementVO> statementVOs = new ArrayList<StatementVO>();


    //TODO remove after refactoring rule table related classes, removing StatementVO
    public void setStatementTreeViewInfo(StatementTreeViewInfo stmtTreeInfo) {
        try {
            composeStatementVO(stmtTreeInfo, this);
        } catch (Exception e) {
            Window.alert(e.getMessage());
            GWT.log("failed", e);
        }
    }

    public StatementTreeViewInfo getStatementTreeViewInfo() {

        StatementTreeViewInfo stmtTreeInfo = new StatementTreeViewInfo();

        try {
            composeStatementTreeViewInfo(this, stmtTreeInfo);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return stmtTreeInfo;
    }

    public StatementVO() {
    }

    public StatementVO(StatementInfo statementInfo) {
        setStatementInfo(statementInfo);
    }

    public void printTree(Node node) {
        int level = 0;
        ReqComponentVO content;
//        level++;

        if (node == null) {
            GWT.log("null node found",null);
            return;
        }

        level = node.getDistance(node);
        if (node.getUserObject() != null) {
            Token token = (Token) node.getUserObject();
            //content = (ReqComponentVO) token.value;
            GWT.log("Node level " + level + ", content: " + token.value,null);
        }
        else GWT.log("Node user object null, level: " + level, null);
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                Token token = (Token) child.getUserObject();
                content = (ReqComponentVO) child.getUserObject();
                GWT.log("Node level " + child.getDistance(child) + ", content: " + content, null);
            } else {
                printTree(child);
            }
        }
    }

    /**
     * Gets the immediate parent statement of reqComponentVO
     * Example: (a and b) or (c and d) or (e)
     *     Where the statements are enclosed in brackets.  So in this example there are 3 statements.  Namely (a and b), (c and d), and (e).
     *     There are 5 requirement components a, b, c, d, and e. If requirement component b is passed in as a parameter, then (a and b)
     *     is returned.  If e is passed in, then (e) is returned.  If d is is passed in, then (c and d) is returned.
     * @param reqComponentVO
     * @return
     */
    public StatementVO getEnclosingStatementVO(StatementVO root, ReqComponentVO reqComponentVO) {
        return doGetEnclosingStatementVO(root, reqComponentVO);
    }

    private StatementVO doGetEnclosingStatementVO(StatementVO statementVO, ReqComponentVO reqComponentVO) {
        StatementVO result = null;
        List<StatementVO> statementVOs = (statementVO == null)? null : statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = (statementVO == null)? null : statementVO.getReqComponentVOs();

        if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO subStatementVO : statementVOs) {
                List<ReqComponentVO>subStatementReqComponentVOs = subStatementVO.getReqComponentVOs();
                if (subStatementReqComponentVOs.size() == 1) {
                    if (subStatementReqComponentVOs.get(0) == reqComponentVO) {
                        result = statementVO;
                        break;
                    }
                }
                else {
                    result = doGetEnclosingStatementVO(subStatementVO, reqComponentVO);
                    // found the enclosing statement exit
                    if (result != null) {
                        break;
                    }
                }
            }
        } else if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            for (ReqComponentVO leafReqComponentVO : reqComponentVOs) {
                if (leafReqComponentVO == reqComponentVO) {
                    result = statementVO;
                    break;
                }
            }
        }
        return result;
    }

    public StatementVO getParentStatementVO(StatementVO root, StatementVO nodeStatement) {
        StatementVO parentStatementVO = null;
        if (nodeStatement == root) {
            parentStatementVO = null;
        } else {
            parentStatementVO = doGetParentStatementVO(root, nodeStatement);
        }
        return parentStatementVO;
    }

    private StatementVO doGetParentStatementVO(StatementVO statementVO, StatementVO nodeStatement) {
        StatementVO parentStatementVO = null;
        if (statementVO.getStatementVOCount() > 0) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                if (childStatementVO == nodeStatement) {
                    return statementVO;
                } else {
                    parentStatementVO = doGetParentStatementVO(childStatementVO, nodeStatement);
                }
            }
        }
        return parentStatementVO;
    }

    /**
     * returns the number of leaf requirement componentVO of the this statement
     * excluding those compound sub statements.
     * e.g. if this method is called on S1, the return value will be 2.
     * <pre>
     *                    S1:OR
     *           S2:OR    S3:OR   S4:AND
     *             a        b     c d e
     * </pre>
     * @return
     */
    /*
    public int getImmediateChildReqComponentCount() {
        int result = 0;
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            result = reqComponentVOs.size();
        } else if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO subStatementVO : statementVOs) {
                List<ReqComponentVO> subStatementReqComponentVOs =
                    subStatementVO.getReqComponentVOs();
                if (subStatementReqComponentVOs != null &&
                        subStatementReqComponentVOs.size() == 1) {
                    result++;
                }
            }
        }
        return result;
    } */

    private void validate() {
        if (statementVOs != null && !statementVOs.isEmpty() && reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            throw new IllegalStateException("Requirement components and statements can not exist together in a statement");
        }
        checkDuplicateRC(this, new ArrayList<ReqComponentVO>());
    }

    private void checkDuplicateRC(StatementVO statementVO, List<ReqComponentVO> seenRCs) {
        if (statementVO.getStatementVOs() != null &&
                !statementVO.getStatementVOs().isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                checkDuplicateRC(childStatementVO, seenRCs);
            }
        } else {
            if (statementVO.getReqComponentVOs() != null &&
                    !statementVO.getReqComponentVOs().isEmpty()) {
                for (ReqComponentVO childReqComponent : statementVO.getReqComponentVOs()) {
                    if (seenRCs.contains(childReqComponent)) {
                        throw new IllegalStateException(
                                "statement and sub statements cannot have duplicated components");
                    } else {
                        seenRCs.add(childReqComponent);
                    }
                }
            }
        }
    }

    public void addStatementVO(StatementVO statementVO) {
        doAddStatementVO(statementVO);
        validate();
    }

    private void doAddStatementVO(StatementVO statementVO) {
        // if there are currently requirement components in this StatementVO
        // move all the existing requirement components into separate wrapping
        // StatementVOs
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            List<ReqComponentVO> tempReqComponentVOs = new ArrayList<ReqComponentVO>(reqComponentVOs);
            for (ReqComponentVO currReqComponentVO : tempReqComponentVOs) {
                StatementVO wrapStatementVO = new StatementVO(statementInfo);
                wrapStatementVO.addReqComponentVO(currReqComponentVO);
                reqComponentVOs.remove(currReqComponentVO);
                statementVOs.add(wrapStatementVO);
            }
        }
        statementVOs.add(statementVO);
    }

    public void addReqComponentVO(ReqComponentVO reqComponentVO) {
        doAddReqComponentVO(reqComponentVO);
        validate();
    }

    private void doAddReqComponentVO(ReqComponentVO reqComponentVO) {
        // if there are sub statements in this statement already
        // add a new sub statement with the same operator as this statement
        // and a the requirement component
        if (statementVOs != null && !statementVOs.isEmpty()) {
            StatementInfo newStatementInfo = new StatementInfo();
            StatementVO newStatementVO = new StatementVO();
            newStatementInfo.setOperator(statementInfo.getOperator());
            newStatementVO.setStatementInfo(newStatementInfo);
            newStatementVO.getReqComponentVOs().add(reqComponentVO);
            statementVOs.add(newStatementVO);
        } else {
            reqComponentVOs.add(reqComponentVO);
        }
    }

    public void removeStatementVO(StatementVO statementVO) {
        statementVOs.remove(statementVO);
        validate();
    }

    public void removeReqComponentVO(ReqComponentVO reqComponentVO) {
        doRemoveReqComponentVO(reqComponentVO);
        validate();
    }

    private void doRemoveReqComponentVO(ReqComponentVO reqComponentVO) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            List<StatementVO> tempStatementVOs = new ArrayList<StatementVO>(statementVOs);
            for (StatementVO subStatementVO : tempStatementVOs) {
                List<ReqComponentVO> subStatementReqComponentVOs =
                    (subStatementVO == null)? null : subStatementVO.getReqComponentVOs();
                if (subStatementReqComponentVOs != null &&
                        subStatementReqComponentVOs.size() == 1 &&
                        subStatementReqComponentVOs.get(0) == reqComponentVO) {
                    subStatementVO.removeReqComponentVO(reqComponentVO);
                    // cleans up empty statements with neither statements nor requirement components.
                    statementVOs.remove(subStatementVO);
                }
            }
        } else {
            reqComponentVOs.remove(reqComponentVO);
        }
    }

    public StatementInfo getStatementInfo() {
        return statementInfo;
    }

    public void setStatementInfo(StatementInfo statementInfo) {
        this.statementInfo = statementInfo;
        this.setType(statementInfo.getOperator() == StatementOperatorTypeKey.OR ? Token.Or : Token.And);
    }

    public List<ReqComponentVO> getReqComponentVOs() {
        return reqComponentVOs;
    }

    public List<StatementVO> getStatementVOs() {
        return statementVOs;
    }

    public void clearStatementAndReqComponents() {
        if (statementVOs != null) {
            statementVOs.clear();
        }
        if (reqComponentVOs != null) {
            reqComponentVOs.clear();
        }
    }

    public void shiftReqComponent(String shiftType, final ReqComponentVO reqComponentVO) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            // the statementVO that wraps the reqComponentVO
            StatementVO reqComponentVOWrap = null;
            for (StatementVO currStatementVO : statementVOs) {
                List<ReqComponentVO> currReqComponentVOs = (currStatementVO == null)? null :
                        currStatementVO.getReqComponentVOs();
                if (currReqComponentVOs != null && currReqComponentVOs.size() == 1 &&
                        currReqComponentVOs.get(0) == reqComponentVO) {
                    reqComponentVOWrap = currStatementVO;
                }
            }
            if (reqComponentVOWrap != null) {
                swapElement(statementVOs, reqComponentVOWrap, shiftType);
            }
        } else if (reqComponentVOs != null && reqComponentVOs.size() > 1) {
            swapElement(reqComponentVOs, reqComponentVO, shiftType);
        }
    }

    private <T> void swapElement(List<T> elements, T element, String direction) {
        int elementIndex = 0;
        if (elements != null && elements.size() > 1) {
            for (T currElement : elements) {
                if (direction != null && direction.equals("RIGHT")) {
                    if (currElement == element && elementIndex + 1 < elements.size()) {
                        Collections.swap(elements, elementIndex, elementIndex + 1);
                        break;
                    }
                } else if (direction != null && direction.equals("LEFT")) {
                    if (currElement == element && elementIndex > 0) {
                        Collections.swap(elements, elementIndex, elementIndex - 1);
                        break;
                    }
                }
                elementIndex++;
            }
        }
    }

    /**
     * returns A, B, C, ... etc depending on the number of
     * Requirement components in the list.
     * @param rcs
     * @return
     */
    private String getNextGuiRCId(List<ReqComponentVO> rcs) {
        int charCode = 65; // ASCII code for capitalized A
        int newCharCode = -1;
        String guiRCId = null;

        while (newCharCode == -1) {
            boolean charUsed = false;
            if (rcs != null) {
                for (ReqComponentVO rc : rcs) {
                    String currGuiRCId = rc.getGuiReferenceLabelId();
                    currGuiRCId = (currGuiRCId == null)? "" : currGuiRCId;
                    if (currGuiRCId.equals(Character.toString((char)charCode))) {
                        charUsed = true;
                        charCode++;
                    }
                }
            }
            if (!charUsed) {
                newCharCode = charCode;
            }
        }

        // the next GUI id will be A - Z, and A1, A2, A3 afterwards.
        if (newCharCode < 65 + 26) {
            guiRCId = Character.toString((char)newCharCode);
        } else {
            guiRCId = Character.toString((char)(65 + 26));
            guiRCId = guiRCId + Integer.toString(
                    newCharCode - 65 + 26 - 1);
        }
        return guiRCId;
    }

    private void assignGuiRCId() {
        doAssignGuiRCId(this, new ArrayList<ReqComponentVO>());
    }

    private void doAssignGuiRCId(StatementVO statementVO, List<ReqComponentVO> rcs) {
        List<StatementVO> statementVOs = statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();

        if (statementVOs != null) {
//            node.setUserObject(statementVO);
            for (int i = 0; i < statementVOs.size(); i++) {
                StatementVO childStatementVO = statementVOs.get(i);
                doAssignGuiRCId(childStatementVO, rcs);
            }
        }

        if (reqComponentVOs != null) {
            for (int rcIndex = 0, rcCount = reqComponentVOs.size(); rcIndex < rcCount; rcIndex++) {
                ReqComponentVO childReqComponentVO = reqComponentVOs.get(rcIndex);
                if (childReqComponentVO.getGuiReferenceLabelId() == null ||
                        childReqComponentVO.getGuiReferenceLabelId().trim().length() == 0) {
                    String guiRCId = null;
                    guiRCId = getNextGuiRCId(rcs);
                    childReqComponentVO.setGuiReferenceLabelId(guiRCId);
                }
                rcs.add(childReqComponentVO);
            }
        }
    }

    public Node getTree() {
        Node node = new Node();
        assignGuiRCId();
        addChildrenNodes(node, this);
        //printTree(node);
        return node;
    }

    private void addChildrenNodes(Node node, StatementVO statementVO) {
        List<StatementVO> statementVOs = statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();

        if (statementVOs != null) {
            node.setUserObject(statementVO);
            setOperatorNode(node, statementVO);
            for (int i = 0; i < statementVOs.size(); i++) {
                StatementVO childStatementVO = statementVOs.get(i);
                childStatementVO.setTokenOperator(true);
                Node childNode = new Node();
                node.addNode(childNode);
                addChildrenNodes(childNode, childStatementVO);
            }
        }

        if (reqComponentVOs != null) {
            for (int rcIndex = 0, rcCount = reqComponentVOs.size(); rcIndex < rcCount; rcIndex++) {
                ReqComponentVO childReqComponentVO = reqComponentVOs.get(rcIndex);
                if (rcCount > 1) {
                    node.addNode(new Node(childReqComponentVO));
                } else {
                    node.setUserObject(childReqComponentVO);
                }
            }
        }
    }

    private void setOperatorNode(Node node, StatementVO statementVO) {
        if (statementVO.getStatementInfo() != null && statementVO.getStatementInfo().getOperator() == StatementOperatorTypeKey.AND) {
            statementVO.type = Token.And;
            statementVO.value = "and";
            node.setUserObject(statementVO);
        } else if (statementVO.getStatementInfo() != null && statementVO.getStatementInfo().getOperator() == StatementOperatorTypeKey.OR) {
            statementVO.type = Token.Or;
            statementVO.value = "or";
            node.setUserObject(statementVO);
        }
    }

    public List<StatementVO> getSelectedStatementVOs() {
        List<StatementVO> selectedStatementVOs = new ArrayList<StatementVO>();
        return doGetSelectedStatmentVOs(this, selectedStatementVOs);
    }

    private List<StatementVO> doGetSelectedStatmentVOs(StatementVO statementVO, List<StatementVO> selectedStatementVOs) {
        List<StatementVO> childrenStatementVOs = statementVO.getStatementVOs();
        if (statementVO.isCheckBoxOn()) {
            selectedStatementVOs.add(statementVO);
        }
        // check children
        if (childrenStatementVOs != null && !childrenStatementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                doGetSelectedStatmentVOs(childStatementVO, selectedStatementVOs);
            }
        }
        return selectedStatementVOs;
    }

    /**
     * goes through the entire tree recursively and returns the list of all RCs
     * @return
     */
    public List<ReqComponentVO> getAllReqComponentVOs() {
        return doGetAllReqComponentVOs(this, new ArrayList<ReqComponentVO>());
    }

    public ReqComponentVO getReqComponentVOByGuiKey(String guiKey) {
        ReqComponentVO result = null;
        List<ReqComponentVO> allRCs = getAllReqComponentVOs();
        for (ReqComponentVO rc : allRCs) {
            if (rc.getGuiReferenceLabelId() != null && rc.getGuiReferenceLabelId().equals(guiKey)) {
                result = rc;
                break;
            }
        }
        return result;
    }

    private List<ReqComponentVO> doGetAllReqComponentVOs(StatementVO statementVO,
            List<ReqComponentVO> allRCs) {
        List<ReqComponentVO> childrenReqComponentVOs = statementVO.getReqComponentVOs();
        List<StatementVO> childrenStatementVOs = statementVO.getStatementVOs();
        if (childrenReqComponentVOs != null && !childrenReqComponentVOs.isEmpty()) {
            for (ReqComponentVO childReqComponentVO : childrenReqComponentVOs) {
                allRCs.add(childReqComponentVO);
            }
        }
        if (childrenStatementVOs != null && !childrenStatementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                doGetAllReqComponentVOs(childStatementVO, allRCs);
            }
        }
        return allRCs;
    }

    public List<ReqComponentVO> getSelectedReqComponentVOs() {
        List<ReqComponentVO> selectedReqComponentVOs = new ArrayList<ReqComponentVO>();
        return doGetSelectedReqComponentVOs(this, selectedReqComponentVOs);
    }

    private List<ReqComponentVO> doGetSelectedReqComponentVOs(StatementVO statementVO, List<ReqComponentVO> selectedReqComponentVOs) {
        List<ReqComponentVO> childrenReqComponentVOs = statementVO.getReqComponentVOs();
        List<StatementVO> childrenStatementVOs = statementVO.getStatementVOs();
        if (childrenReqComponentVOs != null && !childrenReqComponentVOs.isEmpty()) {
            for (ReqComponentVO childReqComponentVO : childrenReqComponentVOs) {
                if (childReqComponentVO.isCheckBoxOn()) {
                    selectedReqComponentVOs.add(childReqComponentVO);
                }
            }
        }
        if (childrenStatementVOs != null && !childrenStatementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                doGetSelectedReqComponentVOs(childStatementVO, selectedReqComponentVOs);
            }
        }
        return selectedReqComponentVOs;
    }

    public boolean isFirstSelectedReqComp() {
        return doIsFirstSelectedReqComp(this);
    }

    public boolean doIsFirstSelectedReqComp(StatementVO statementVO) {
        List<ReqComponentVO> childrenReqComponentVOs = statementVO.getReqComponentVOs();
        boolean isFirst = false;
        if (childrenReqComponentVOs != null && !childrenReqComponentVOs.isEmpty()) {
            isFirst = true;
            for (ReqComponentVO childReqComponentVO : childrenReqComponentVOs) {
                if (childReqComponentVO.isCheckBoxOn()) {
                    return isFirst;
                }
                isFirst = false;
            }
        }
        return isFirst;
    }

    public boolean isLastSelectedReqComp() {
        return doIsLastSelectedReqComp(this);
    }

    public boolean doIsLastSelectedReqComp(StatementVO statementVO) {
        List<ReqComponentVO> childrenReqComponentVOs = statementVO.getReqComponentVOs();
        boolean isLast = false;
        if (childrenReqComponentVOs != null && !childrenReqComponentVOs.isEmpty()) {
            isLast = true;
            for (ReqComponentVO childReqComponentVO : childrenReqComponentVOs) {
                isLast = childReqComponentVO.isCheckBoxOn();
            }
        }
        return isLast;
    }

    public boolean isNodeSelected() {
        return ((getSelectedStatementVOs().size() + getSelectedReqComponentVOs().size()) > 0);
    }

    public int getNestingDepth() {
        return doGetNestingDepth(this);
    }

    private int doGetNestingDepth(StatementVO statementVO) {
        int depth = 0;
        List<StatementVO> statementVOs = getStatementVOs();
        if (this == statementVO) {
            return depth;
        }
        if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVOs) {
                depth = depth + doGetNestingDepth(childStatementVO);
            }
        }
        return depth;
    }

    public int getReqComponentVOCount() {
        return (reqComponentVOs == null)? 0 : reqComponentVOs.size();
    }

    public int getStatementVOCount() {
        return (statementVOs == null)? 0 : statementVOs.size();
    }

    public int getChildCount() {
        return getReqComponentVOCount() + getStatementVOCount();
    }

    public boolean isWrapperStatementVO() {
        boolean result = false;
        if (getReqComponentVOCount() == 1 && getStatementVOCount() == 0) {
            result = true;
        }
        return result;
    }

    public void addStatementVOs(List<StatementVO> statementVOs) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO s : statementVOs) {
                this.addStatementVO(s);
            }
        }
    }

    public void addReqComponentVOs(List<ReqComponentVO> reqComponentVOs) {
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            for (ReqComponentVO rc : reqComponentVOs) {
                this.addReqComponentVO(rc);
            }
        }
    }

    /**************************************************************************************************
     * simplifies statement
     * @return true if statement has been changed as a result of the call
     */
    public boolean simplify() {
        boolean structureChanged = false;
        structureChanged = structureChanged || doSimplify(this, null);
        structureChanged = structureChanged || doCleanupStatementVO(this, null);
        structureChanged = structureChanged || doUnwrapRCs(this, 0);
        return structureChanged;
    }

    private boolean doSimplify(StatementVO statementVO, StatementVO parent) {
        boolean structureChanged = false;
        StatementOperatorTypeKey op = (statementVO == null || statementVO.getStatementInfo() == null)? null :
                                        statementVO.getStatementInfo().getOperator();
        StatementOperatorTypeKey parentOp = (parent == null || parent.getStatementInfo() == null)? null : parent.getStatementInfo().getOperator();

        if (parentOp == op && !statementVO.isWrapperStatementVO()) {
            structureChanged = true;
            if (statementVO.getReqComponentVOCount() > 0) {
                parent.removeStatementVO(statementVO);
                for (ReqComponentVO rc : statementVO.getReqComponentVOs()) {
                    parent.addReqComponentVO(rc);
                }
            } else if (statementVO.getStatementVOCount() > 0) {
                if (parent != null) {
                    parent.removeStatementVO(statementVO);
                }
                List<StatementVO> subSs = new ArrayList<StatementVO>(statementVO.getStatementVOs());
                for (StatementVO subS : subSs) {
                    doSimplify(subS, statementVO);
                }
                parent.addStatementVOs(statementVO.getStatementVOs());
            }
        } else if (statementVO!=null && statementVO.getStatementVOCount() > 0) {
            List<StatementVO> subSs = new ArrayList<StatementVO>(statementVO.getStatementVOs());
            for (StatementVO subS : subSs) {
                structureChanged = structureChanged || doSimplify(subS, statementVO);
            }
        }
        return structureChanged;
    }

    private boolean doCleanupStatementVO(StatementVO statementVO, StatementVO parent) {
        boolean structureChanged = false;
        if (statementVO.getStatementVOCount() == 0 && statementVO.getReqComponentVOCount() == 0) {
            if (parent != null) {
                parent.removeStatementVO(statementVO);
                structureChanged = true;
            }
        } else if (statementVO.getStatementVOCount() > 0) {
            for (StatementVO subS : statementVO.getStatementVOs()) {
                structureChanged = structureChanged || doCleanupStatementVO(subS, statementVO);
            }
        }
        return structureChanged;
    }

    private boolean doUnwrapRCs(StatementVO statementVO, int level) {
        boolean structureChanged = false;
        List<ReqComponentVO> wrappedRCs = new ArrayList<ReqComponentVO>();
        if (statementVO.getStatementVOCount() > 0) {
            List<StatementVO> subSs = new ArrayList<StatementVO>(statementVO.getStatementVOs());
            for (StatementVO subS : subSs) {
                if (!subS.isWrapperStatementVO()) {
                    structureChanged = structureChanged || doUnwrapRCs(subS, level + 1);
                }
            }

            for (StatementVO subS : subSs) {
                if (subS.isWrapperStatementVO()) {
                    wrappedRCs.add(subS.getReqComponentVOs().get(0));
                }
            }
            if (wrappedRCs != null && wrappedRCs.size() == statementVO.getChildCount()) {
                structureChanged = true;
                for (StatementVO subS : subSs) {
                    statementVO.removeStatementVO(subS);
                }
                for (ReqComponentVO wrappedRC : wrappedRCs) {
                    statementVO.addReqComponentVO(wrappedRC);
                }
            }
        }
        return structureChanged;
    }

    /*************************************************************************************************/

    public String getPrintableStatement() {
        return doConvertToExpression(new StringBuilder(), this, true).toString();
    }

    public String convertToExpression() {
        assignGuiRCId();
        return doConvertToExpression(new StringBuilder(), this, false).toString();
    }

    private StringBuilder doConvertToExpression(StringBuilder inSbResult,
            StatementVO statementVO,
            boolean extraBrackets) {
        List<StatementVO> currStatementVOs = (statementVO == null)? null : statementVO.getStatementVOs();
        List<ReqComponentVO> currReqComponentVOs = (statementVO == null)? null : statementVO.getReqComponentVOs();
        if (currStatementVOs != null && !currStatementVOs.isEmpty()) {
            int statementCounter = 0;
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                if (statementCounter > 0) {
                    StatementOperatorTypeKey operator = (statementVO == null ||
                                statementVO.getStatementInfo() == null)? null :
                                    statementVO.getStatementInfo().getOperator();
                    inSbResult.append(" " + operator + " ");
                }
                if (extraBrackets || !childStatementVO.isWrapperStatementVO()) {
                    inSbResult.append("(");
                }
                inSbResult.append(doConvertToExpression(new StringBuilder(), childStatementVO,
                        extraBrackets).toString());
                if (extraBrackets || !childStatementVO.isWrapperStatementVO()) {
                    inSbResult.append(")");
                }
                statementCounter++;
            }
        } else if (currReqComponentVOs != null && !currReqComponentVOs.isEmpty()) {
            int rcCounter = 0;
            for (ReqComponentVO childReqComponentInfo : currReqComponentVOs) {
                if (rcCounter > 0) {
                    if(statementVO != null &&
                       statementVO.getStatementInfo() != null &&
                       statementVO.getStatementInfo().getOperator() != null) {
	                	StatementOperatorTypeKey operator = statementVO.getStatementInfo().getOperator();
	                    inSbResult.append(" " + operator.toString().toLowerCase() + " ");
                    }
                }
                inSbResult.append(childReqComponentInfo.getGuiReferenceLabelId());
                rcCounter++;
            }
        }
        return inSbResult;
    }

    public void clearSelections() {
        doClearSelections(this);
    }

    private void doClearSelections(StatementVO statementVO) {
        statementVO.setCheckBoxOn(false);
        if (statementVO.getStatementVOCount() > 0) {
            for (StatementVO childS : statementVOs) {
                doClearSelections(childS);
            }
        } else if (statementVO.getReqComponentVOCount() > 0) {
            for (ReqComponentVO rc : statementVO.getReqComponentVOs()) {
                rc.setCheckBoxOn(false);
            }
        }
    }

    public boolean isSimple() {
        boolean simple = false;
        if (getStatementVOCount() == 0 && getReqComponentVOCount() <= 1) {
            simple = true;
        }
        return simple;
    }

    public boolean isEmpty() {
        boolean simple = false;
        if (getStatementVOCount() == 0 && getReqComponentVOCount() == 0) {
            simple = true;
        }
        return simple;
    }

    public String composeStatementTreeViewInfo(StatementVO statementVO, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        String rc = "";
        List<StatementVO> statementVOs = statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();

        if ((statementVOs != null) && (reqComponentVOs != null) && (statementVOs.size() > 0) && (reqComponentVOs.size() > 0))
        {
            return "Internal error: found both Statements and Requirement Components on the same level of boolean expression";
        }

        statementVO.setFieldsTo(statementTreeViewInfo);

        if ((statementVOs != null) && (statementVOs.size() > 0)) {
            // retrieve all statements
            List<StatementTreeViewInfo> subStatementTVInfos = new ArrayList<StatementTreeViewInfo>();
            for (StatementVO statement : statementVOs) {
                StatementTreeViewInfo subStatementTVInfo = new StatementTreeViewInfo();
                statement.setFieldsTo(subStatementTVInfo);
                rc = composeStatementTreeViewInfo(statement, subStatementTVInfo); // inside set the children of this statementTreeViewInfo
                subStatementTVInfos.add(subStatementTVInfo);
            }
            statementTreeViewInfo.setStatements(subStatementTVInfos);
        } else {
            // retrieve all req. component LEAFS
            List<ReqComponentInfo> reqComponentList = new ArrayList<ReqComponentInfo>();
            for (ReqComponentVO reqComponent : reqComponentVOs) {
                ReqComponentInfo newReqComp = ObjectClonerUtil.clone(reqComponent.getReqComponentInfo());
                reqComponentList.add(newReqComp);
            }
            statementTreeViewInfo.setReqComponents(reqComponentList);
        }

        return rc;
    }

    private void setFieldsTo(final StatementTreeViewInfo stvInfo) {
        stvInfo.setAttributes(getStatementInfo().getAttributes());
        stvInfo.setDesc(getStatementInfo().getDesc());
        stvInfo.setId(getStatementInfo().getId());
        stvInfo.setMetaInfo(getStatementInfo().getMetaInfo());
        stvInfo.setName(getStatementInfo().getName());
        stvInfo.setOperator(getStatementInfo().getOperator());
        stvInfo.setState(getStatementInfo().getState());
        stvInfo.setType(getStatementInfo().getType());
    }

    public String composeStatementVO(StatementTreeViewInfo statementTreeViewInfo, StatementVO statementVO) throws Exception {
        String rc = "";
        List<StatementTreeViewInfo> statements = statementTreeViewInfo.getStatements();
        List<ReqComponentInfo> reqComponentInfos = statementTreeViewInfo.getReqComponents();

        if ((statements != null) && (reqComponentInfos != null) && (statements.size() > 0) && (reqComponentInfos.size() > 0))
        {
            return "Internal error: found both Statements and Requirement Components on the same level of boolean expression";
        }

        statementVO.setFields(statementTreeViewInfo);
        statementVO.setTokenOperator(true);

        if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            List<StatementVO> newStatementList = new ArrayList<StatementVO>();
            for (StatementTreeViewInfo statement : statements) {
                StatementVO newStatementVO = new StatementVO();
                newStatementVO.setFields(statement);
                newStatementVO.getStatementInfo().setId("123");
                rc = composeStatementVO(statement, newStatementVO); // inside set the children of this statementTreeViewInfo
                newStatementList.add(newStatementVO);
            }
            statementVO.statementVOs = newStatementList;
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            List<ReqComponentVO> reqComponentList = new ArrayList<ReqComponentVO>();
            for (ReqComponentInfo reqComponent : reqComponentInfos) {
                ReqComponentVO newReqComp = new ReqComponentVO();
                newReqComp.setReqComponentInfo(ObjectClonerUtil.clone(reqComponent));
                reqComponentList.add(newReqComp);
            }
            statementVO.reqComponentVOs = reqComponentList;
        }

        return rc;
    }

    private void setFields(final StatementTreeViewInfo statementTreeViewInfo) {
        statementInfo = new StatementInfo();
        getStatementInfo().setAttributes(statementTreeViewInfo.getAttributes());
        getStatementInfo().setDesc(statementTreeViewInfo.getDesc());
        getStatementInfo().setId(statementTreeViewInfo.getId());
        getStatementInfo().setMetaInfo(statementTreeViewInfo.getMetaInfo());
        getStatementInfo().setName(statementTreeViewInfo.getName());
        getStatementInfo().setOperator(statementTreeViewInfo.getOperator());
        getStatementInfo().setState(statementTreeViewInfo.getState());
        getStatementInfo().setType(statementTreeViewInfo.getType());
    }

    @Override
    public String toString() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append(value);
        return sbResult.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

}
