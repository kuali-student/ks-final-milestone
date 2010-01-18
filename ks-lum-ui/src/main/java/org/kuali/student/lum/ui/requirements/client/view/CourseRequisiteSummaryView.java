package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class CourseRequisiteSummaryView extends ViewComposite {
    
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);

    private final String KS_STATEMENT_TYPE_PREREQ = "kuali.luStatementType.prereqAcademicReadiness";
    private final String KS_STATEMENT_TYPE_COREQ = "kuali.luStatementType.coreqAcademicReadiness";
    private final String KS_STATEMENT_TYPE_ANTIREQ = "kuali.luStatementType.antireqAcademicReadiness";
    private final String KS_STATEMENT_TYPE_ENROLLREQ = "kuali.luStatementType.enrollAcademicReadiness";    

    //view's widgets
    private final SimplePanel mainPanel = new SimplePanel();
    private final SimplePanel viewPanel = new SimplePanel();

    //rule types we deal with
    List<String> applicableLuStatementTypes = new ArrayList<String>();

    //view's data           
    private List<RuleInfo> courseRules = new ArrayList<RuleInfo>();  //contains all rules, each rule has its own RuleInfo object
    private static int id = 0;
    
    public CourseRequisiteSummaryView(Controller controller) {
        super(controller, "Course Requisites");
        super.initWidget(mainPanel);   
        
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_PREREQ);
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_COREQ);
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_ANTIREQ);
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_ENROLLREQ);
    }
    
    public void beforeShow() {                  
        
//        getController().requestModel(CluProposalModelDTO.class,
//                new ModelRequestCallback<CluProposalModelDTO>() {
//                    @Override
//                    public void onModelReady(Model<CluProposalModelDTO> model) {
//                        ModelDTO cluInfoModelDTO = (ModelDTO) ((ModelDTOType) model.get().get("cluInfo")).get();
//                        courseRules = model.get().getRuleInfos();
//                        initializeView();
//                    }    
//    
//                    @Override
//                    public void onRequestFail(Throwable cause) {
//                        Window.alert("Failed to get CluProposalModelDTO");
//                    }
//        });                 
    }
    
    public void initializeView() {       
        mainPanel.clear();
        viewPanel.clear();
        mainPanel.add(viewPanel);;        

        //go to retrieve new rules only for the first time
    }

    private void setRuleInfo(RuleInfo ruleInfo) { 
        RuleInfo origRule = getRuleInfo(ruleInfo.getLuStatementTypeKey());
        if (origRule != null) {     
            courseRules.remove(origRule);
        }               
        courseRules.add(ruleInfo);
    }
    
    private RuleInfo getRuleInfo(String luStatementTypeKey) {   
        if ((courseRules != null) && !courseRules.isEmpty()) {
            for (RuleInfo ruleInfo : courseRules) {
                if (ruleInfo.getLuStatementTypeKey().equals(luStatementTypeKey)) {                  
                    return ruleInfo;
                }                
            }
        }     
        return null;
    }

    private String getRuleTypeName(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return "Enrollment Restrictions";
        if (luStatementTypeKey.contains("prereq")) return "Prerequisites";
        if (luStatementTypeKey.contains("coreq")) return "Corequisites";
        if (luStatementTypeKey.contains("antireq")) return "Antirequisites";
        return "";
    }

}
