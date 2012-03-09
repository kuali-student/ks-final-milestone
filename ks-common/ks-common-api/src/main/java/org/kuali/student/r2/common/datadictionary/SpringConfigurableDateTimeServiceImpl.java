/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.r2.common.datadictionary;

import org.kuali.rice.core.impl.datetime.DateTimeServiceImpl;


/**
 * Class that can be configured directly by spring instead of by the KNS configurer
 * 
 * @author nwright
 */
public class SpringConfigurableDateTimeServiceImpl extends DateTimeServiceImpl {

    public String getDateToStringFormatForFileName() {
        return dateToStringFormatForFileName;
    }

    public void setDateToStringFormatForFileName(String dateToStringFormatForFileName) {
        this.dateToStringFormatForFileName = dateToStringFormatForFileName;
    }

    public String getDateToStringFormatForUserInterface() {
        return dateToStringFormatForUserInterface;
    }

    public void setDateToStringFormatForUserInterface(String dateToStringFormatForUserInterface) {
        this.dateToStringFormatForUserInterface = dateToStringFormatForUserInterface;
    }

    public String[] getStringToDateFormats() {
        return stringToDateFormats;
    }

    public void setStringToDateFormats(String[] stringToDateFormats) {
        this.stringToDateFormats = stringToDateFormats;
    }

    public String[] getStringToTimestampFormats() {
        return stringToTimestampFormats;
    }

    public void setStringToTimestampFormats(String[] stringToTimestampFormats) {
        this.stringToTimestampFormats = stringToTimestampFormats;
    }

    public String getTimestampToStringFormatForFileName() {
        return timestampToStringFormatForFileName;
    }

    public void setTimestampToStringFormatForFileName(String timestampToStringFormatForFileName) {
        this.timestampToStringFormatForFileName = timestampToStringFormatForFileName;
    }

    public String getTimestampToStringFormatForUserInterface() {
        return timestampToStringFormatForUserInterface;
    }

    public void setTimestampToStringFormatForUserInterface(String timestampToStringFormatForUserInterface) {
        this.timestampToStringFormatForUserInterface = timestampToStringFormatForUserInterface;
    }


}
