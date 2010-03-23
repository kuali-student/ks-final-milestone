/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.server.mvc.dto;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.BooleanType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ByteType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.CharacterType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DateType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DoubleType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.FloatType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.IntegerType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.LongType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.MapType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;

/**
 * Default implementation of the BeanMapper interface, performs mapping via reflection. 
 * 
 * @author Kuali Student Team
 *
 */
public class DefaultBeanMapper implements BeanMapper {
    private final PropertyMapping defaultPropertyMappingInstance = new DefaultPropertyMapping();
    private final Map<String, PropertyMapping> mappings = new HashMap<String, PropertyMapping>();
    
    /**
     * 
     * @see org.kuali.student.common.ui.server.mvc.dto.BeanMapper#addPropertyMapping(java.lang.String, org.kuali.student.common.ui.server.mvc.dto.PropertyMapping)
     */
    @Override
    public void addPropertyMapping(String key, PropertyMapping mapping) {
        mappings.put(key, mapping);
    }

    protected PropertyMapping getPropertyMapping(String key) {
        return getPropertyMapping(key, null);
    }
    protected PropertyMapping getPropertyMapping(String key, PropertyMapping defaultMapping) {
        PropertyMapping result = mappings.get(key);
        if (result == null) {
            result = defaultMapping;
        }
        return result;
    }
    
    /**
     * 
     * @see org.kuali.student.common.ui.server.mvc.dto.BeanMapper#fromBean(java.lang.Object, org.kuali.student.common.ui.server.mvc.dto.MapContext)
     */
    @Override
    public ModelDTO fromBean(Object value, MapContext context) throws BeanMappingException {
        ModelDTO result = null;
        
        if (value != null) {
            try {
                result = new ModelDTO(value.getClass().getName());
                BeanInfo info = Introspector.getBeanInfo(value.getClass());
                PropertyDescriptor[] properties = info.getPropertyDescriptors();
                
                for (PropertyDescriptor pd : properties) {
                    String propKey = pd.getName();
                    Object propValue = pd.getReadMethod().invoke(value, (Object[]) null);
                    
                    PropertyMapping pm = getPropertyMapping(propKey, defaultPropertyMappingInstance);
                    ModelDTOValue mdv = pm.toModelDTOValue(propValue, context);
                    result.put(propKey, mdv, true);
                }
                
            } catch (Exception e) {
                if (e instanceof BeanMappingException) {
                    throw (BeanMappingException) e;
                } else {
                    throw new BeanMappingException("Unable to map " + value.getClass().getName() + " to ModelDTO", e);
                }
            }
        }
        
        return result;
    }

    /**
     * 
     * @see org.kuali.student.common.ui.server.mvc.dto.BeanMapper#fromModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTO, org.kuali.student.common.ui.server.mvc.dto.MapContext)
     */
    @Override
    public Object fromModelDTO(ModelDTO value, MapContext context) throws BeanMappingException {
        Object result = null;
        
        if (value != null) {
            try {
                result = Class.forName(value.getClassName()).newInstance();
                
                BeanInfo info = Introspector.getBeanInfo(result.getClass());
                PropertyDescriptor[] properties = info.getPropertyDescriptors();
                
                for (PropertyDescriptor pd : properties) {
                    String propKey = pd.getName();
                    try {
                        if (value.keySet().contains(propKey)) {
                            PropertyMapping pm = getPropertyMapping(propKey, defaultPropertyMappingInstance);
                            Object propValue = pm.fromModelDTOValue(value.get(propKey), context);
                            if(pd.getWriteMethod() != null & propValue != null){    
                                    pd.getWriteMethod().invoke(result, new Object[] {propValue});
                            }
                        }
                    } catch (Exception e){
                        throw new BeanMappingException("Unable to map field " + propKey, e);
                    }
                }
            } catch (Exception e) {
                if (e instanceof BeanMappingException) {
                    throw (BeanMappingException) e;
                } else {
                    throw new BeanMappingException("Unable to map ModelDTO to " + value.getClassName(), e);
                }
            }
        }
        
        return result;
    }

    
    /**
     * Default property mapping supports basic Java types (String, Integer, etc), handles any complex types as nested ModelDTOs 
     * 
     * @author Kuali Student Team
     *
     */
    public static class DefaultPropertyMapping implements PropertyMapping {

