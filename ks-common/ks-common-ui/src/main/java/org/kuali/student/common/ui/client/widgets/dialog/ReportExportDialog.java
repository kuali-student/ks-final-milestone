/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.dialog;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;

import com.google.gwt.core.client.GWT;

public class ReportExportDialog {

    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private KSLightBox dialog;

    private KSRadioButtonListImpl selectItemWidget = GWT.create(KSRadioButtonListImpl.class);

    private ActionCancelGroup actionButtons = new ActionCancelGroup(ButtonEnumerations.ExportCancelEnum.EXPORT, ButtonEnumerations.ExportCancelEnum.CANCEL);

    public ReportExportDialog() {

        dialog = new KSLightBox();
        layout.addStyleName("KS-Advanced-Search-Buttons");
        SectionTitle sectionTitle = SectionTitle.generateH2Title(this.getMessage("exportTitle"));
        layout.add(sectionTitle);

        // Add label to layout panel.
        KSLabel exportLabel = new KSLabel(getMessage("exportDialog"));
        layout.add(exportLabel);

        // Add radiobutton to layout panel.
        HorizontalBlockFlowPanel radioPanel = new HorizontalBlockFlowPanel();
        radioPanel.setHeight("90px");
        SimpleListItems formatList = new SimpleListItems();
        formatList.addItem(ExportUtils.PDF, this.getMessage("pdfFormat"));
        formatList.addItem(ExportUtils.DOC, this.getMessage("docFormat"));
        selectItemWidget.setListItems(formatList);
        selectItemWidget.selectItem(ExportUtils.PDF);
        radioPanel.add(selectItemWidget);
        layout.add(radioPanel);

        // Add buttons to layout panel.
        this.addCancelCompleteCallback();
        layout.add(actionButtons);

        dialog.setMaxHeight(200);
        dialog.setMaxWidth(250);
    }

    public void show() {
        dialog.setWidget(layout);
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public void addSelectCompleteCallback(final Callback<String> callback) {
        actionButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
            @Override
            public void exec(ButtonEnum result) {
                if (result == ButtonEnumerations.ExportCancelEnum.EXPORT) {
                    if (selectItemWidget.getSelectedItem() != null) {
                        if (callback != null) {
                            callback.exec(selectItemWidget.getSelectedItem());
                        }
                        dialog.hide();
                    }
                }
            }
        });
    }

    public void addCancelCompleteCallback() {
        actionButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
            @Override
            public void exec(ButtonEnum result) {
                if (result == ButtonEnumerations.ExportCancelEnum.CANCEL) {
                    dialog.hide();
                }
            }
        });
    }

    private String getMessage(final String msgKey) {
        return Application.getApplicationContext().getMessage(msgKey);
    }

}
