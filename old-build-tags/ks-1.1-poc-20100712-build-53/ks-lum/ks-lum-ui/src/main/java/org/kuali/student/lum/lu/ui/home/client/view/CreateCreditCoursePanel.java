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

package org.kuali.student.lum.lu.ui.home.client.view;

import org.kuali.student.common.ui.client.event.ChangeViewActionEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.lum.lu.ui.course.client.widgets.CategoryManagement;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateCreditCoursePanel extends ViewComposite {

    private VerticalPanel mainPanel = new VerticalPanel();
    private KSButton viewProcess = new KSButton("View Process Overview");
    private KSButton startBlank = new KSButton("Start Blank Proposal");
    private KSButton selectTemplate = new KSButton("Select Proposal Template");
    private KSButton copyProposal = new KSButton("Copy Course Proposal");
    private KSButton copyCourse = new KSButton("Copy Existing Course");
    private KSButton categoryManagement = new KSButton("Category Management");
    private KSButton cluSetManagement = new KSButton("CLU Set Management");
    private KSButton browseCatalog = new KSButton("Browse Catalog");
    private KSButton viewMajorDiscipline = new KSButton("View MajorDiscipline");

    /**
     * Constructor.
     *
     * @param controller
     */
    public enum Views{CREATE_COURSE_PANEL}
    
    public CreateCreditCoursePanel(Controller controller) {
        super(controller, "Create Credit Course", Views.CREATE_COURSE_PANEL);
        //Hyperlink more = new Hyperlink("More", "More");
        //more.addStyleName("Home-Small-Hyperlink");
        buildLayout();
        initHandlers();
        initWidget(mainPanel);
    }

    private void initHandlers() {
        viewProcess.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Function not yet implemented");
            }
        });
        startBlank.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.CREATE_COURSE));
            }
        });
        selectTemplate.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Function not yet implemented");
            }
        });
        copyProposal.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Function not yet implemented");
            }
        });
        cluSetManagement.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.MANAGE_CLU_SETS));
            }
        });
        browseCatalog.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.BROWSE_COURSE_CATALOG));

            }
        });
        categoryManagement.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Button closeButton = new Button("Close");

                final KSLightBox pop = new KSLightBox();
                VerticalPanel mainPanel = new VerticalPanel();
                mainPanel.add(new CategoryManagement());
                mainPanel.add(closeButton);

                closeButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        pop.hide();
                    }
                });

                pop.setWidget(mainPanel);
                pop.show();
            }
        });
        copyCourse.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Function not yet implemented");
            }
        });
        viewMajorDiscipline.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.VIEW_MAJOR_DISCIPLINE));
            }
        });
    }

    private void buildLayout() {
        mainPanel.add(new ButtonRow(viewProcess, ""));
        mainPanel.add(new RowBreak());
        mainPanel.add(new ButtonRow(startBlank, "Create a new blank course proposal."));
        mainPanel.add(new ButtonRow(selectTemplate, "Create a proposal from a proposal template."));
        mainPanel.add(new ButtonRow(copyProposal, "Create a proposal by copying an existing course proposal."));
        mainPanel.add(new ButtonRow(copyCourse, "Create a proposal by copying an existing course."));
        mainPanel.add(new ButtonRow(cluSetManagement, "Manage CLU Sets."));
        mainPanel.add(new ButtonRow(browseCatalog, "Browse Catalog"));
        mainPanel.add(new ButtonRow(categoryManagement, "Manage the category"));
        mainPanel.add(new ButtonRow(viewMajorDiscipline, "View Major Discipline"));
        Hyperlink helpMeDecide = new Hyperlink("Help Me Decide", "HelpMe");
        helpMeDecide.addStyleName("Home-Small-Hyperlink");
        helpMeDecide.addStyleName("Content-Left-Margin");
        mainPanel.add(helpMeDecide);
    }

    public static class ButtonRow extends Composite {
        private HorizontalPanel row = new HorizontalPanel();
        private KSLabel descLabel = new KSLabel();

        public ButtonRow(KSButton theButton, String description) {
            row.addStyleName("Home-Button-Row");
            row.addStyleName("Content-Left-Margin");
            descLabel.addStyleName("Home-Description-Label");
            theButton.addStyleName("Home-Standard-Button");
            descLabel.setWordWrap(true);

            descLabel.setText(description);
            row.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            row.add(theButton);
            row.add(descLabel);
            this.initWidget(row);
        }

        public ButtonRow(KSButton theButton, String description, Widget moreLink) {
            this(theButton, description);
            descLabel.getElement().appendChild(moreLink.getElement());
        }
    }

    private class RowBreak extends Composite {
        private HorizontalPanel row = new HorizontalPanel();
        private HTML hr = new HTML("<HR />");

        public RowBreak() {
            row.addStyleName("Home-Horizontal-Break");
            row.add(hr);
            this.initWidget(row);
        }
    }
}
