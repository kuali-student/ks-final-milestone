/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 8/1/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.type.service.TypeService;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SeatPoolExpirationMilestoneTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private transient TypeService typeService;
    final Logger LOG = Logger.getLogger(SeatPoolExpirationMilestoneTypeKeyValues.class);
    List<KeyValue> keyValues = new ArrayList<KeyValue>();

    @Override
    public List<KeyValue> getKeyValues(ViewModel viewModel) {
        try {
            List<TypeInfo> typeInfos = getTypeService().getTypesForGroupType("kuali.milestone.type.group.seatpool", getContextInfo());

            if (typeInfos != null) {
                for (TypeInfo typeInfo : typeInfos) {
                    keyValues.add(new ConcreteKeyValue(typeInfo.getKey(), typeInfo.getName()));
                }
            }
        } catch (InvalidParameterException e) {
            LOG.error("Error getting type: Invalid Parameter ", e);
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            LOG.error("Error getting type: Missing Parameter ", e);
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            LOG.error("Error getting type: Operation Failed ", e);
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            LOG.error("Error getting type: Permission Denied ", e);
            throw new RuntimeException(e);
        } catch (DoesNotExistException e) {
            LOG.error("Error getting type: Does Not Exist ", e);
            throw new RuntimeException(e);
        }

        keyValues.add(new ConcreteKeyValue("NONE", "NONE"));
        return keyValues;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }
}
