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
package org.kuali.student.datadictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;
import org.kuali.student.common.service.CommonConstants;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dictionary Service implementation that reads the dictionary from the spring beans and feeds
 * both the Student dictionary and the RICE dictionary
 *
 * @author nwright
 */
public class DataDictionaryServiceImpl implements DataDictionaryService, RiceDataDictionaryServiceInfc {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DataDictionaryServiceImpl.class);
    private String serviceNamespaceSuffix;
    private Map<String, DictionaryEntryInfo> studMap;
    private Map<String, DataObjectEntry> riceMap;

    public DataDictionaryServiceImpl() {
    }

    public String getServiceNamespaceSuffix() {
        return serviceNamespaceSuffix;
    }

    public void setServiceNamespaceSuffix(String serviceNamespaceSuffix) {
        this.serviceNamespaceSuffix = serviceNamespaceSuffix;
    }


    public void setDictionaryLocations(List<String> locations) throws IOException {
        for (String location : locations) {
            ApplicationContext ac = new ClassPathXmlApplicationContext(location);
            Map<String, DataObjectEntry> beansOfType =
                    (Map<String, DataObjectEntry>) ac.getBeansOfType(DataObjectEntry.class);
            studMap = new LinkedHashMap<String, DictionaryEntryInfo>();
            riceMap = new LinkedHashMap<String, DataObjectEntry>();
            for (DataObjectEntry entry : beansOfType.values()) {
                LOG.debug(entry.getObjectClass());
                riceMap.put(entry.getFullClassName(), entry);
                studMap.put(calcRefObjectURI (entry.getObjectClass()), new Rice2StudentDictionaryEntryConverter().convert(entry));
            }
        }
    }

    private String calcRefObjectURI (Class<?> objectClass) {
     return CommonConstants.REF_OBJECT_URI_GLOBAL_PREFIX + this.serviceNamespaceSuffix + "/" + objectClass.getSimpleName();
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException,
            MissingParameterException,
            PermissionDeniedException,
            DoesNotExistException {
        DictionaryEntryInfo entry = studMap.get(entryKey);
        if (entry == null) {
            throw new DoesNotExistException(entryKey);
        }
        return entry;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
            throws OperationFailedException,
            MissingParameterException,
            PermissionDeniedException {
        return new ArrayList(studMap.keySet());
    }

    @Override
    public DataObjectEntry getDataObjectEntry(String entryKey) {
        return riceMap.get(entryKey);
    }
}
