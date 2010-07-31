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

package org.kuali.student.core.assembly.dictionary.poc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.kuali.student.core.dictionary.poc.dto.FieldDefinition;
import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.poc.DictionaryService;

/**
 * This class provides metadata lookup for service dto objects.
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
     * Create a metadata service initializing it with all known dictionary services
     * 
     * @param dictionaryServices
     */
    public MetadataServiceImpl(DictionaryService...dictionaryServices){        
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
    public Metadata getMetadata(String objectKey){
        if (metadataRepository == null || metadataRepository.get(objectKey) == null){
            return getMetadataFromDictionaryService(objectKey);
        }
        
        return null;
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
    protected Metadata getMetadataFromDictionaryService(String objectKey){

        Metadata metadata = new Metadata();      

        ObjectStructureDefinition objectStructure = getObjectStructure(objectKey);
        
            
        metadata.setProperties(getProperties(objectStructure, new RecursionCounter()));
        
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
    private Map<String, Metadata> getProperties(ObjectStructureDefinition objectStructure, RecursionCounter counter){
    	String objectId = objectStructure.getName();
    	int hits = counter.increment(objectId);
        
    	Map<String, Metadata> properties = null;
    	
    	if (hits < RecursionCounter.MAX_DEPTH){   	    
    		properties = new HashMap<String, Metadata>();
    		
    		List<FieldDefinition> attributes = objectStructure.getAttributes();
	    	for (FieldDefinition fd:attributes){
	            
	            Metadata metadata = new Metadata();
	           
	            //Set constraints, authz flags, default value
	            metadata.setWriteAccess(WriteAccess.ALWAYS);
	            metadata.setDataType(convertDictionaryDataType(fd.getDataType()));
	            metadata.setConstraints(getConstraints(fd));
	            metadata.setCanEdit(!fd.isReadOnly());
	            metadata.setCanUnmask(!fd.isMask());
	            metadata.setCanView(!fd.isHide());
	            
	           	metadata.setDefaultValue(convertDefaultValue(metadata.getDataType(), fd.getDefaultValue()));            	
	            
	            //TODO: Need for a way to obtain full lookup info from a UI lookup config
	            //metadata.setInitialLookup(initialLookup);
	           
	                                   
	            //Get properties for nested object structure
	            Map<String, Metadata> nestedProperties = null;
	            if (fd.getDataType() == org.kuali.student.core.dictionary.poc.dto.DataType.COMPLEX && fd.getDataObjectStructure() != null){
	                nestedProperties = getProperties(fd.getDataObjectStructure(), counter );                
	            }
	            
	            //For repeating field, create a LIST with wildcard in metadata structure
	            if (isRepeating(fd)){
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
    	}
        
    	counter.decrement(objectId);
        return properties;
    }
    
	/** 
     * This method determines if a field is repeating
     * 
     * @param fd
     * @return
     */
    protected boolean isRepeating(FieldDefinition fd){
        return FieldDefinition.UNBOUNDED.equals(fd.getMaxOccurs());
    }
    
    /** 
     * This method gets the object structure for given objectKey from a dictionaryService
     * 
     * @param objectKey
     * @return
     */
    protected ObjectStructureDefinition getObjectStructure(String objectKey){
        DictionaryService dictionaryService = dictionaryServiceMap.get(objectKey);
        
        return dictionaryService.getObjectStructure(objectKey);
    }
    
          
    protected List<ConstraintMetadata> getConstraints(FieldDefinition fd){
        List<ConstraintMetadata> constraints = null;
       
        //For now ignoring the serverSide flag and making determination of which constraints
        //should be passed up to the UI via metadata.
        ConstraintMetadata constraintMetadata = new ConstraintMetadata();
        
        //Min Length
        constraintMetadata.setMinLength(fd.getMinLength());
        
        //Max Length
        try {
        	constraintMetadata.setMaxLength(Integer.parseInt(fd.getMaxLength()));

    		//Do we need to add another constraint and label it required if minOccurs = 1
        } catch (NumberFormatException nfe) {
			// Ignoring an unbounded length, cannot be handled in metadata structure, maybe change Metadata to string or set to -1
		}
        
        //Min Occurs
        constraintMetadata.setMinOccurs(fd.getMinOccurs());
        
        //Max Occurs
    	String maxOccurs = fd.getMaxOccurs();
        try {        	
        	constraintMetadata.setMaxOccurs(Integer.parseInt(maxOccurs));
        	if (!FieldDefinition.SINGLE.equals(maxOccurs)){
        		constraintMetadata.setId("repeating");
        	}
        } catch (NumberFormatException nfe){
        	// Ignoring unbounded max occurs, cannot be handled in metadata structure, mabe change metadata to string or set to -1
        }
        
        //Min Value
        constraintMetadata.setMinValue(fd.getExclusiveMin());
        
        //Max Value
        constraintMetadata.setMaxValue(fd.getInclusiveMax());
                        
        if (fd.getValidChars() != null){
        	constraintMetadata.setValidChars(fd.getValidChars().getValue());
        }
        
       return constraints;
    }
    
    /**
     * Convert Object value to respective DataType. Method return null for object Value.
     * 
     * @param dataType
     * @param value
     * @return
     */
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
        } else {
        	v = convertDefaultValue(value);
        }
        
        return v;
    }
    
    protected Value convertDefaultValue(Object value) {
    	Value v = null;
    	
    	if (value instanceof String){
    		v = new Data.StringValue((String)value);
    	} else if (value instanceof Boolean){
    		v = new Data.BooleanValue((Boolean)value);
    	} else if (value instanceof Integer){
    		v = new Data.IntegerValue((Integer)value);
    	} else if (value instanceof Double){
    		v = new Data.DoubleValue((Double)value);
    	} else if (value instanceof Long){
    		v = new Data.LongValue((Long)value);
    	} else if (value instanceof Short){
    		v = new Data.ShortValue((Short)value);
    	} else if (value instanceof Float){
    		v = new Data.FloatValue((Float)value);
    	}
    	
		return v;
	}

    
    protected DataType convertDictionaryDataType(org.kuali.student.core.dictionary.poc.dto.DataType dataType){
        switch (dataType){
        	case STRING:  	return DataType.STRING;
        	case BOOLEAN: 	return DataType.BOOLEAN;
        	case INTEGER: 	return DataType.INTEGER;
        	case FLOAT:		return DataType.FLOAT;
        	case COMPLEX: 	return DataType.DATA;
        	case DATE:		return DataType.DATE;
        	case DOUBLE:	return DataType.DOUBLE;
        	case LONG:		return DataType.LONG;
        }
        
        return null;        
    }
}
