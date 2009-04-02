package org.kuali.student.lum.ui.requirements.client.view;

public interface LumUIEventListener {

    void switchToComplexView();
    void switchToSimpleView();
    void showRequirementDialog();
    void hideRequirementDialog();
    void updateExampleAndWorkArea();
    void showSearchDialog();
    void hideSearchDialog();
    void updateAlgebra(String algebra);
    void updateSelectedCourseList(String cluList);
}
