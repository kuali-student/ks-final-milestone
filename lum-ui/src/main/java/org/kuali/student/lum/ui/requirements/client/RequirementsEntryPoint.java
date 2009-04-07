package org.kuali.student.lum.ui.requirements.client;

import java.util.List;

import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.ui.requirements.client.controller.LumApplication;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RequirementsEntryPoint implements EntryPoint {

    public final static String testCluId = "CLU-1"; //"CHEM111";
    
    public void onModuleLoad() {
        
        RequirementsService.Util.getInstance().getCourseInfo(testCluId, new AsyncCallback<CourseRuleInfo>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final CourseRuleInfo courseRuleInfo) {                
                RootPanel.get().add(new LumApplication(courseRuleInfo));               
            }
        });                      
    }
}
