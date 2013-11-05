/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.constants;

import java.util.ArrayList;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Constants used by for LprService
 *
 * @author nwright
 */
public class CommonServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String REF_OBJECT_URI_GLOBAL_PREFIX = "http://student.kuali.org/wsdl/";
    /**
     * Special attribute value which when supplied on the context during
     * a create will allow the calling program to supply an ID to the 
     * implementation bypassing the check that throws a ReadOnlyException
     * if this is the case.
     */
    public static final String ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_KEY = "kuali.context.allow.id.on.create";
    public static final String ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_TRUE_VALUE = "TRUE";
    public static final String ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_FALSE_VALUE = "FALSE";

    /**
     * Checks if should allow Ids to be supplied on creates
     */
    public static boolean isIdAllowedOnCreate(ContextInfo context) {
        if (context.getAttributes() == null) {
            return false;
        }
        for (AttributeInfo attr : context.getAttributes()) {
            if (ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_KEY.equals(attr.getKey())) {
                if (ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_TRUE_VALUE.equals(attr.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void setIsIdAllowedOnCreate(ContextInfo context, boolean allowed) {
        if (context.getAttributes() == null) {
            context.setAttributes(new ArrayList<AttributeInfo>());
        }
        for (AttributeInfo attr : context.getAttributes()) {
            if (ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_KEY.equals(attr.getKey())) {
                attr.setValue(ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_TRUE_VALUE);
                return;
            }
        }
        AttributeInfo attr = new AttributeInfo ();
        attr.setKey(ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_KEY);
        attr.setValue(ALLOW_ID_ON_CREATE_CONTEXT_ATTRIBUTE_TRUE_VALUE);
        context.getAttributes().add(attr);
    }
}
