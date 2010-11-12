package org.kuali.student.lum.program.client;

import java.util.HashMap;

import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;

/**
 * Program section's identifiers.
 *
 * @author Igor
 */
public enum ProgramSections {
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
    VIEW_ALL,
    SUMMARY,
    COMMENTS,
    EMPTY;

    private static HashMap<Enum<?>, Enum<?>> sectionMap = new HashMap<Enum<?>, Enum<?>>();

    static {
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

}
