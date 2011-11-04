package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TermKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;


    @Override
    public List<KeyValue> getKeyValues() {

        List<AcademicCalendarInfo> acals;
        ContextInfo context = ContextInfo.newInstance();


        List<TermInfo> terms = new ArrayList<TermInfo>();
        try {
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(new Date());
            int year = nowCal.get(Calendar.YEAR);
            acals = getAcalService().getAcademicCalendarsByStartYear(year, context);
            for (AcademicCalendarInfo acal : acals) {
                terms.addAll(getAcalService().getTermsForAcademicCalendar(acal.getKey(), context));
            }
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No Terms found for current AcademicCalendar(s)! There should be some in the database.", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        for(TermInfo term : terms) {
            keyValues.add(new ConcreteKeyValue(term.getKey(), term.getName()));
        }

        return keyValues;
    }

    protected AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }
}
