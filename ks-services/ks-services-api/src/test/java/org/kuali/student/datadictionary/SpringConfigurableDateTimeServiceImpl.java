/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.datadictionary;

import org.kuali.rice.core.impl.DateTimeServiceImpl;

/**
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
