/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.common.assembly.dictionary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.client.ConstraintMetadata;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.Data.DataType;
import org.kuali.student.common.assembly.client.Data.Value;
import org.kuali.student.common.assembly.client.Metadata.WriteAccess;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class provides metadata lookup services for orchestration objects.
 * 
 *  TODO: 
 *      1) Handle type state configuration
 *      2) Differnetiate b/w metadata structure required for client vs. assemblers      
 * 
 * @author Kuali Student Team
 *
 */
public class MetadataServiceImpl {
    private String metadataContext;
    private static Map<String, Object> metadataRepository;
    
    final Logger LOG = Logger.getLogger(MetadataServiceImpl.class);
    
    /**
     * Create a Metadata service initialized using a given classpath metadata context file
     * 
     * @param metadataContext the classpath metadata context file
     */
    public MetadataServiceImpl(String metadataContext){
        this.metadataContext = metadataContext;
        init();
    }
    
    @SuppressWarnings("unchecked")
    private void init(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(metadataContext);
                
        Map<String, DataObjectStructure> beansOfType = (Map<String, DataObjectStructure>) context.getBeansOfType(DataObjectStructure.class);
        metadataRepository = new HashMap<String, Object>();
        for (DataObjectStructure dataObjStr : beansOfType.values()){
            metadataRepository.put(dataObjStr.getName(), getProperties(dataObjStr));                    
        }               
    }
    
    /** 
     * This method gets the metadata for the given object key
     * 
     * @param objectKey
     * @param type
     * @param state
     * @return
     */
    @SuppressWarnings("unchecked")
    public Metadata getMetadata(String objectKey, String type, String state){
        Metadata metadata = new Metadata ();
        metadata.setWriteAccess(WriteAccess.ALWAYS);
        metadata.setOnChangeRefreshMetadata(false);
        metadata.setDataType(DataType.DATA);
        metadata.setProperties((Map<String, Metadata>)metadataRepository.get(objectKey));
        return metadata;
    }    
    
    /**
     * Retreives data object structure from the spring file and caches the properties
     * 
     * @param properties
     * @param fields
     */
    protected void loadProperties(Map<String, Metadata> properties, List<DataFieldDescriptor> fields){

        for (DataFieldDescriptor field:fields){
            Metadata metadata = new Metadata();
            metadata.setWriteAccess(WriteAccess.valueOf(field.getWriteAccess()));
            metadata.setDataType(DataType.valueOf(field.getDataType()));
            metadata.setAdditionalLookups(field.getAdditionalLookups());
            metadata.setLookupContextPath(field.getLookupContextPath());
            
            metadata.setConstraints(field.getConstraints());
            
            //Determine if repeating element, which needs to be wrapped in a '*' property
            boolean isRepeating = false;
            if (field.getConstraints() != null) {
                for (ConstraintMetadata c : field.getConstraints()) {
                    if (c.getId() != null && c.getId().equals("repeating")) {
                        isRepeating = true;
                        break;
                    }
                }
            }
            
            metadata.setDefaultValue(convertDefaultValue(metadata.getDataType(), field.getDefaultValue()));
            metadata.setLookupMetadata(field.getLookups());
            
            if (isRepeating){
                Metadata repeatingMetadata = new Metadata();
                metadata.setDataType(DataType.LIST);
                
                repeatingMetadata.setWriteAccess(WriteAccess.ALWAYS);
                repeatingMetadata.setOnChangeRefreshMetadata(false);
                repeatingMetadata.setDataType(DataType.valueOf(field.getDataType()));
                                
                if (field.getDataObjectStructure() != null){                   
                    repeatingMetadata.setProperties(getProperties(field.getDataObjectStructure()));
                }
                
                Map<String, Metadata> repeatingProperty = new HashMap<String, Metadata>();
                repeatingProperty.put("*", repeatingMetadata);
                metadata.setProperties(repeatingProperty);
            } else if (field.getDataObjectStructure() != null){
                metadata.setProperties(getProperties(field.getDataObjectStructure()));
            }

            properties.put(field.getName(), metadata);
        }        
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, Metadata> getProperties(DataObjectStructure dataObjectStructure){
        String objectId = dataObjectStructure.getName();                    
        if (!metadataRepository.containsKey(objectId)){
            Map<String, Metadata> properties = new HashMap<String, Metadata>();
            metadataRepository.put(objectId, properties);
            loadProperties(properties, dataObjectStructure.getFields());
            return properties;
        } else{
            //FIXME: Circular reference is causing a problem
            //return (Map<String, Metadata>)metadataRepository.get(objectId);
            
            return copyPropertyMap(objectId);        
        }               
    }

    @SuppressWarnings("unchecked")
    /**
     * This is for testing only to prevent circular references in metadata structures
     */
    protected Map<String, Metadata> copyPropertyMap(String objectId){
        Map<String, Metadata> metaData = (Map<String, Metadata>)metadataRepository.get(objectId); 
        Map<String, Metadata> newMetadata = new HashMap<String, Metadata>();
        for (String key:metaData.keySet()){
            newMetadata.put(key, metaData.get(key));
        }
        return newMetadata;        
    }
    
    protected Value convertDefaultValue(DataType dataType, Object value){
        Value v = null;
        if (value instanceof String){
            String s = (String)value;
            switch (dataType){
                case STRING:
                    value = new Data.StringValue(s);
                    break; 
                case BOOLEAN:
                    value = new Data.BooleanValue(Boolean.valueOf(s));
                    break;
                case FLOAT:
                    value = new Data.FloatValue(Float.valueOf(s));
                    break;
                case DATE:
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");                    
                    try {
                        value = new Data.DateValue(format.parse(s));
                    } catch (ParseException e) {
                        LOG.error("Unable to get default date value from metadata definition");
                    }
                    break;
                case LONG:
                    value = new Data.LongValue(Long.valueOf(s));
                    break;
                case DOUBLE:
                    value = new Data.DoubleValue(Double.valueOf(s));
                    break;
                case INTEGER:
                    value = new Data.IntegerValue(Integer.valueOf(s));
                    break;                    
            }
        }
        
        return v;
    }

}
