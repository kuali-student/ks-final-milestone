package org.kuali.student.common.ui.client.validator;

import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.BOOLEAN;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.DATE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.DOUBLE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.FLOAT;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.INTEGER;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.LENGTH_OUT_OF_RANGE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.LONG;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MAX_LENGTH;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MAX_OCCURS;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MAX_VALUE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MIN_LENGTH;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MIN_OCCURS;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MIN_VALUE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.OCCURS;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.OUT_OF_RANGE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.REQUIRED;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.VALID_CHARS;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinLength;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinOccurs;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinValue;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinValueDate;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinValueDouble;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxLength;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxOccurs;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxValue;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxValueDate;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxValueDouble;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.isRequired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.common.validator.DateParser;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.StringKey;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

public class DataModelValidator {
	private static final String RUNTIME_DELETED_KEY = "_runtimeData/deleted";
	private static final String UNBOUNDED_CHECK = null;

	private Stack<String> elementStack = new Stack<String>();
	private DateParser dateParser = null;


	/**
	 * @return the dateParser
	 */
	public DateParser getDateParser() {
		return dateParser;
	}

	/**
	 * @param dateParser
	 *            the dateParser to set
	 */
	public void setDateParser(DateParser dateParser) {
		this.dateParser = dateParser;
	}

	

	public List<ValidationResultContainer> validate(final DataModel model) {
		List<ValidationResultContainer> results = new ArrayList<ValidationResultContainer>();
		
		DataModelDefinition def = (DataModelDefinition) model.getDefinition();
		doValidate(model, def.getMetadata(), new QueryPath(), results);
		
		translateMessages(results);
		
		return results;
	}
	
	
	

    private void doValidate(final DataModel model, final Metadata meta, final QueryPath path, List<ValidationResultContainer> results) {
		switch (meta.getDataType()) {
		case DATA:
			// intentional fallthrough case
		case LIST:
			doValidateComplex(model, meta, path, results);
			break;

		case BOOLEAN:
			doValidateBoolean(model, meta, path, results);
			break;
		
		case DATE:
			// intentional fallthrough case
		case TRUNCATED_DATE:
			doValidateDate(model, meta, path, results);
			break;
		
		case DOUBLE:
			doValidateDouble(model, meta, path, results);
			break;
		
		case FLOAT:
			doValidateFloat(model, meta, path, results);
			break;

		case INTEGER:
			doValidateInteger(model, meta, path, results);
			break;
		
		case LONG:
			doValidateLong(model, meta, path, results);
			break;
		
		case STRING:
			doValidateString(model, meta, path, results);
			break;
		
		default:
			// do nothing
		}
		
	}
	
    private static void addResult(ValidationResultContainer v, List<ValidationResultContainer> list, Metadata meta) {
        list.add(v);
        if (!v.isOk()) {
            populateConstraintInfo(v, meta);
        }
    }
    
	private void doValidateString(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);
		
		validateOccurs(path, values, meta, results);
		
