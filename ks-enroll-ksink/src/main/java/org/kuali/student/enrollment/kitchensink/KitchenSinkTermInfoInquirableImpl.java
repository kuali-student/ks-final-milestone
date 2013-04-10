/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by bobhurt on 2/26/13
 */
package org.kuali.student.enrollment.kitchensink;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class supports an InquirableImpl lookup of TermInfo
 *
 * @author Kuali Student Team
 */
public class KitchenSinkTermInfoInquirableImpl extends InquirableImpl {

    private AcademicCalendarService academicCalendarService;

    @Override
    public TermInfo retrieveDataObject(Map<String, String> parameters) {
        String termKey = parameters.get("code");
        List<TermInfo> termInfoList = new ArrayList();
        try{
            termInfoList = getAcademicCalendarService().getTermsByCode(termKey, new ContextInfo());
            if (termInfoList.size() > 0) {
                // NOTE: should check to make sure length is not more than one, and throw an error if it is
                return termInfoList.get(0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    private AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return academicCalendarService;
    }

}
