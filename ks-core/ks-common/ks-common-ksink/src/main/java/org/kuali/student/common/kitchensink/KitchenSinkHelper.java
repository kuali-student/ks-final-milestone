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
package org.kuali.student.common.kitchensink;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.krad.uif.field.LinkField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.BeanPropertyComparator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class extends ViewHelperServiceImpl to provide additional controller layer logic for the kitchen sink
 *
 * @author Kuali Student Team
 */
public class KitchenSinkHelper extends ViewHelperServiceImpl {

    private AcademicCalendarService academicCalendarService;


    public TermInfo termInfoAjaxQuery(String termCode) {
        TermInfo termInfo = new TermInfo();
        if (!StringUtils.isEmpty(termCode)) {
            try {
                List<TermInfo> termInfoList = new ArrayList();
                termInfoList = getAcademicCalendarService().getTermsByCode(termCode, getContextInfo());
                if (termInfoList.size() > 0) {
                    //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
                    int firstTermInfo = 0;
                    termInfo = termInfoList.get(firstTermInfo);
                }
                // NOTE: should check to make sure length is not more than one, and throw an error if it is
                //GlobalVariables.getMessageMap().putError("field", MessageKeyString, messageParameter);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return termInfo;
    }

    public void delete(int selectedIndex, KitchenSinkForm form) throws Exception{
        form.getCollection().remove(selectedIndex);
    }

    public List<Person> getPersonsForSuggest(String personName) {
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.LAST_NAME, personName+"*");
        PersonService personService = KimApiServiceLocator.getPersonService();
        List<Person> personList = personService.findPeople(searchCriteria);

        // sort results, as property "sortPropertyNames" is ignored when "queryMethodToCall" is used
        if ((personList != null) && (personList.size() > 1)) {
            Collections.sort((List<?>) personList, new BeanPropertyComparator(Arrays.asList("lastName","firstName")));//attributeQuery.getSortPropertyNames()));
        }

        return personList;
    }

    public void setSourceLinkText(LinkField linkField, Object model) {
        linkField.setLinkText(linkField.getHref());
    }

    public void setDirectLinkUrl(LinkField linkField, Object model, String methodToCall) {
        if (StringUtils.isEmpty(methodToCall)) {
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
    public void processCollectionSaveLine(ViewModel model, String collectionId, String collectionPath, int lineIndex) {
        //
        // Code goes here to save existing collection line to database...
        //

        if (collectionId.startsWith("KS-KitchenSink-CollectionAsForm-View")) {
            // only set growl for the Collection As Form example
            GlobalVariables.getMessageMap().addGrowlMessage("NOTE", "kitchensink.saveLine", String.valueOf(lineIndex));
        }
        super.processCollectionSaveLine(model, collectionId, collectionPath, lineIndex);
    }

    @Override
    public void processCollectionDeleteLine(ViewModel model, String collectionId, String collectionPath, int lineIndex) {
        //
        // Code goes here to delete existing collection line from database...
        //

        if (collectionId.startsWith("KS-KitchenSink-CollectionAsForm-View")) {
            // only set growl for the Collection As Form example
            GlobalVariables.getMessageMap().addGrowlMessage("NOTE", "kitchensink.deleteLine", String.valueOf(lineIndex));
        }
        super.processCollectionDeleteLine(model, collectionId, collectionPath, lineIndex);
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
