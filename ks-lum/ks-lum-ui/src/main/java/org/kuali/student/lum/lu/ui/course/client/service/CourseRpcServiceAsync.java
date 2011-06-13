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

package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CourseRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync{
    public void getCourseStatements(String courseId, String nlUsageTypeKey, String language, AsyncCallback<List<StatementTreeViewInfo>> callback);
    public void storeCourseStatements(String courseId, String courseState, Map<Integer, CourseRequirementsDataModel.requirementState> states,
                                            Map<Integer, StatementTreeViewInfo> rules, AsyncCallback<Map<Integer, StatementTreeViewInfo>> callback);    
    public void createCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo, AsyncCallback<StatementTreeViewInfo> callback);
    public void deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, AsyncCallback<StatusInfo> callback);
    public void updateCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo, AsyncCallback<StatementTreeViewInfo> callback);
    
    public void changeState(String courseId, String newState, AsyncCallback<StatusInfo> callback);
    public void changeState(String courseId, String newState, Date currentVersionStart, AsyncCallback<StatusInfo> callback);
	
    public void createCopyCourse(String originalCluId, AsyncCallback<DataSaveResult> asyncCallback);
	public void createCopyCourseProposal(String originalProposalId, AsyncCallback<DataSaveResult> asyncCallback);
	public void isLatestVersion(String versionIndId, Long versionSequenceNumber, AsyncCallback<Boolean> callback);
}
