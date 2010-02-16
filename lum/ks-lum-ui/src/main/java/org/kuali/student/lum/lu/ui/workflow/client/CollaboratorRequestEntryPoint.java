/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.ui.workflow.client;

import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CollaboratorRequestEntryPoint implements EntryPoint{
	
    private CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
	private ServerPropertiesRpcServiceAsync serverPropertiesRpcServiceAsync = GWT.create(ServerPropertiesRpcService.class);
	
    private VerticalPanel rootPanel = new VerticalPanel();

    String docId;
    String backdoorId;
    
	private KSButton wfApproveButton = new KSButton("Approve", new ClickHandler(){
		public void onClick(ClickEvent event) {
			cluProposalRpcServiceAsync.approveDocumentWithId(docId, new AsyncCallback<Boolean>(){
				public void onFailure(
						Throwable caught) {
					Window.alert("Error approving Proposal");
				}
				public void onSuccess(
						Boolean result) {
					if(result){
						Window.alert("Proposal was approved");
						redirect();
					}else{
						Window.alert("Error approving Proposal");
					}
				}
			});
		}        
	});

	private KSButton  wfDisApproveButton = new KSButton("Disapprove", new ClickHandler(){
        public void onClick(ClickEvent event) {
			cluProposalRpcServiceAsync.disapproveDocumentWithId(docId, new AsyncCallback<Boolean>(){
				public void onFailure(
						Throwable caught) {
					Window.alert("Error disapproving Proposal");
				}
				public void onSuccess(
						Boolean result) {
					if(result){
						Window.alert("Proposal was disapproved");
						redirect();
					}else{
						Window.alert("Error disapproving Proposal");
					}
				}
				
			});
        }        
    });
    
    @Override
	public void onModuleLoad() {
    	rootPanel.add(new KSLabel("You've been invited to collaborate"));
        docId = Window.Location.getParameter("docId");
        backdoorId = Window.Location.getParameter("backdoorId");
        
        if(docId!=null){
                showView();
        }
		RootPanel.get().add(rootPanel);		
	}
    
	private void showView() {
		rootPanel.add(wfApproveButton);
		rootPanel.add(wfDisApproveButton);
	}

	private void redirect() {
		// TODO Auto-generated method stub
		serverPropertiesRpcServiceAsync.get("ks.lum.MainEntryPoint", new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert("errorgetting system property");
				Window.Location.assign("http://localhost:8080/ks-lum-web/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp");
			}
			public void onSuccess(String result) {
				Window.Location.assign(result);				
			}
		});
		
	}
}
