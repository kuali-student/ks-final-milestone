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

package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicatorAbstract;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class KSProgressIndicatorImpl extends KSProgressIndicatorAbstract {

    HorizontalPanel main = new HorizontalPanel();
    private Image image = Theme.INSTANCE.getCommonImages().getProgressSpinner();
    private KSLabel label = new KSLabel();

    public KSProgressIndicatorImpl(){
        super();
        main.add(image);
        main.add(label);
        main.addStyleName("KSProgressIndicator");
        this.initWidget(main);
    }

    @Override
    public void hide() {
        main.setVisible(false);
    }

    @Override
    public void show() {
        main.setVisible(true);

    }

    @Override
    public void setText(String labelText) {
        label.setText(labelText);

    }
}
