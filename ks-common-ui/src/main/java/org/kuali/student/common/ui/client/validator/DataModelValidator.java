package org.kuali.student.common.ui.client.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.kuali.student.common.assembly.client.ConstraintMetadata;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.MetadataInterrogator;
import org.kuali.student.common.assembly.client.ModelDefinition;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.assembly.client.Data.DataType;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.common.validator.DateParser;
import org.kuali.student.common.validator.ValidatorUtils;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import static org.kuali.student.common.assembly.client.MetadataInterrogator.*;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.*;

public class DataModelValidator {
	private static final String UNBOUNDED_CHECK = null;

	private Map<String, String> messages = new HashMap<String, String>();

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

	public void addMessages(List<Message> list) {
		for (Message m : list) {
			messages.put(m.getId(), m.getValue());
		}
	}
	private String getMessage(String id){
	    if(messages.get(id) == null){
	        return id;
	    }
	    return messages.get(id);
	}


	public List<ValidationResultContainer> validate(final DataModel model) {
		List<ValidationResultContainer> results = new ArrayList<ValidationResultContainer>();
		
		DataModelDefinition def = (DataModelDefinition) model.getDefinition();
		doValidate(model, def.getMetadata(), new QueryPath(), results);
		
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
			
			results.add(v);
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
			
			results.add(v);
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
			
			results.add(v);
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
			
			results.add(v);
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
			
			results.add(v);
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
				
				
				Date min = getLargestMinValueDate(meta, dateParser);
				Date max = getSmallestMaxValueDate(meta, dateParser);
				
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
			
			results.add(v);
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
			
			results.add(v);
		}
	}

	private void doValidateComplex(final DataModel model, final Metadata meta, final QueryPath path, List<ValidationResultContainer> results) {
		Map<QueryPath, Object> values = model.query(path);
		ValidationResultContainer v = new ValidationResultContainer(path.toString());
		if (values.isEmpty() && isRequired(meta)) {
			v.addError(REQUIRED.getKey());
		} else {
			// do min/max occurs checks
			Integer min = getLargestMinOccurs(meta);
			if (min != null && values.size() < min) {
				v.addError(MIN_OCCURS.getKey());
			}
			Integer max = getSmallestMaxOccurs(meta);
			if (max != null && values.size() > max) {
				v.addError(MAX_OCCURS.getKey());
			}
		}
		
		results.add(v);
		
		// validate children
		String basePath = path.toString();
		if (meta.getProperties() != null) {
			for (Map.Entry<String, Metadata> e : meta.getProperties().entrySet()) {
				QueryPath childPath = QueryPath.concat(basePath, e.getKey());
				doValidate(model, e.getValue(), childPath, results);
			}
		}
	
	}

	private static boolean validateOccurs(QueryPath path, Map<QueryPath, Object> values, Metadata meta, List<ValidationResultContainer> results) {
	    Integer min = getLargestMinOccurs(meta);
	    boolean minValid = min == null || min <= values.size();
	 
	    Integer max = getSmallestMaxOccurs(meta);
        boolean maxValid = max == null || max >= values.size();
	    
        
        if (!minValid || !maxValid) {
            ValidationResultContainer v = new ValidationResultContainer(path.toString());
            if (!minValid && !maxValid) {
                v.addError(OCCURS.getKey());
            } else if (!minValid) {
                v.addError(MIN_OCCURS.getKey());
            } else {
                v.addError(MAX_OCCURS.getKey());
            }
            results.add(v);
        }
        
	    return minValid && maxValid;
	}
	
	
    
	
}
