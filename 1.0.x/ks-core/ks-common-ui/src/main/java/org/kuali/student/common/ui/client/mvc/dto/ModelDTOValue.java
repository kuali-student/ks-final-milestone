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

package org.kuali.student.common.ui.client.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This interface is used to constrain the types allowed within a ModelDTO.
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public interface ModelDTOValue extends Serializable {
	
	/**
	 * Enumeration of ModelDTOValue types
	 * @author Kuali Student Team
	 *
	 */
	public enum Type {
		STRING,
		CHARACTER,
		INTEGER,
		LONG,
		FLOAT,
		DOUBLE,
		BYTE,
		BOOLEAN,
		DATE,
		LIST,
		MAP,
		MODELDTO
	}
	

	/**
	 * 
	 * @return the ModelDTOValue.Type associated with this value
	 */
	public Type getType();
	
	public interface StringSupportedValue extends ModelDTOValue{
	    public String getString();
	    public void setString(String value);
	}


	public static class StringType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private String value = null;
		
		public StringType() {			
		}
		
		public StringType(String value){
		    this.set(value);
		}
		
		@Override
		public Type getType() {
			return Type.STRING;
		}
		
		public String get() {
			return this.value;
		}
		public void set(String value) {
			this.value = value;
		}

        @Override
        public String getString() {
            return this.value;
        }

        @Override
        public void setString(String value) {
            this.value = value;
        }
        
        public String toString(){
            return getString();
        }
	}
	
	public static class CharacterType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Character value = null;
	    
		public CharacterType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.CHARACTER;
	    }
	    
	    public Character get() {
	        return this.value;
	    }
	    
	    public void set(Character value) {
	        this.value = value;
	    }

        @Override
        public String getString() {
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
	            value = value.trim();
	            if(value.length() == 1){
	                this.value = value.charAt(0);
	            }
	            else{
	                throw new UnsupportedOperationException("Characters can only be set with Strings containing 1 character");
	            }
        	}
        }
        
        public String toString(){
            return getString();
        }
	}

	public static class IntegerType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Integer value = null;
	    
		public IntegerType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.INTEGER;
	    }
	    
	    public Integer get() {
	        return this.value;
	    }
	    
	    public void set(Integer value) {
	        this.value = value;
	    }

        @Override
        public String getString() {
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
		        value = value.trim();
	            this.value = Integer.parseInt(value);
        	}
        }
        
        public String toString(){
            return getString();
        }        
	}

	public static class LongType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Long value = null;
	    
		public LongType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.LONG;
	    }
	    
	    public Long get() {
	        return this.value;
	    }
	    
	    public void set(Long value) {
	        this.value = value;
	    }

        @Override
        public String getString() {
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
		        value = value.trim();
	            this.value = Long.parseLong(value);
        	}
        }
        
        public String toString(){
            return getString();
        }        
	}

	public static class FloatType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Float value = null;
	    
		public FloatType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.FLOAT;
	    }
	    
	    public Float get() {
	        return this.value;
	    }
	    
	    public void set(Float value) {
	        this.value = value;
	    }

        @Override
        public String getString() { 
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
		        value = value.trim();
	            this.value = Float.parseFloat(value);
        	}
        }
        
        public String toString(){
            return getString();
        }        
	}

	public static class DoubleType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Double value = null;
	    
		public DoubleType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.DOUBLE;
	    }
	    
	    public Double get() {
	        return this.value;
	    }
	    
	    public void set(Double value) {
	        this.value = value;
	    }

        @Override
        public String getString() {
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
		        value = value.trim();
	            this.value = Double.parseDouble(value);
        	}
        }
        
        public String toString(){
            return getString();
        }        
	}

	public static class ByteType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Byte value = null;
	    
		public ByteType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.BYTE;
	    }
	    
	    public Byte get() {
	        return this.value;
	    }
	    
	    public void set(Byte value) {
	        this.value = value;
	    }

        @Override
        public String getString() {
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
		        value = value.trim();
		        this.value = Byte.parseByte(value);
        	}
        }
        
        public String toString(){
            return getString();
        }        
	}

	public static class BooleanType implements StringSupportedValue {
		private static final long serialVersionUID = 1L;
		private Boolean value = null;
	    
		public BooleanType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.BOOLEAN;
	    }
	    
	    public Boolean get() {
	        return this.value;
	    }
	    
	    public void set(Boolean value) {
	        this.value = value;
	    }

        @Override
        public String getString() {
            return value == null ? null : this.value.toString();
        }

        @Override
        public void setString(String value) {
        	if (value == null) {
        		this.value = null;
        	} else {
		        value = value.trim();
	            if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
	                this.value = Boolean.parseBoolean(value);
	            }
	            else{
	                throw new UnsupportedOperationException("Booleans can only be set with true or false");
	            }
        	}
        }
        
        public String toString(){
            return getString();
        }        
	}

	public static class DateType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private Date value = null;
		//private DateParser dateParser;
	    
		public DateType() {
			//dateParser = DateParserFactory.getDateParser();
		}
		
	    @Override
	    public Type getType() {
	        return Type.DATE;
	    }
	    
	    public Date get() {
	        return this.value;
	    }
	    
	    public void set(Date value) {
	        this.value = value;
	    }
        
        public String toString(){
            return "";//getString();
        }        
	}


	public static class ListType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private List<ModelDTOValue> value = null;
		
		public ListType() {
			
		}
		
		@Override
		public Type getType() {
			return Type.LIST;
		}
		
		public List<ModelDTOValue> get() {
			return this.value;
		}
        @SuppressWarnings("unchecked") // TODO - remove this and make sure callers can handle parameterized List returntype
		public List getBaseTypeList() {
            List list = new ArrayList();
            for (ModelDTOValue modelDTOValue : value) {

                switch (modelDTOValue.getType()) {
                    case STRING:
                        list.add(((StringType) modelDTOValue).get());
                        break;
                    case CHARACTER:
                        list.add(((CharacterType) modelDTOValue).get());
                        break;
                    case INTEGER:
                        list.add(((IntegerType) modelDTOValue).get());
                        break;
                    case LONG:
                        list.add(((LongType) modelDTOValue).get());
                        break;
                    case FLOAT:
                        list.add(((FloatType) modelDTOValue).get());
                        break;
                    case DOUBLE:
                        list.add(((DoubleType) modelDTOValue).get());
                        break;
                    case BYTE:
                        list.add(((ByteType) modelDTOValue).get());
                        break;
                    case BOOLEAN:
                        list.add(((BooleanType) modelDTOValue).get());
                        break;
                    case DATE:
                        list.add(((DateType) modelDTOValue).get());
                        break;
                    // case LIST:
                        // return ((ListType) modelDTOValue).get();
                        // break;
                    // case MAP:
                        // return ((MapType) modelDTOValue).get();
                        // break;
                    case MODELDTO:
                        list.add(((ModelDTOType) modelDTOValue).get());
                        break;
                    default:
                    	// TODOxception?
                }

            }

            return list;
        }
		public void set(List<ModelDTOValue> value) {
			this.value = value;
		}
		
		public String toString(){
		    return value == null ? null : value.toString();
		}
	}
	
	public static class MapType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private Map<String, ModelDTOValue> value = null;
	    
		public MapType() {
			
		}
		
	    @Override
	    public Type getType() {
	        return Type.MAP;
	    }
	    
	    public Map<String, ModelDTOValue> get() {
	        return this.value;
	    }
	    
	    public void set(Map<String, ModelDTOValue> value) {
	        this.value = value;
	    }
	    
	    public String toString(){
	        return value == null ? null : value.toString();
	    }
	}

	public static class ModelDTOType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private ModelDTO value = null;
	    
		public ModelDTOType() {			
		}
		
		public ModelDTOType(ModelDTO value){
		    this.set(value);
		}
		
	    @Override
	    public Type getType() {
	        return Type.MODELDTO;
	    }
	    
	    public ModelDTO get() {
	        return this.value;
	    }
	    
	    public void set(ModelDTO value) {
	        this.value = value;
	    }
	    
	    public String toString(){
	        return value == null ? null : value.toString();
	    }
	}


}
