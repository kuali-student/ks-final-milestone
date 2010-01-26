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
    final Logger LOG = Logger.getLogger(MetadataServiceImpl.class);
    
    private String metadataContext;
    private static Map<String, Object> metadataRepository;
    
    private static class RecursionCounter{
        public static final int MAX_DEPTH = 4;
        
        private Map<String, Integer> recursions = new HashMap<String, Integer>();
        
        public int increment(String objectName){
            Integer hits = recursions.get(objectName);
            
            if (hits == null){
                hits = new Integer(1);
            } else {
                hits++;
            }
            recursions.put(objectName, hits);
            return hits;
        }        
    }
    
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
            metadataRepository.put(dataObjStr.getName(), getProperties(dataObjStr, new RecursionCounter()));
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
        //return the clone
        return new Metadata(metadata);
    }    
    
    /**
     * Retreives data object structure from the spring file and caches the properties
     * 
     * @param properties
     * @param fields
     */
    protected void loadProperties(Map<String, Metadata> properties, List<DataFieldDescriptor> fields, RecursionCounter counter){

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
                    repeatingMetadata.setProperties(getProperties(field.getDataObjectStructure(), counter));
                }
                
                Map<String, Metadata> repeatingProperty = new HashMap<String, Metadata>();
                repeatingProperty.put("*", repeatingMetadata);
                metadata.setProperties(repeatingProperty);
            } else if (field.getDataObjectStructure() != null){
                metadata.setProperties(getProperties(field.getDataObjectStructure(), counter));
            }

            properties.put(field.getName(), metadata);
        }        
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, Metadata> getProperties(DataObjectStructure dataObjectStructure, RecursionCounter counter){
        String objectId = dataObjectStructure.getName();
        int hits = counter.increment(objectId);
        
        if (hits == 1 && metadataRepository.containsKey(objectId)){
            return (Map<String, Metadata>)metadataRepository.get(objectId);
        } else if (hits < RecursionCounter.MAX_DEPTH){
            Map<String, Metadata> properties = new HashMap<String, Metadata>();
            if (hits == 1){
                metadataRepository.put(objectId, properties);
            }
            loadProperties(properties, dataObjectStructure.getFields(), counter);
            return properties;
        } else {
            return null;
        }
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