		for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());

			String s = (e.getValue() == null) ? "" : e.getValue().toString();
			
			if (s.isEmpty() && isRequired(meta)) {
				v.addError(REQUIRED.getKey());
			} else {
				int len = s.length();
				Integer minLength = getLargestMinLength(meta);
				Integer maxLength = getSmallestMaxLength(meta);
				
				if (minLength != null && maxLength != null) {
					if (len < minLength || len > maxLength) {
						v.addError(LENGTH_OUT_OF_RANGE.getKey());
					}
				} else if (minLength != null && len < minLength) {
					v.addError(MIN_LENGTH.getKey());
				} else if (maxLength != null && len > maxLength) {
					v.addError(MAX_LENGTH.getKey());
				}
				
				// process valid chars constraint
				if (meta.getConstraints() != null) {
					boolean failed = false;
					for (ConstraintMetadata cons : meta.getConstraints()) {
						if (failed) {
							break;
						}
						String validChars = cons.getValidChars();
						validChars = (validChars == null) ? "" : validChars.trim();
						if (!validChars.isEmpty()) {
							if (validChars.startsWith("regex:")) {
								validChars = validChars.substring(6);
								if (!s.matches(validChars)) {
									v.addError(VALID_CHARS.getKey());
									failed = true;
									break;
								}
							} else {
								for (char c : s.toCharArray()) {
									if (!validChars.contains(String.valueOf(c))) {
										v.addError(VALID_CHARS.getKey());
										failed = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			
			addResult(v, results, meta);
		}
	}
	
	
	private void doValidateInteger(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);
	    
	    validateOccurs(path, values, meta, results);
        
	    for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());
			Object o = e.getValue();
			
			if (o == null) {
				if (isRequired(meta)) {
					v.addError(REQUIRED.getKey());
				}
			} else {
				Integer i = null;
				try {
					i = (o instanceof Integer) ? (Integer) o : Integer.valueOf(o.toString());
				} catch (Exception ex) {
					v.addError(INTEGER.getKey());
				}
				
				if (i != null) {
    				Long min = getLargestMinValue(meta);
    				Long max = getSmallestMaxValue(meta);
    				
    				if (min != null && max != null) {
    					if (i < min || i > max) {
    						v.addError(OUT_OF_RANGE.getKey());
    					}
    				} else if (min != null && i < min) {
    					v.addError(MIN_VALUE.getKey());
    				} else if (max != null && i > max) {
    					v.addError(MAX_VALUE.getKey());
    				}
				}
			}
			
	        addResult(v, results, meta);
		}
	}

	private void doValidateLong(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

        validateOccurs(path, values, meta, results);
        
	    for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());
			Object o = e.getValue();
			
			if (o == null) {
				if (isRequired(meta)) {
					v.addError(REQUIRED.getKey());
				}
			} else {
				Long i = null;
				try {
					i = (o instanceof Long) ? (Long) o : Long.valueOf(o.toString());
				} catch (Exception ex) {
					v.addError(LONG.getKey());
				}
				
				
				if (i != null) {
    				Long min = getLargestMinValue(meta);
    				Long max = getSmallestMaxValue(meta);
    				
    				if (min != null && max != null) {
    					if (i < min || i > max) {
    						v.addError(OUT_OF_RANGE.getKey());
    					}
    				} else if (min != null && i < min) {
    					v.addError(MIN_VALUE.getKey());
    				} else if (max != null && i > max) {
    					v.addError(MAX_VALUE.getKey());
    				}
				}
			}
			
	        addResult(v, results, meta);
		}
	}
	
	private void doValidateDouble(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);
		
        validateOccurs(path, values, meta, results);
        
        for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());
			Object o = e.getValue();
			
			if (o == null) {
				if (isRequired(meta)) {
					v.addError(REQUIRED.getKey());
				}
			} else {
				Double d = null;
				try {
					d = (o instanceof Double) ? (Double) o : Double.valueOf(o.toString());
				} catch (Exception ex) {
					v.addError(DOUBLE.getKey());
				}
				
				
				if (d != null) {
    				Double min = getLargestMinValueDouble(meta);
    				Double max = getSmallestMaxValueDouble(meta);
    				    				
    				if (min != null && max != null) {
    					if (d < min || d > max) {
    						v.addError(OUT_OF_RANGE.getKey());
    					}
    				} else if (min != null && d < min) {
    					v.addError(MIN_VALUE.getKey());
    				} else if (max != null && d > max) {
    					v.addError(MAX_VALUE.getKey());
    				}
				}
			}
			
			addResult(v, results, meta);
		}
	}
	
	private void doValidateFloat(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

        validateOccurs(path, values, meta, results);
        
        for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());
			Object o = e.getValue();
			
			if (o == null) {
				if (isRequired(meta)) {
					v.addError(REQUIRED.getKey());
				}
			} else {
				Float d = null;
				try {
					d = (o instanceof Float) ? (Float) o : Float.valueOf(o.toString());
				} catch (Exception ex) {
					v.addError(FLOAT.getKey());
				}
				

				if (d != null) {
    				Double min = getLargestMinValueDouble(meta);
    				Double max = getSmallestMaxValueDouble(meta);
    				
    				if (min != null && max != null) {
    					if (d < min || d > max) {
    						v.addError(OUT_OF_RANGE.getKey());
    					}
    				} else if (min != null && d < min) {
    					v.addError(MIN_VALUE.getKey());
    				} else if (max != null && d > max) {
    					v.addError(MAX_VALUE.getKey());
    				}
				}
			}
			
			addResult(v, results, meta);
		}
	}
	
	private void doValidateDate(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

        validateOccurs(path, values, meta, results);
        
        for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());
			Object o = e.getValue();
			
			if (o == null) {
				if (isRequired(meta)) {
					v.addError(REQUIRED.getKey());
				}
			} else {
				Date d = null;
				try {
					d = (o instanceof Date) ? (Date) o : dateParser.parseDate(o.toString());
				} catch (Exception ex) {
					v.addError(DATE.getKey());
				}
			
				
				if (d != null) {
					//Get defined min/max value constraint
    				Date min = getLargestMinValueDate(meta, dateParser, getCrossFieldMinValue(model, path, meta));
    				Date max = getSmallestMaxValueDate(meta, dateParser, getCrossFieldMaxValue(model, path, meta));
    				    				
    				if (min != null && max != null) {
    					if (d.getTime() < min.getTime() || d.getTime() > max.getTime()) {
    						v.addError(OUT_OF_RANGE.getKey());
    					}
    				} else if (min != null && d.getTime() < min.getTime()) {
    					v.addError(MIN_VALUE.getKey());
    				} else if (max != null && d.getTime() > max.getTime()) {
    					v.addError(MAX_VALUE.getKey());
    				}
				}
			}
			
			addResult(v, results, meta);
		}
	}
	
	
	private void doValidateBoolean(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultContainer> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

	    validateOccurs(path, values, meta, results);
        
        for (Entry<QueryPath, Object> e : values.entrySet()) {
			ValidationResultContainer v = new ValidationResultContainer(e.getKey().toString());
			Object o = e.getValue();
			
			if (o == null) {
				if (isRequired(meta)) {
					v.addError(REQUIRED.getKey());
				}
			} else {
				if (o instanceof Boolean == false) {
					v.addError(BOOLEAN.getKey());
				}
			}
			
			addResult(v, results, meta);
		}
	}

	private void doValidateComplex(final DataModel model, final Metadata meta, final QueryPath path, List<ValidationResultContainer> results) {
		Map<QueryPath, Object> values = model.query(path);
		ValidationResultContainer v = new ValidationResultContainer(path.toString());
		if (values.isEmpty() && isRequired(meta)) {
			v.addError(REQUIRED.getKey());
		} else if (meta.getDataType().equals(DataType.LIST)){
			for (Map.Entry<QueryPath, Object> e:values.entrySet()){
				QueryPath listPath = QueryPath.parse(e.getKey().toString());
				listPath.add(Data.WILDCARD_KEY);
				values = model.query(listPath);
	
				// do min/max occurs checks
				validateOccurs(e.getKey(), values, meta, results);
			}
		}
		
		addResult(v, results, meta);
		
		// validate children
		String basePath = path.toString();
		if (meta.getProperties() != null) {
			for (Map.Entry<String, Metadata> e : meta.getProperties().entrySet()) {
				
				QueryPath childPath = QueryPath.concat(basePath, e.getKey());
				//System.out.println(childPath.toString());
				doValidate(model, e.getValue(), childPath, results);
			}
		}
	
	}
	
	private static boolean validateOccurs(QueryPath path, Map<QueryPath, Object> values, Metadata meta, List<ValidationResultContainer> results) {
	    
	    int size = getListSize(values, meta);
		
	    Integer min = getLargestMinOccurs(meta);
	    boolean minValid = min == null || min <= size;
	 
	    Integer max = getSmallestMaxOccurs(meta);
        boolean maxValid = max == null || max >= size;
	    
        
		if (!minValid || !maxValid) {
            ValidationResultContainer v = new ValidationResultContainer(path.toString());
            if (!minValid && !maxValid) {
                v.addError(OCCURS.getKey());
            } else if (!minValid) {
                v.addError(MIN_OCCURS.getKey());
            } else {
                v.addError(MAX_OCCURS.getKey());
            }
            addResult(v, results, meta);
        }
        
	    return minValid && maxValid;
	}
	
	private static int getListSize(Map<QueryPath, Object> values, Metadata meta){
	    int size = 0;

	    //Check to see if a complex data element in list has been deleted
	    Map<String, Metadata> properties = meta.getProperties();		
		if (properties.containsKey(Data.WILDCARD_KEY.toString())){
			Metadata listMeta = properties.get(Data.WILDCARD_KEY.toString());
			if (listMeta != null && listMeta.getDataType().equals(DataType.DATA)){
				for (Object value:values.values()){
					Data d = (Data)value;
					Boolean deleted = d.query(RUNTIME_DELETED_KEY);
					if (deleted == null || !deleted){
						size++;
					}
				}
			} else {
				size = values.size();
			}
		} else {
			size = values.size();
		}
		
		return size;
	}
	
	private void translateMessages(List<ValidationResultContainer> results) {
        ApplicationContext context = Application.getApplicationContext();
        
        for (ValidationResultContainer vrc : results) {
            if (!vrc.isOk()) {
                Map<String, Object> constraintInfo = gatherConstraintInfo(vrc); 
                // don't bother translating "OK" messages for now, maybe later if we have a use case
                for (ValidationResultInfo v : vrc.getValidationResults()) {
                    if (v.getLevel() != ErrorLevel.OK) {
                        String raw = v.getMessage();
                        String translated = context.getMessage(raw);
                        if (translated != null && !translated.equals(raw)) {
                            translated = MessageUtils.interpolate(translated, constraintInfo);
                            v.setMessage(translated);
                        }
                    }
                }
            }
        }
    }

	private static void populateConstraintInfo(ValidationResultContainer results, Metadata meta) {
	    results.setDataType(asString(meta.getDataType()));
	    results.setDerivedMaxLength(asString(getSmallestMaxLength(meta)));
	    results.setDerivedMaxOccurs(asString(getSmallestMaxOccurs(meta)));
	    results.setDerivedMinLength(getLargestMinLength(meta));
	    results.setDerivedMinOccurs(getLargestMinOccurs(meta));
	}
	
	private static String asString(Object o) {
	    if (o == null) {
	        return null;
	    } else {
	        return o.toString();
	    }
	}
	
    private Map<String, Object> gatherConstraintInfo(ValidationResultContainer vrc) {
        Map<String, Object> m = new HashMap<String, Object>();
        put(m, "field", vrc.getElement());
        put(m, "minOccurs", vrc.getDerivedMinOccurs());
        put(m, "maxOccurs", vrc.getDerivedMaxOccurs());
        put(m, "minLength", vrc.getDerivedMinLength());
        put(m, "maxLength", vrc.getDerivedMaxLength());
        put(m, "dataType", vrc.getDataType());
        return m;
    }
    
	//FIXME: This is a temp solution for getting cross field min value
    private Object getCrossFieldMinValue(DataModel model, QueryPath path, Metadata meta){
    	Object v = null;
    	for (ConstraintMetadata cons : meta.getConstraints()) {
			if (cons.getMinValue() != null && cons.getMinValue().contains("../")){
				QueryPath crossFieldPath = QueryPath.parse(path.toString());
				String crossFieldKey = cons.getMinValue().substring(3);
				crossFieldPath.remove(crossFieldPath.size()-1);
				crossFieldPath.add(new StringKey(crossFieldKey));
				v = model.get(crossFieldPath);
			}
		}
    	
    	return v;
    }
    
	//FIXME: This is a temp solution for getting cross field max value
    private Object getCrossFieldMaxValue(DataModel model, QueryPath path, Metadata meta){
    	Object v = null;
    	for (ConstraintMetadata cons : meta.getConstraints()) {
			if (cons.getMaxValue() != null && cons.getMaxValue().contains("../")){
				QueryPath crossFieldPath = QueryPath.parse(path.toString());
				String crossFieldKey = cons.getMinValue().substring(3);
				crossFieldPath.remove(crossFieldPath.size()-1);
				crossFieldPath.add(new StringKey(crossFieldKey));
				v = model.get(crossFieldPath);
			}
		}
    	
    	return v;
    }

    private void put(Map<String, Object> m, String key, Object value) {
        if (value != null) {
            m.put(key, value);
        }
    }
    
	
}
