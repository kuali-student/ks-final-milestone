/* Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

/**
 * This is the wrapper class for the <code>AcalEventInfo</code> DTO.
 *
 * @author Kuali Student Team
 */
public class AcalEventWrapper extends TimeSetWrapper{

    private AcalEventInfo acalEventInfo;
    private String eventTypeKey;
    private String eventTypeName;

    public AcalEventWrapper() {
        acalEventInfo = new AcalEventInfo();
        setAllDay(false);
        setDateRange(true);
        acalEventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
    }

    /**
     * Populate all the properties from the passed in dto.
     *
     * @param acalEventInfo source dto to copy
     * @param isCopy whether to copy the dto or not
     */
    public AcalEventWrapper(AcalEventInfo acalEventInfo,boolean isCopy){
        this.setStartDate(acalEventInfo.getStartDate());
        this.setEndDate(acalEventInfo.getEndDate());
        this.setAllDay(acalEventInfo.getIsAllDay());
        this.setDateRange(acalEventInfo.getIsDateRange());
        this.setEventTypeKey(acalEventInfo.getTypeKey());

        buildDateAndTime();

        if (isCopy){
            setAcalEventInfo(new AcalEventInfo());
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getAcalEventInfo().getTypeKey());
            getAcalEventInfo().setDescr(rti);
            getAcalEventInfo().setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        }else{
           setAcalEventInfo(acalEventInfo);
        }

    }

    public AcalEventInfo getAcalEventInfo(){
        return acalEventInfo;
    }

    /**
     * <code>AcalEventInfo</code> associated with this wrapper
     *
     * @param acalEventInfo event info dto
     */
    public void setAcalEventInfo(AcalEventInfo acalEventInfo) {
        this.acalEventInfo =  acalEventInfo;
    }

    /**
     * See <code>setEventTypeKey()</code>
     *
     * @return event type key
     */
    public String getEventTypeKey() {
        return eventTypeKey;
    }

    /**
     * Event Type key
     *
     * @param eventTypeKey type key
     */
    public void setEventTypeKey(String eventTypeKey) {
        this.eventTypeKey = eventTypeKey;        
    }

    /**
     * See <code>setEventTypeName()</code>
     *
     * @return event name
     */
    public String getEventTypeName() {
        return eventTypeName;
    }

    /**
     * Sets the event name for the ui display purpose.
     *
     * @param eventTypeName event name
     */
    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    /**
     * Formatted start date for the ui display
     *
     * @return formatted start date
     */
    @SuppressWarnings("unused")
    public String getStartDateUI(){
        return formatStartDateUI(acalEventInfo.getStartDate());
    }

    /**
     * Formatted end date for the ui display
     *
     * @return formatted end date
     */
    @SuppressWarnings("unused")
    public String getEndDateUI(){
        return formatEndDateUI(acalEventInfo.getEndDate());
    }


}
