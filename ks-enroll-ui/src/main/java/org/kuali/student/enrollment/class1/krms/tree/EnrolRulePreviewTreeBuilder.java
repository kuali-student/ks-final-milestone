package org.kuali.student.enrollment.class1.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Overridden class to add items to be displayed in the view trees that are not converted
 * by the natural language translater on the rule management service.
 *
 * @author Kuali Student Team
 */
public class EnrolRulePreviewTreeBuilder extends RulePreviewTreeBuilder {

    private static final long serialVersionUID = 1L;

    @Override
    public List<String> getListItems(PropositionEditor propositionEditor) {
        if (propositionEditor instanceof EnrolPropositionEditor) {
            CluSetInformation cluSetInfo = ((EnrolPropositionEditor) propositionEditor).getCluSet();

            if (cluSetInfo != null) {
                List<String> listItems = new ArrayList<String>();

                for (CluInformation clu : cluSetInfo.getClusAndClusInRange()) {
                    String description = clu.getCode() + (clu.getTitle() != null ? " " + clu.getTitle() : "") + (clu.getCredits() != null ? " " + clu.getCredits() : "");
                    listItems.add(description);
                }

                for (CluSetInfo cluSet : cluSetInfo.getCluSets()) {
                    listItems.add(cluSet.getName());
                }

                return listItems;
            }
        }
        return null;
    }
}
