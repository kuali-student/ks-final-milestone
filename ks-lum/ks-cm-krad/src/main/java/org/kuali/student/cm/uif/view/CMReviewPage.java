/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 8/12/14
 */
package org.kuali.student.cm.uif.view;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.rice.krad.uif.view.FormView;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.common.uif.container.KSUifPage;

/**
 *
 * Override to mainly refresh the complete view header instead of refreshing just a portion of view header.
 *
 * @author Kuali Student Team
 */
public class CMReviewPage extends KSUifPage{

    /**
     * Override the finalize method to use 'setupReviewPage()' instead of 'setupPage()' javascript at the client.
     * By default, KRAD refreshes the view header by looking for the div with css class 'uif-viewHeader',
     * which doesnt contain the lower group. It suppose to look for 'uif-viewHeader-contentWrapper' to refresh the entire
     * view header. To overcome this issue, we are overriding the default KRAD functionality and refresh the
     * 'uif-viewHeader-contentWrapper' div which should be refreshing the upper, right and lower groups at the view header.
     *
     * @param model
     * @param parent
     */
    @Override
    public void performFinalize(Object model, LifecycleElement parent) {

        super.performFinalize(model,parent);

        View view = ViewLifecycle.getView();


        String documentLoadScript;
        if (view instanceof FormView && ((FormView) view).isValidateClientSide()) {
            documentLoadScript = StringUtils.replace(getOnDocumentReadyScript(),"setupPage(true)","setupReviewPage(true)");
        } else {
            documentLoadScript = StringUtils.replace(getOnDocumentReadyScript(),"setupPage(false)","setupReviewPage(false)");
        }

        this.setOnDocumentReadyScript(documentLoadScript);

    }

}
