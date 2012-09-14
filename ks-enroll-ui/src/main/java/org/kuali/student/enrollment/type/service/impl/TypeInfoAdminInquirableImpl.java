/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.type.service.impl;

import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;

/**
 * This class 
 *
 * @author Kuali Student Team
 */
public class TypeInfoAdminInquirableImpl
        extends InquirableImpl {

    private final static String TYPE_KEY = "key";
    private transient TypeService typeService;
    final Logger logger = Logger.getLogger(TypeInfoAdminInquirableImpl.class);

    @Override
    public TypeInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            return getTypeService().getType(parameters.get(TYPE_KEY), getContextInfo());
        } catch (DoesNotExistException ex) {
            throw new RuntimeException (ex);
        } catch (InvalidParameterException ex) {
            throw new RuntimeException (ex);
        } catch (MissingParameterException ex) {
            throw new RuntimeException (ex);
        } catch (OperationFailedException ex) {
            throw new RuntimeException (ex);
        } catch (PermissionDeniedException ex) {
            throw new RuntimeException (ex);
        }
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE,
                    TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
