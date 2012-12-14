package org.kuali.student.myplan.course.util;
/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimestampPropertyEditor extends PropertyEditorSupport implements Serializable {

    private final static Logger logger = Logger.getLogger(TimestampPropertyEditor.class);

    private List<String> styleClasses;

    private String simpleDateFormat = "";

    private String emptyDateText = "";

    public TimestampPropertyEditor() {
        styleClasses = new ArrayList<String>();
    }

    @Override
    public void setValue(Object value) {
	    if (value == null) {
            logger.error("Date was null.");
            return;
        }

        if ( ! (value instanceof Date)) {
            logger.error(String.format("Value was type [%s] instead of Date.", value.getClass()));
            return;
        }

        super.setValue(value);
    }

    @Override
    public String getAsText() {
        Date date = (Date) super.getValue();

        if(null == date) {
            return this.emptyDateText;
        }

        if (this.simpleDateFormat.length() == 0) {
            return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
        } else {
            SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);
            return format.format(date);
        }
    }

    public void setSimpleDateFormat(String simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public String getSimpleDateFormat() {
        return this.simpleDateFormat;
    }

    public List<String> getStyleClasses() {
        return this.styleClasses;
    }

    public void setStyleClasses(List<String> styleClasses) {
        this.styleClasses = styleClasses;
    }

    public String getStyleClassesAsString() {
        if (styleClasses != null) {
            return StringUtils.join(styleClasses, " ");
        }
        return "";
    }

    public String getEmptyDateText() {
        return emptyDateText;
    }

    public void setEmptyDateText(String emptyDateText) {
        this.emptyDateText = emptyDateText;
    }
}
