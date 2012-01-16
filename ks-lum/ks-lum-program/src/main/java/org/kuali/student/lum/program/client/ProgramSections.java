package org.kuali.student.lum.program.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

/**
 * Program section's identifiers.
 *
 * @author Igor
 */
public enum ProgramSections {
    PROGRAM_PROPOSAL_VIEW,
    PROGRAM_PROPOSAL_EDIT,
    PROGRAM_PROPOSAL_CHANGE_IMPACT_VIEW,
    PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT,
    PROGRAM_DETAILS_VIEW,
    PROGRAM_DETAILS_EDIT,
    SPECIALIZATIONS_VIEW,
    SPECIALIZATIONS_EDIT,
    PROGRAM_REQUIREMENTS_VIEW,
    PROGRAM_REQUIREMENTS_EDIT,
    MANAGE_BODIES_VIEW,
    MANAGE_BODIES_EDIT,
    CATALOG_INFO_VIEW,
    CATALOG_INFO_EDIT,
    LEARNING_OBJECTIVES_VIEW,
    LEARNING_OBJECTIVES_EDIT,
    SUPPORTING_DOCUMENTS_VIEW,
    SUPPORTING_DOCUMENTS_EDIT,
    COLLABORATORS_EDIT,
    VIEW_ALL,
    SUMMARY,
    COMMENTS,
    EMPTY,
    WF_APPROVE_DIALOG;

    private static HashMap<Enum<?>, Enum<?>> sectionMap = new HashMap<Enum<?>, Enum<?>>();

    private static ArrayList<String> viewForUpdate = new ArrayList<String>();

    static{
        viewForUpdate.add(SPECIALIZATIONS_EDIT.name());
        viewForUpdate.add(MANAGE_BODIES_EDIT.name());
        viewForUpdate.add(LEARNING_OBJECTIVES_EDIT.name());
    }

    static {
        sectionMap.put(PROGRAM_PROPOSAL_VIEW, PROGRAM_PROPOSAL_EDIT);
        sectionMap.put(PROGRAM_PROPOSAL_CHANGE_IMPACT_VIEW, PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT);
        sectionMap.put(PROGRAM_DETAILS_VIEW, PROGRAM_DETAILS_EDIT);
        sectionMap.put(SPECIALIZATIONS_VIEW, SPECIALIZATIONS_EDIT);
        sectionMap.put(ProgramRequirementsViewController.ProgramRequirementsViews.PREVIEW, PROGRAM_REQUIREMENTS_EDIT);
        sectionMap.put(MANAGE_BODIES_VIEW, MANAGE_BODIES_EDIT);
        sectionMap.put(CATALOG_INFO_VIEW, CATALOG_INFO_EDIT);
        sectionMap.put(LEARNING_OBJECTIVES_VIEW, LEARNING_OBJECTIVES_EDIT);
        sectionMap.put(SUPPORTING_DOCUMENTS_VIEW, SUPPORTING_DOCUMENTS_EDIT);
    }

    public static Enum<?> getEditSection(Enum<?> viewSection) {
        return sectionMap.get(viewSection);
    }

    public static ArrayList<String> getViewForUpdate() {
        return viewForUpdate;
    }
}
