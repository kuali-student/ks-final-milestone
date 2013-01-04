package org.kuali.student.enrollment.class2.acal.keyvalue;

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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Core slice class.
@Deprecated
public class TermKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;


    @Override
    /*
     * Return a list of terms that belong to the Academic Year with the start year equal to the current year.
     *
     * From Bonnie: The current implementation has some problems.
     * For instance, in Jan 2012, although we are still in Academic Calendar 2011 - 2012, probably the term of
     * Spring 2012, the current implementation can't display terms of Academic Calendar 2011 - 2012, but can
     * only display terms of Academic Calendar 2012 - 2013.
     */
    public List<KeyValue> getKeyValues() {

        List<AcademicCalendarInfo> acals = new ArrayList<AcademicCalendarInfo>();
        ContextInfo context = new ContextInfo();


        List<TermInfo> terms = new ArrayList<TermInfo>();
        try {
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(new Date());
            int year = nowCal.get(Calendar.YEAR);
            acals.addAll(getAcalService().getAcademicCalendarsByStartYear(year - 1, context));
            acals.addAll(getAcalService().getAcademicCalendarsByStartYear(year, context));
            acals.addAll(getAcalService().getAcademicCalendarsByStartYear(year+1, context));
            for (AcademicCalendarInfo acal : acals) {
                if (StringUtils.equals(acal.getStateKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)){
                    terms.addAll(getAcalService().getTermsForAcademicCalendar(acal.getId(), context));
                }
            }

            // TODO: remove this when we figure out why KRAD defaultValue property is not working
            for (TermInfo term : terms) {
                if ("testTermId1".equals(term.getId())) {
                    terms.remove(term);
                    terms.add(0, term);
                    break;
                }
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
            keyValues.add(new ConcreteKeyValue(term.getId(), term.getName()));
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
