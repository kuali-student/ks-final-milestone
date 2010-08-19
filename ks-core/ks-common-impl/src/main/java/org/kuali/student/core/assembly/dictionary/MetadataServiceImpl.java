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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.UILookupConfig;
import org.kuali.student.core.assembly.data.UILookupData;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.dictionary.dto.CaseConstraint;
import org.kuali.student.core.dictionary.dto.CommonLookupParam;
import org.kuali.student.core.dictionary.dto.Constraint;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.dto.WhenConstraint;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    private Map<String, UILookupConfig> lookupObjectStructures;
    private String uiLookupContext; 
    
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
     * This method gets the metadata for the given object key and state
     * 
     * @param objectKey
     * @param type
     * @param state
     * @return
     */
    public Metadata getMetadata(String objectKey, String state){
        if (metadataRepository == null || metadataRepository.get(objectKey) == null){
            return getMetadataFromDictionaryService(objectKey, state);
        }
        
        return null;
    }
       
    /**
     * This method gets the metadata for the given object key for state DRAFT.
     * 
     * @see MetadataServiceImpl#getMetadata(String, String)
     * @param objectKey
     * @return
     */
    public Metadata getMetadata(String objectKey){        
        return getMetadata(objectKey, null);
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
    protected Metadata getMetadataFromDictionaryService(String objectKey, String state){

        Metadata metadata = new Metadata();      

        ObjectStructureDefinition objectStructure = getObjectStructure(objectKey);
        
            
        metadata.setProperties(getProperties(objectStructure, state, new RecursionCounter()));
        
        metadata.setWriteAccess(WriteAccess.ALWAYS);
        metadata.setDataType(DataType.DATA);
        addLookupstoMetadata(metadata);
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
    private Map<String, Metadata> getProperties(ObjectStructureDefinition objectStructure, String state, RecursionCounter counter){
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
	            metadata.setConstraints(getConstraints(fd,state));
	            metadata.setCanEdit(!fd.isReadOnly());
	            metadata.setCanUnmask(!fd.isMask());
	            metadata.setCanView(!fd.isHide());
	            metadata.setDynamic(fd.isDynamic());
	            metadata.setLabelKey(fd.getLabelKey());
	           	metadata.setDefaultValue(convertDefaultValue(metadata.getDataType(), fd.getDefaultValue()));            	
	            
	            //TODO: Need for a way to obtain full lookup info from a UI lookup config
	            //metadata.setInitialLookup(initialLookup);
	           
	                                   
	            //Get properties for nested object structure
	            Map<String, Metadata> nestedProperties = null;
	            if (fd.getDataType() == org.kuali.student.core.dictionary.dto.DataType.COMPLEX && fd.getDataObjectStructure() != null){
	                nestedProperties = getProperties(fd.getDataObjectStructure(), state, counter );                
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
        
        if (dictionaryService == null){
        	throw new RuntimeException("Dictionary service not provided for objectKey=[" + objectKey+"].");
        }
        
        return dictionaryService.getObjectStructure(objectKey);
    }
    
          
    protected List<ConstraintMetadata> getConstraints(FieldDefinition fd, String state){
        List<ConstraintMetadata> constraints = new ArrayList<ConstraintMetadata>();
       
        //For now ignoring the serverSide flag and making determination of which constraints
        //should be passed up to the UI via metadata.
        ConstraintMetadata constraintMetadata = new ConstraintMetadata();
        
        //Min Length
        constraintMetadata.setMinLength(fd.getMinLength());
        
        //Max Length
        try {
        	if(fd.getMaxLength()!=null){
        		constraintMetadata.setMaxLength(Integer.parseInt(fd.getMaxLength()));
        	}
    		//Do we need to add another constraint and label it required if minOccurs = 1
        } catch (NumberFormatException nfe) {
			// Ignoring an unbounded length, cannot be handled in metadata structure, maybe change Metadata to string or set to -1
        	constraintMetadata.setMaxLength(9999);
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
        	// Setting unbounded to a value of 9999, since unbounded not handled by metadata
        	if (FieldDefinition.UNBOUNDED.equals(maxOccurs)){
	        	constraintMetadata.setId("repeating");
	        	constraintMetadata.setMaxOccurs(9999);
        	}
        }
        
        //Min Value
        constraintMetadata.setMinValue(fd.getExclusiveMin());
        
        //Max Value
        constraintMetadata.setMaxValue(fd.getInclusiveMax());
                        
        if (fd.getValidChars() != null){
        	constraintMetadata.setValidChars(fd.getValidChars().getValue());
        	constraintMetadata.setValidCharsMessageId(fd.getValidChars().getLabelKey());
        }
        
        //Case constraints - Currently this is only checking case constraints to determine
        //the ConstraintMetadata.isRequiredForNextState flag        
        String nextState = null;
        if (state==null || "DRAFT".equals(state.toUpperCase())){
        	nextState = "SUBMITTED";        	
        } else if ("SUBMITTED".equals(state.toUpperCase())){
        	nextState = "ACTIVE";
        }
        if (nextState != null && fd.getCaseConstraint() != null){
        	CaseConstraint caseConstraint = fd.getCaseConstraint();
        	String fieldPath = caseConstraint.getFieldPath();
        	if (fieldPath.equals("state")){
        		List<WhenConstraint> whenConstraints = caseConstraint.getWhenConstraint();
        		if ("EQUALS".equals(caseConstraint.getOperator()) && whenConstraints != null){
        			for (WhenConstraint whenConstraint:whenConstraints){
        				List<Object> values = whenConstraint.getValues();
        				if (values != null && values.contains(nextState.toUpperCase())){
        					Constraint constraint = whenConstraint.getConstraint();
        					if (constraint.getMinOccurs() > 0){
        						constraintMetadata.setRequiredForNextState(true);
        					}
        				}
        			}
        		}
        	}
        }
        
        constraints.add(constraintMetadata);
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
                    v = new Data.StringValue(s);
                    break; 
                case BOOLEAN:
                    v = new Data.BooleanValue(Boolean.valueOf(s));
                    break;
                case FLOAT:
                    v = new Data.FloatValue(Float.valueOf(s));
                    break;
                case DATE:
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");                    
                    try {
                        v = new Data.DateValue(format.parse(s));
                    } catch (ParseException e) {
                        LOG.error("Unable to get default date value from metadata definition");
                    }
                    break;
                case LONG:
                	if (!s.isEmpty()){
                		v = new Data.LongValue(Long.valueOf(s));
                	}
                    break;
                case DOUBLE:
                    v = new Data.DoubleValue(Double.valueOf(s));
                    break;
                case INTEGER:
                    v = new Data.IntegerValue(Integer.valueOf(s));
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

    
    protected DataType convertDictionaryDataType(org.kuali.student.core.dictionary.dto.DataType dataType){
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
    
    public void setUiLookupContext(String uiLookupContext){
    	this.uiLookupContext=uiLookupContext;
    	init();
    	
    }
    
    private void init(){
    	ApplicationContext ac = new ClassPathXmlApplicationContext(uiLookupContext);

		Map<String, UILookupConfig> beansOfType = (Map<String, UILookupConfig>) ac.getBeansOfType(UILookupConfig.class);
		lookupObjectStructures = new HashMap<String, UILookupConfig>();
		for (UILookupConfig objStr : beansOfType.values()){
			lookupObjectStructures.put(objStr.getPath(), objStr);
		}
		System.out.println("UILookup loaded");
    }
    
    private void addLookupstoMetadata(Metadata metadata){
    	if (lookupObjectStructures != null){
	    	Collection<UILookupConfig> lookups = lookupObjectStructures.values();
	    	for(UILookupConfig lookup: lookups){
	    		Map<String,Metadata> parsedMetadataMap = metadata.getProperties();
	    		Metadata parsedMetadata = null;
	    		String lookupFieldPath = lookup.getPath();
	    		String[] lookupPathTokens = getPathTokens(lookupFieldPath);
	            for(int i = 1; i < lookupPathTokens.length; i++) {
	                if(parsedMetadataMap == null) {
	                    break;
	                }
	                if(i==lookupPathTokens.length-1){
	                	//get the metadata on the last path key token
	                	parsedMetadata=parsedMetadataMap.get(lookupPathTokens[i]);
	                }
	                if(parsedMetadataMap.get(lookupPathTokens[i])!=null){
	                	parsedMetadataMap = parsedMetadataMap.get(lookupPathTokens[i]).getProperties();
	                }
	                else if(parsedMetadataMap.get("*")!=null){
	                	//Lookup wildcard in case of unbounded elements in metadata.
	                	parsedMetadataMap = parsedMetadataMap.get("*").getProperties();
	                	i--;
	                }
	
	            }
	            if (parsedMetadata != null) {
	            	UILookupData initialLookup =lookup.getInitialLookup();
	            	if(initialLookup!=null){
	            		mapLookupDatatoMeta(initialLookup);
	            		parsedMetadata.setInitialLookup(mapLookupDatatoMeta(lookup.getInitialLookup()));
	            	}
	            	List<LookupMetadata> additionalLookupMetadata = null;
	            	if (lookup.getAdditionalLookups() != null) {
						additionalLookupMetadata = new ArrayList<LookupMetadata>();
						for (UILookupData additionallookup : lookup.getAdditionalLookups()) {
							additionalLookupMetadata
									.add(mapLookupDatatoMeta(additionallookup));
						}
						parsedMetadata
								.setAdditionalLookups(additionalLookupMetadata);
					}
	            }
	    	}
    	}                    
    }
    
    private LookupMetadata mapLookupDatatoMeta(UILookupData lookupData){
    	LookupMetadata lookupMetadata = new LookupMetadata();
    	List<LookupParamMetadata> paramsMetadata;
		BeanUtils.copyProperties(lookupData,lookupMetadata, new String[]{"widget","usage","widgetOptions","params"});
		if(lookupData.getWidget()!=null){
			lookupMetadata.setWidget(org.kuali.student.core.assembly.data.LookupMetadata.Widget.valueOf(lookupData.getWidget().toString()));
		}
		if(lookupData.getUsage()!=null){
			lookupMetadata.setUsage(org.kuali.student.core.assembly.data.LookupMetadata.Usage.valueOf(lookupData.getUsage().toString()));
		}
		if(lookupData.getParams()!=null){
			paramsMetadata = new ArrayList<LookupParamMetadata>();
			for(CommonLookupParam param: lookupData.getParams()){
				paramsMetadata.add(mapLookupParamMetadata(param));
			}
			lookupMetadata.setParams(paramsMetadata);
		}
		//WidgetOptions is not used as of now. So not setting it into metadata.
    	return lookupMetadata;
    }
    
    private LookupParamMetadata mapLookupParamMetadata(CommonLookupParam param){
    	LookupParamMetadata paramMetadata = new LookupParamMetadata();
		BeanUtils.copyProperties(param,paramMetadata,new String[]{"childLookup","dataType","writeAccess","usage","widget"});
		if(param.getChildLookup()!=null){
			paramMetadata.setChildLookup(mapLookupDatatoMeta((UILookupData) param.getChildLookup()));
		}
		if(param.getDataType()!=null){
			paramMetadata.setDataType(org.kuali.student.core.assembly.data.Data.DataType.valueOf(param.getDataType().toString()));
		}
		if(param.getWriteAccess()!=null){
			paramMetadata.setWriteAccess(org.kuali.student.core.assembly.data.Metadata.WriteAccess.valueOf(param.getWriteAccess().toString()));
		}
		if(param.getUsage()!=null){
			paramMetadata.setUsage(org.kuali.student.core.assembly.data.LookupMetadata.Usage.valueOf(param.getUsage().toString()));
		}
		if(param.getWidget()!=null){
			paramMetadata.setWidget(org.kuali.student.core.assembly.data.LookupParamMetadata.Widget.valueOf(param.getWidget().toString()));
		}
		
    	return paramMetadata;
    }
    
    private static String[] getPathTokens(String fieldPath) {
        return (fieldPath != null && fieldPath.contains(".") ? fieldPath.split("\\.") : new String[]{fieldPath});
    }
}
