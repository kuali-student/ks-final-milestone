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

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;

public class ScheduledTermsPropertyEditor extends CollectionListPropertyEditor {

    private final static Logger logger = Logger.getLogger(ScheduledTermsPropertyEditor.class);

    @Override
    protected String makeHtmlList(Collection c) {
        StringBuilder list = new StringBuilder();
        Iterator<Object> i = c.iterator();
        while (i.hasNext()) {
            String term = (String) i.next();
            String elemTxt = KsapFrameworkServiceLocator.getTermHelper()
                    .getTerm(term).getName();

            // Convert Winter 2012 to WI 12

            Matcher m = CourseSearchConstants.TERM_PATTERN.matcher(KsapFrameworkServiceLocator.getTermHelper()
                    .getTerm(term).getName());
            if(m.matches()) {
                elemTxt = m.group(1).substring(0,2).toUpperCase() + " " + m.group(2);
            }
            list.append(wrapListItem(elemTxt));
        }
        return list.toString();
    }
}
