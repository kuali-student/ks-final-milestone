package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsSummaryView;

import java.util.ArrayList;
import java.util.List;

public class ProgramRequirementsViewConfiguration extends AbstractConfiguration<ProgramViewConfigurer> {

    @Override
    public View getView() {
       //TODO remove after testing done
        StatementTreeViewInfo stmtTreeInfo = new StatementTreeViewInfo();

        List<StatementTreeViewInfo> subTrees = new ArrayList<StatementTreeViewInfo>();
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTrees.add(subTree1);
        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTrees.add(subTree2);
        stmtTreeInfo.setStatements(subTrees);
        stmtTreeInfo.setOperator(StatementOperatorTypeKey.AND);

        // set reqComps for sub-tree 1
        subTree1.setId("STMT-TV-2");
        subTree1.setStatements(null);
        ReqComponentInfo reqComp1 = new ReqComponentInfo();
        reqComp1.setId("REQCOMP-TV-1");
        reqComp1.setNaturalLanguageTranslation("Student must have completed all of MATH 152, MATH 180");
        ReqComponentInfo reqComp2 = new ReqComponentInfo();
        reqComp2.setId("REQCOMP-TV-2");
        reqComp2.setNaturalLanguageTranslation("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180");
        List<ReqComponentInfo> reqComponents = new ArrayList<ReqComponentInfo>();
        reqComponents.add(reqComp1);
        reqComponents.add(reqComp2);
        subTree1.setReqComponents(reqComponents);
        subTree1.setNaturalLanguageTranslation("Student must have completed all of MATH 152, MATH 180 " +
        		"and Student needs a minimum GPA of 3.5 in MATH 152, MATH 180");
        subTree1.setOperator(StatementOperatorTypeKey.OR);

        subTree2.setId("STMT-TV-3");
        subTree2.setStatements(null);
        ReqComponentInfo reqComp3 = new ReqComponentInfo();
        reqComp3.setId("REQCOMP-TV-3");
        reqComp3.setNaturalLanguageTranslation("Student must have completed 1 of MATH 152, MATH 180");
        ReqComponentInfo reqComp4 = new ReqComponentInfo();
        reqComp4.setId("REQCOMP-TV-4");
        reqComp4.setNaturalLanguageTranslation("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180");
        List<ReqComponentInfo> reqComponents2 = new ArrayList<ReqComponentInfo>();
        reqComponents2.add(reqComp3);
        reqComponents2.add(reqComp4);
        subTree2.setReqComponents(reqComponents2);
        subTree2.setNaturalLanguageTranslation("Student must have completed 1 of MATH 152, MATH 180 " +
                "and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180");
        subTree2.setOperator(StatementOperatorTypeKey.OR);

       stmtTreeInfo.setNaturalLanguageTranslation(
               "(Student must have completed all of MATH 152, MATH 180 and Student needs a minimum GPA of 3.5 in MATH 152, MATH 180) " +
        		"or (Student must have completed 1 of MATH 152, MATH 180 and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180)");

        //no name for the view so that breadcrumbs do not extra link
        return new ProgramRequirementsSummaryView(null, ProgramSections.PROGRAM_DETAILS_VIEW,
                                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramConstants.PROGRAM_MODEL_ID, stmtTreeInfo);
    }
}
