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
package org.kuali.student.common.ui.client.mvc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This interface is used to constrain the types allowed within a ModelDTO.
 * 
 * @author Kuali Student Team
 *
 */
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


	public static class StringType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private String value = null;
		
		public StringType() {
			
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
	}
	
	public static class CharacterType implements ModelDTOValue {
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
	}

	public static class IntegerType implements ModelDTOValue {
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
	}

	public static class LongType implements ModelDTOValue {
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
	}

	public static class FloatType implements ModelDTOValue {
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
	}

	public static class DoubleType implements ModelDTOValue {
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
	}

	public static class ByteType implements ModelDTOValue {
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
	}

	public static class BooleanType implements ModelDTOValue {
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
	}

	public static class DateType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private Date value = null;
	    
		public DateType() {
			
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
		public void set(List<ModelDTOValue> value) {
			this.value = value;
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
	}

	public static class ModelDTOType implements ModelDTOValue {
		private static final long serialVersionUID = 1L;
		private ModelDTO value = null;
	    
		public ModelDTOType() {
			
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
	}


}
