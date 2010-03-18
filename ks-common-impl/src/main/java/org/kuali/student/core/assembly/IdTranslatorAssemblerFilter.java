/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.core.assembly;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.util.IdTranslation;
import org.kuali.student.core.assembly.util.IdTranslator;

/**
 * Intercepts the result of the target Assembler's get(...) method and translates IDs
 * 
 * @author Kuali Student Team
 */
public class IdTranslatorAssemblerFilter extends PassThroughAssemblerFilter<Data, Void> {
    private IdTranslator idTranslator;

    public IdTranslatorAssemblerFilter(IdTranslator idTranslator) {
        this.idTranslator = idTranslator;
    }

    @Override
    public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<Data> response, GetFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doGetFilter(id, response);
        Data data = response.getValue(); 
        if (data != null) {
            translateIds(data, chain);
        }
    }
    
    @Override
    public void doSaveFilter(FilterParamWrapper<Data> request, FilterParamWrapper<SaveResult<Data>> response, SaveFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doSaveFilter(request, response);
        SaveResult<Data> saveResult = response.getValue();
        Data data = saveResult != null && saveResult.getValue() != null ? saveResult.getValue() : null;
        if(data != null) {
            translateIds(data, chain);
        }
    }

    private void translateIds(Data data, AssemblerManagerAccessable<Data, Void> chain) throws AssemblyException {
        Assembler a = chain.getManager().getTarget();
        Metadata metadata = a.getDefaultMetadata();
        if (metadata != null && data != null) {
            _translateIds(data, metadata);
        }
    }
    
    /**
     * Uses the IdTranslator and Metadata to convert IDs into their display text and stores those translations in the
     * _runtimeData node
     * 
     * @param data
     *            the Data instance containing IDs to be translated
     * @param metadata
     *            the Metadata instance representing the data provided.
     * @throws AssemblyException
     */
    private void _translateIds(Data data, Metadata metadata) throws AssemblyException {
        try {
            Map<String, Metadata> children = metadata.getProperties();
            if (children != null && children.size() > 0) {
                for (Entry<String, Metadata> e : children.entrySet()) {
                    
                    Metadata fieldMetadata = e.getValue();
                    if(MetadataInterrogator.isRepeating(fieldMetadata)) {
                        Iterator<Data.Property> iter = data.iterator();
                        while(iter.hasNext()) {
                            Data.Property prop = iter.next();
                            Object fieldValue = prop.getValue();
                            if(fieldValue != null && fieldValue instanceof Data) {
                                _translateIds((Data) fieldValue, fieldMetadata);//, p);
                            } else if (fieldValue != null && fieldValue instanceof String) {
                                if (fieldMetadata.getInitialLookup() != null && !StringUtils.isEmpty((String) fieldValue)) {
                                    IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(), (String) fieldValue);
                                    if(trans != null) {
                                        setTranslation(data, e.getKey(), trans.getDisplay());
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Object fieldValue = data.get(e.getKey());
                        if (fieldValue != null && fieldValue instanceof Data) {
                            _translateIds((Data) fieldValue, fieldMetadata);//, p);
                        } else if (fieldValue != null && fieldValue instanceof String) {
                            if (fieldMetadata.getInitialLookup() != null && !StringUtils.isEmpty((String) fieldValue)) {
                                IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(), (String) fieldValue);
                                if(trans != null) {
                                    setTranslation(data, e.getKey(), trans.getDisplay());
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            // throw new AssemblyException("Unable to translate IDs", e);

            // Do nothing for now
            e.printStackTrace();
        }
    }

    private static void setTranslation(Data data, String field, String translation) {
        if (data != null) {
            Data runtime = data.get("_runtimeData");
            if (runtime == null) {
                runtime = new Data();
                data.set("_runtimeData", runtime);
            }
            Data fieldData = runtime.get(field);
            if (fieldData == null) {
                fieldData = new Data();
                runtime.set(field, fieldData);
            }
            fieldData.set("id-translation", translation);
        }
    }
}
