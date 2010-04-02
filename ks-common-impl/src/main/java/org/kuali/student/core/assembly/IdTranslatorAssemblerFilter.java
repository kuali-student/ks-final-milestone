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
import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.util.IdTranslation;
import org.kuali.student.core.assembly.util.IdTranslator;

/**
 * Intercepts the result of the target Assembler's get(...) method and translates IDs
 * 
 * @author Kuali Student Team
 */
public class IdTranslatorAssemblerFilter extends PassThroughAssemblerFilter<Data, Void> {
    
    final Logger LOG = Logger.getLogger(IdTranslatorAssemblerFilter.class);

	
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
            __translateIds(data, metadata);
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
                        Integer index = 0;
                        Object fieldValue = data.get(e.getKey());
                        if(fieldValue instanceof Data) {
                            Data fieldData = data.get(e.getKey());
                            if(fieldData != null) {
                                Iterator<Data.Property> iter = fieldData.iterator();
                                while(iter.hasNext()) {
                                    Data.Property prop = iter.next();
                                    fieldValue = prop.getValue();
                                    if(fieldValue != null && fieldValue instanceof Data) {
                                        _translateIds((Data) fieldValue, fieldMetadata);//, p);
                                    } else if (fieldValue != null && fieldValue instanceof String) {
                                        if (fieldMetadata.getInitialLookup() != null && !StringUtils.isEmpty((String) fieldValue)) {
                                            IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(), (String) fieldValue);
                                            Integer key = prop.getKey();
                                            if(trans != null) {
                                                setTranslation(data, e.getKey(), key, trans.getDisplay());
                                            }
                                        }
                                    }
                                    index++;
                                }
                            }
                        }
                    } else {
                        Object fieldValue = data.get(e.getKey());
                        if (fieldValue != null && fieldValue instanceof Data) {
                            _translateIds((Data) fieldValue, fieldMetadata);//, p);
                        } else if (fieldValue != null && fieldValue instanceof String) {
                            if (fieldMetadata.getInitialLookup() != null && !StringUtils.isEmpty((String) fieldValue)) {
                                IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(), (String) fieldValue);
                                if(trans != null) {
                                    setTranslation(data, e.getKey(), null, trans.getDisplay());
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

	private void __translateIds(Data data, Metadata metadata)
			throws AssemblyException {
		try{
		if (data != null && metadata != null) {
			//Iterate through all the data;s properties
			for (Iterator<Property> iter = data.iterator(); iter.hasNext();) {
				Property prop = iter.next();
				
				Object fieldData = prop.getValue();
				Object fieldKey = prop.getKey();
				Metadata fieldMetadata = metadata.getProperties().get(fieldKey);
				
				//if the fieldMetadata is null then try to use the parent metadata as in the case of lists
				if(fieldMetadata==null){
					fieldMetadata=metadata;
				}
				
				//If the fieldData is Data itself the recurse
				if (fieldData instanceof Data) {
					if (DataType.LIST.equals(fieldMetadata.getDataType())) {
						//Lists are a special case where the metadata property name is "*"
						Metadata listChildMetadata = fieldMetadata.getProperties().get("*");
						//see if this is a list of data or a list of fields
						if(DataType.DATA.equals(listChildMetadata.getDataType())){
							__translateIds((Data) prop.getValue(), listChildMetadata);
						}else{
							//its a list of fields so loop through and translate using the "index"
							for(Iterator<Property> listIter = ((Data)fieldData).iterator(); listIter.hasNext();){
								Property listItem = listIter.next();
								Object listData = listItem.getValue();
								if (listData != null && listData instanceof String) {
									if (fieldMetadata.getInitialLookup() != null
											&& !StringUtils.isEmpty((String) listData)) {
										//This is a string with a lookup so do the translation
										IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(),	(String) listData);
										if (trans != null) {
											Integer index = listItem.getKey();
											setTranslation((Data)fieldData, listItem.getKey().toString(), index, trans.getDisplay());
										}
									}
								}
							}
						}
					} else {
						//Otherwise just use the fieldMetadata
						__translateIds((Data) prop.getValue(), fieldMetadata);
					}
				} else if (fieldData != null && fieldData instanceof String) {
					if (fieldMetadata.getInitialLookup() != null
							&& !StringUtils.isEmpty((String) fieldData)) {
						//This is a string with a lookup so do the translation
						IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(),	(String) fieldData);
						if (trans != null) {
							setTranslation(data, prop.getKey().toString(), null, trans.getDisplay());
						}
					}
				}
			}
		}
		}catch(Exception e){
			LOG.error("Error translating", e);
		}
	}
	
    private static void setTranslation(Data data, String field, Integer index, String translation) {
        if (data != null) {
        	//Get runtime data for the node and create if it doesn't exist
            Data runtime = data.get("_runtimeData");
            if (runtime == null) {
                runtime = new Data();
                data.set("_runtimeData", runtime);
            }
            if(index != null) {
                //If the index is set this is a list item (foo/bar/0/, foo/bar/1/)
                Data fieldIndexData = runtime.get(index);
                if(fieldIndexData==null){
                	fieldIndexData = new Data();
                    runtime.set(index, fieldIndexData);
                }
                fieldIndexData.set("id-translation", translation);
            }else{
            	//Otherwise set the translation directly referenced by the field
                //If the index is set this is a list item (foo/bar/0/, foo/bar/1/)
                Data fieldData = runtime.get(field);
                if(fieldData==null){
                	fieldData = new Data();	
                    runtime.set(field, fieldData);
                }
                fieldData.set("id-translation", translation);
            }
        }
    }
}