        /**
         * 
         * @see org.kuali.student.common.ui.server.mvc.dto.PropertyMapping#fromModelDTOValue(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue, org.kuali.student.common.ui.server.mvc.dto.MapContext)
         */
        @Override
        public Object fromModelDTOValue(ModelDTOValue value, MapContext context) throws BeanMappingException {
            Object result = null;
            
            if (value != null) {
                switch (value.getType()) {
                    case STRING:
                        result = ((StringType)value).get();
                        break;

                    case CHARACTER:
                        result = ((CharacterType)value).get();
                        break;

                    case INTEGER:
                        result = ((IntegerType)value).get();
                        break;

                    case LONG:
                        result = ((LongType)value).get();
                        break;

                    case FLOAT:
                        result = ((FloatType)value).get();
                        break;

                    case DOUBLE:
                        result = ((DoubleType)value).get();
                        break;

                    case BYTE:
                        result = ((ByteType)value).get();
                        break;

                    case BOOLEAN:
                        result = ((BooleanType)value).get();
                        break;

                    case DATE:
                        result = ((DateType)value).get();
                        break;

                    case LIST:
                        result = convertListFromDTO(((ListType)value).get(), context);
                        break;

                    case MAP:
                        result = convertMapFromDTO(((MapType)value).get(), context);
                        break;

                    case MODELDTO:
                        result = context.fromModelDTO(((ModelDTOType)value).get());
                        break;
                }
            }
            
            return result;
        }

        /**
         * 
         * @see org.kuali.student.common.ui.server.mvc.dto.PropertyMapping#toModelDTOValue(java.lang.Object, org.kuali.student.common.ui.server.mvc.dto.MapContext)
         */
        @Override
        public ModelDTOValue toModelDTOValue(Object source, MapContext context) throws BeanMappingException {
            ModelDTOValue result = null;
            if (source != null) {
                if (source instanceof String) {
                    result = new StringType();
                    ((StringType)result).set((String) source);
                } else if (source instanceof Character) {
                    result = new CharacterType();
                    ((CharacterType)result).set((Character) source);
                } else if  (source instanceof Integer) {
                    result = new IntegerType();
                    ((IntegerType)result).set((Integer) source);
                } else if  (source instanceof Long) {
                    result = new LongType();
                    ((LongType)result).set((Long) source);
                } else if  (source instanceof Float) {
                    result = new FloatType();
                    ((FloatType)result).set((Float) source);
                } else if  (source instanceof Double) {
                    result = new DoubleType();
                    ((DoubleType)result).set((Double) source);
                } else if  (source instanceof Byte) {
                    result = new ByteType();
                    ((ByteType)result).set((Byte) source);
                } else if  (source instanceof Boolean) {
                    result = new BooleanType();
                    ((BooleanType)result).set((Boolean) source);
                } else if  (source instanceof Date) {
                    result = new DateType();
                    ((DateType)result).set((Date) source);
                } else if  (source instanceof List) {
                    result = convertDTOFromList(source, context);
                } else if  (source instanceof HashMap) {
                    result = convertDTOFromMap(source, context);
                }else if(source.getClass().isArray()){
                }else if(source.getClass().isAnnotation()){
                }else if(source instanceof Class){
                } else {
                    result = new ModelDTOType();
                    ((ModelDTOType)result).set(context.fromBean(source));
                }
            }
            return result;
        }

   
        @SuppressWarnings("unchecked")
        private List convertListFromDTO(List<ModelDTOValue> list, MapContext context) throws BeanMappingException {
            List result = null;
            
            if (list != null) {
                result = new ArrayList();
                for (ModelDTOValue mdv : list) {
                    result.add(fromModelDTOValue(mdv, context));
                }
            }
            
            return result;
        }

        @SuppressWarnings("unchecked")
        private Map convertMapFromDTO(Map<String, ModelDTOValue> map, MapContext context) throws BeanMappingException {
            Map result = null;
            if (map != null) {
                result = new HashMap();
                for (String key : map.keySet()) {
                    result.put(key, fromModelDTOValue(map.get(key), context));
                }
            }
            return result;
        }
        
        @SuppressWarnings("unchecked")
        private ListType convertDTOFromList(Object value, MapContext context) throws BeanMappingException {
            ListType result = null;
            
            if (value != null) {
                result = new ListType();
                List<ModelDTOValue> list = new ArrayList<ModelDTOValue>();
                for (Object o : ((List)value)) {
                    list.add(toModelDTOValue(o, context));
                }
                result.set(list);
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        private MapType convertDTOFromMap(Object value, MapContext context) throws BeanMappingException {
            MapType result = null;
            if (value != null) {
                Map inputMap = (Map) value;
                result = new MapType();
                Map<String, ModelDTOValue> map = new HashMap<String, ModelDTOValue>();
                for (Object key : inputMap.keySet()) {
                    map.put((String) key, toModelDTOValue(inputMap.get(key), context));
                }
                result.set(map);
            }
            return result;
        }
    }
}
