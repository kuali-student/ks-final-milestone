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

package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;

public class CourseRpcGwtServlet extends DataGwtServlet implements CourseRpcService {

	private static final long serialVersionUID = 1L;

    private CourseService courseService;
    private StatementService statementService;    

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language) throws Exception {
        List<StatementTreeViewInfo> rules = courseService.getCourseStatements(courseId, nlUsageTypeKey, language);
        for (StatementTreeViewInfo rule : rules) {
            setReqCompNL(rule);
        }
        return rules;
    }    

    public Map<Integer, StatementTreeViewInfo> storeCourseStatements(String courseId, Map<Integer, CourseRequirementsDataModel.requirementState> states,
                                                                        Map<Integer, StatementTreeViewInfo> rules) throws Exception {

        Map<Integer, StatementTreeViewInfo> storedRules = new HashMap<Integer, StatementTreeViewInfo>();

        for (Integer key : rules.keySet()) {
            StatementTreeViewInfo rule = rules.get(key);
            switch (states.get(key)) {
                case STORED:
                    //rule was not changed so continue
                    storedRules.put(key, null);
                    break;
                case ADDED:
                    storedRules.put(key, createCourseStatement(courseId, rule));
                    break;
                case EDITED:
                    storedRules.put(key, updateCourseStatement(courseId, rule));
                    break;
                case DELETED:
                    storedRules.put(key, null);
                    deleteCourseStatement(courseId, rule);
                    break;
                default:
                    break;
            }
        }
        return storedRules;
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        statementTreeViewInfo.setState("Active");
        CourseRequirementsDataModel.stripStatementIds(statementTreeViewInfo);
        StatementTreeViewInfo rule = courseService.createCourseStatement(courseId, statementTreeViewInfo);
        setReqCompNL(rule);
        return rule;
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        return courseService.deleteCourse(courseId);
    }    

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
        statementTreeViewInfo.setState("Active");        
        CourseRequirementsDataModel.stripStatementIds(statementTreeViewInfo);
        StatementTreeViewInfo rule = courseService.updateCourseStatement(courseId, statementTreeViewInfo);
        setReqCompNL(rule);
        return rule;
    }

    private void setReqCompNL(StatementTreeViewInfo tree) throws Exception {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

         if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                setReqCompNL(statement); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            for (ReqComponentInfo reqComponent : reqComponentInfos) {
                reqComponent.setNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqComponent, "KUALI.RULE", "en"));
            }
        }
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }    
}
