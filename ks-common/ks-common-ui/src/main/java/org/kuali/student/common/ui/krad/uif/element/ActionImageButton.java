/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by bobhurt on 8/23/12
 */
package org.kuali.student.common.ui.krad.uif.element;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Image;
import org.kuali.rice.krad.uif.view.View;

import java.text.MessageFormat;

/**
 * Field in the form of an image-only button that presents an action that
 * can be taken on the UI such as submitting the form or invoking a script
 *
 * @author Kuali Student Team
 */
public class ActionImageButton extends Action {

    private String imageSource;
    private String imageSourceOnHover;

    @Override
    public void performFinalize(View view, Object model, Component parent) {
        super.performFinalize(view, model, parent);

        Image img = this.getActionImage();
        if (null == img) {  // Spring config should always include "actionImage" property
            throw new RuntimeException("Implementation of "+ this.getClass() +" did not instantiate \"actionImage\" property.");
        }

        if (StringUtils.isBlank(img.getSource()) && StringUtils.isNotBlank(this.imageSource)) {
            img.setSource(this.imageSource);
        }

        if (StringUtils.isNotBlank(this.imageSourceOnHover)) {
            String script = MessageFormat.format("ksButtonImageChanger(this,\"{0}\");", this.imageSourceOnHover);
            if (StringUtils.isNotBlank(this.getOnMouseOverScript())) {
                script += this.getOnMouseOverScript();
            }
            this.setOnMouseOverScript(script);

            script = MessageFormat.format("ksButtonImageChanger(this,\"{0}\");", img.getSource());
            if (StringUtils.isNotBlank(this.getOnMouseOutScript())) {
                script += this.getOnMouseOutScript();
            }
            this.setOnMouseOutScript(script);
        }
    }

    public String getImageSource() {
        return imageSource;
    }
    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageSourceOnHover() {
        return imageSourceOnHover;
    }
    public void setImageSourceOnHover(String imageSourceOnHover) {
        this.imageSourceOnHover = imageSourceOnHover;
    }

}
