/**
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

package org.kuali.student.core.assembly.dictionary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.dictionary.dto.ConstraintDescriptor;
import org.kuali.student.core.dictionary.dto.ConstraintSelector;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

/**
 * This class provides metadata lookup services for orchestration objects.
 * 
 *  TODO: 
 *      1) Handle type state configuration & better caching
 *      2) Differentiate b/w metadata structure required for client vs. assemblers
 *      3) Namespace collision b/w service dictionaries and orchestration dictionary?      
 * 
 * @author Kuali Student Team
 *
 */
public class MetadataServiceImpl {
    final Logger LOG = Logger.getLogger(MetadataServiceImpl.class);
    
    private Map<String, Object> metadataRepository = null;
    
    private Map<String, DictionaryService> dictionaryServiceMap;
    
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
        
        public int decrement(String objectName){
            Integer hits = recursions.get(objectName);
             if (hits >= 1){
                 hits--;
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
        init(metadataContext, (DictionaryService[])null);
    }
    
    public MetadataServiceImpl(DictionaryService...dictionaryServices){        
        init(null, dictionaryServices);
    }

    public MetadataServiceImpl(String metadataContext, DictionaryService...dictionaryServices){        
        init(metadataContext, dictionaryServices);
    }
    
    @SuppressWarnings("unchecked")
    private void init(String metadataContext, DictionaryService...dictionaryServices){
        if (metadataContext != null){
        	String[] locations = StringUtils.tokenizeToStringArray(metadataContext, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
    		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(locations);
                    
            Map<String, DataObjectStructure> beansOfType = (Map<String, DataObjectStructure>) context.getBeansOfType(DataObjectStructure.class);
            metadataRepository = new HashMap<String, Object>();
            for (DataObjectStructure dataObjStr : beansOfType.values()){
                metadataRepository.put(dataObjStr.getName(), getProperties(dataObjStr, new RecursionCounter()));
            }
        }
        
        if (dictionaryServices != null){
            this.dictionaryServiceMap = new HashMap<String, DictionaryService>();
            for (DictionaryService d:dictionaryServices){
                List<String> objectTypes = d.getObjectTypes();
                for(String objectType:objectTypes){
                    dictionaryServiceMap.put(objectType, d);
                }            
            }            
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
        if (metadataRepository == null || metadataRepository.get(objectKey) == null){
            return getMetadataFromDictionaryService(objectKey, type, state);
        } else {        
            Metadata metadata = new Metadata ();
            metadata.setWriteAccess(WriteAccess.ALWAYS);
            metadata.setOnChangeRefreshMetadata(false);
            metadata.setDataType(DataType.DATA);

            metadata.setProperties((Map<String, Metadata>)metadataRepository.get(objectKey));
            
            //return the clone
            return new Metadata(metadata);
        }        
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
            metadata.setName(field.getName());
            metadata.setCanEdit(field.isCanEdit());
            metadata.setCanView(field.isCanView());
            metadata.setCanUnmask(field.isCanUnmask());
            metadata.setDefaultValue(convertDefaultValue(metadata.getDataType(), field.getDefaultValue()));
            metadata.setInitialLookup(field.getInitialLookup());
            
            if (isRepeating(field)){
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
    
    /** 
     * This method determines if a field is a repeating field
     * 
     * @param field
     * @return
     */
    protected boolean isRepeating(DataFieldDescriptor field){
        if (field.getConstraints() != null) {
            for (ConstraintMetadata c : field.getConstraints()) {
                if (c.getId() != null && c.getId().equals("repeating")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, Metadata> getProperties(DataObjectStructure dataObjectStructure, RecursionCounter counter){
        String objectId = dataObjectStructure.getName();
        int hits = counter.increment(objectId);
        
        Map<String, Metadata> properties = null;
        
        if (hits == 1 && metadataRepository.containsKey(objectId)){
            properties =  (Map<String, Metadata>)metadataRepository.get(objectId);
        } else if (hits < RecursionCounter.MAX_DEPTH){
            properties = new HashMap<String, Metadata>();
            if (hits == 1){
                metadataRepository.put(objectId, properties);
            }
            loadProperties(properties, dataObjectStructure.getFields(), counter);
        }
        
        counter.decrement(objectId);
        return properties;
    }
    
    
    /** 
     * This invokes the appropriate dictionary service to get the object structure and then
     * converts it to a metadata structure.
     * 
     * @param objectKey
     * @param type
     * @param state
     * @return
     */
    protected Metadata getMetadataFromDictionaryService(String objectKey, String type, String state){

        Metadata metadata = new Metadata();      

        ObjectStructure objectStructure = getObjectStructure(objectKey);
        State objectState = getObjectState(objectStructure, type, state);
        
        ConstraintDescriptor constraintDescriptor = objectState.getConstraintDescriptor(); 
        metadata.setConstraints(copyConstraints(constraintDescriptor));
        
        List<Field> fields = objectState.getField();
        metadata.setProperties(getProperties(fields, type, state));
        
        metadata.setWriteAccess(WriteAccess.ALWAYS);
        metadata.setDataType(DataType.DATA);
        
        return metadata;
    }
    
    /** 
     * This method is used to convert a list of dictionary fields into metadata properties
     * 
     * @param fields
     * @param type
     * @param state
     * @return
     */
    private Map<String, Metadata> getProperties(List<Field> fields, String type, String state){
        Map<String, Metadata> properties = new HashMap<String, Metadata>();
        
        for (Field field:fields){
            FieldDescriptor fd = field.getFieldDescriptor();
            
            Metadata metadata = new Metadata();
            metadata.setWriteAccess(WriteAccess.ALWAYS);
            metadata.setDataType(convertDictionaryDataType(fd.getDataType()));
            metadata.setConstraints(copyConstraints(field.getConstraintDescriptor()));            
            
            //Where to get values for defaultValue, lookupMetdata (SearchSelector,fd.getSearch()), 
                                   
            Map<String, Metadata> nestedProperties = null;
            if (fd.getDataType().equals("complex")){                                                                  
                ObjectStructure objectStructure;
                if (fd.getObjectStructure() != null){
                    objectStructure = fd.getObjectStructure();
                } else {
                    String objectKey = fd.getObjectStructureRef();
                    objectStructure = getObjectStructure(objectKey);
                }

                State objectState = getObjectState(objectStructure, type, state);
                nestedProperties = getProperties(objectState.getField(), type, state);
                
                //Cross field constraints for nested object fields? What to do about them?
                //ConstraintDescriptor constraintDescriptor = objectState.getConstraintDescriptor()

            }
            
            
            if (isRepeating(field)){
                Metadata repeatingMetadata = new Metadata();
                metadata.setDataType(DataType.LIST);
                
                repeatingMetadata.setWriteAccess(WriteAccess.ALWAYS);
                repeatingMetadata.setOnChangeRefreshMetadata(false);
                repeatingMetadata.setDataType(convertDictionaryDataType(fd.getDataType()));
                
                if (nestedProperties != null){
                    repeatingMetadata.setProperties(nestedProperties);
                }
                
                Map<String, Metadata> repeatingProperty = new HashMap<String, Metadata>();
                repeatingProperty.put("*", repeatingMetadata);
                metadata.setProperties(repeatingProperty);
            } else if (nestedProperties != null){
                metadata.setProperties(nestedProperties);
            }
            
            properties.put(fd.getName(), metadata);
            
        }
        
        return properties;
    }
    
    
    /** 
     * This method determines if a field is repeating
     * 
     * @param field
     * @return
     */
    protected boolean isRepeating(Field field){
        if (field.getConstraintDescriptor() != null && field.getConstraintDescriptor().getConstraint() != null){
            for (ConstraintSelector c:field.getConstraintDescriptor().getConstraint()){
                if (c.getKey().equals("repeating")){
                    return true;
                }
            }
        }
        return false;
    }
    
    /** 
     * This method gets the object structure for given objectKey from a dictionaryService
     * 
     * @param objectKey
     * @return
     */
    protected ObjectStructure getObjectStructure(String objectKey){
        DictionaryService dictionaryService = dictionaryServiceMap.get(objectKey);
        
        return dictionaryService.getObjectStructure(objectKey);
    }
    
    /**
     * This method retrieves the desire object state for the object structure. 
     *
     * @param objectStructure
     * @param type
     * @param state
     * @return
     */
    protected State getObjectState(ObjectStructure objectStructure, String type, String state){
        //This method would not be required if we could just get objectStructure for a particular 
        //type/state from the dictionary service
        for (Type t:objectStructure.getType()){
            if (t.getKey().equals(type)){
                for (State s:t.getState()){
                    if (s.getKey().equals(state)){
                        return s;
                    }
                }
            }
        }
        
        return null;
    }
           
    protected List<ConstraintMetadata> copyConstraints(ConstraintDescriptor constraintDescriptor){
        List<ConstraintMetadata> constraints = null;
       
        if (constraintDescriptor != null && constraintDescriptor.getConstraint() != null){           
            constraints = new ArrayList<ConstraintMetadata>();
            
            
            for (ConstraintSelector dictConstraint:constraintDescriptor.getConstraint()){
               ConstraintMetadata constraintMetadata = new ConstraintMetadata();
               
               constraintMetadata.setId(dictConstraint.getKey());
               if (dictConstraint.getMaxLength() != null){
                   constraintMetadata.setMaxLength(Integer.valueOf(dictConstraint.getMaxLength()));
               }
               constraintMetadata.setMinLength(dictConstraint.getMinLength());
               constraintMetadata.setMinOccurs(dictConstraint.getMinOccurs());
               constraintMetadata.setMinValue(dictConstraint.getMinValue());
               
               if (dictConstraint.getValidChars() != null){
                   constraintMetadata.setValidChars(dictConstraint.getValidChars().getValue());
               }
               
               constraintMetadata.setMessageId("kuali.msg.validation." + dictConstraint.getKey());
               
               //Skipping cross field constraints (eg. case, occurs, require)
               
              constraints.add(constraintMetadata);
           }
        }
        
       return constraints;
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
                	if (!s.isEmpty()){
                		value = new Data.LongValue(Long.valueOf(s));
                	}
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
    
    protected DataType convertDictionaryDataType(String dataType){
        if ("string".equals(dataType)){
            return DataType.STRING;
        } else if ("boolean".equals(dataType)){
            return DataType.BOOLEAN;
        } else if ("integer".equals(dataType)){
            return DataType.INTEGER;
        } else if ("datetime".equals(dataType)){
            return DataType.DATE;
        } else if ("complex".equals(dataType)){
            return DataType.DATA;
        }            
        
        return null;        
    }
}
