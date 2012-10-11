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
 * Created by bobhurt on 8/7/12
 */
package org.kuali.student.enrollment.kitchensink;

import org.hsqldb.lib.StringUtil;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.field.LinkField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

import javax.xml.namespace.QName;
import java.text.MessageFormat;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KitchenSinkHelper extends ViewHelperServiceImpl {

    private AcademicCalendarService academicCalendarService;


    public TermInfo termInfoAjaxQuery(String termId) {
        TermInfo termInfo = new TermInfo();
        if (!StringUtil.isEmpty(termId)) {
            try {
                termInfo = getAcademicCalendarService().getTerm(termId, getContextInfo());
            }
            catch (DoesNotExistException e1) {
                termInfo.setName("Unknown");
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return termInfo;
    }

    public void delete(int selectedIndex, KitchenSinkForm form) throws Exception{
        form.getCollection().remove(selectedIndex);
    }

    public void setSourceLinkText(LinkField linkField, Object model) {
        linkField.setLinkText(linkField.getHref());
    }

    public void setDirectLinkUrl(LinkField linkField, Object model, String methodToCall) {
        if (StringUtil.isEmpty(methodToCall)) {
            methodToCall = "start";
        }

        String url = MessageFormat.format("{0}?viewId={1}&amp;methodToCall={2}",
                ((KitchenSinkForm) model).getFormPostUrl(),
                ((KitchenSinkForm) model).getViewId() + "-Bare", methodToCall);
        linkField.setLinkText(url);
        linkField.setHref(url);
    }
    public void setDirectLinkUrl(LinkField linkField, Object model) {
        setDirectLinkUrl(linkField, model, null);
    }

    @Override
    public void processCollectionSaveLine(View view, Object model, String collectionPath, int lineIndex) {
        //
        // Code goes here to save existing collection line to database...
        //
        GlobalVariables.getMessageMap().addGrowlMessage("NOTE", "kitchensink.saveLine", String.valueOf(lineIndex));

        super.processCollectionSaveLine(view, model, collectionPath, lineIndex);
    }

    @Override
    public void processCollectionDeleteLine(View view, Object model, String collectionPath, int lineIndex) {
        //
        // Code goes here to delete existing collection line from database...
        //
        GlobalVariables.getMessageMap().addGrowlMessage("NOTE", "kitchensink.deleteLine", String.valueOf(lineIndex));

        super.processCollectionDeleteLine(view, model, collectionPath, lineIndex);
    }



    private AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return academicCalendarService;
    }

    // TODO - where does context come from?
    private ContextInfo getContextInfo() {
        return new ContextInfo();
    }

}
