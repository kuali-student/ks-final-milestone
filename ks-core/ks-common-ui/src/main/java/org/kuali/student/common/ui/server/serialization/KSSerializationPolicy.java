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

package org.kuali.student.common.ui.server.serialization;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.impl.LegacySerializationPolicy;
import com.google.gwt.user.server.rpc.impl.StandardSerializationPolicy;

/**
 * Wrap the StandardSerializationPolicy and LegacySerializationPolicy to create
 * customized SerializationPolicy
 * 
 * @author Joe Yin
 */
public class KSSerializationPolicy extends StandardSerializationPolicy {
    LegacySerializationPolicy legacySerializationPolicy = LegacySerializationPolicy.getInstance();
    
    public KSSerializationPolicy(Map<Class<?>, Boolean> whitelist) {
        super(whitelist, whitelist, new HashMap<Class<?>, String>());
    }

    /**
     * Check both StandardSerializationPolicy and LegacySerializationPolicy
     */
    @Override
    public boolean shouldDeserializeFields(Class<?> clazz) {
      return super.shouldDeserializeFields(clazz)||legacySerializationPolicy.shouldDeserializeFields(clazz);
    }

    /**
     * Check both StandardSerializationPolicy and LegacySerializationPolicy
     * 
     */
    @Override
    public boolean shouldSerializeFields(Class<?> clazz) {
        return super.shouldSerializeFields(clazz)||legacySerializationPolicy.shouldSerializeFields(clazz);
    }

    /**
     * Validates that the specified class should be deserialized from a stream.
     * Check both StandardSerializationPolicy and LegacySerializationPolicy
     * 
     * @param clazz the class to validate
     * @throws SerializationException if the class is not allowed to be
     *           deserialized
     */
    @Override
    public void validateDeserialize(Class<?> clazz)
        throws SerializationException{
        boolean throwedFromStandardSerializationPolicy = false; 
        boolean throwedFromLegacySerializationPolicy = false;
        try{
            super.validateDeserialize(clazz);
        }catch(SerializationException e){
            throwedFromStandardSerializationPolicy = true;
        }
        try{
            legacySerializationPolicy.validateDeserialize(clazz);
        }catch(SerializationException e){
            throwedFromLegacySerializationPolicy = true;
        }

        if(throwedFromStandardSerializationPolicy && 
                throwedFromLegacySerializationPolicy){
            throw new SerializationException(
                    "Type '"
                        + clazz.getName()
                        + "' was not included in the set of types which can be deserialized by this SerializationPolicy or its Class object could not be loaded. For security purposes, this type will not be deserialized."); 
        }
    }

    /**
     * Validates that the specified class should be serialized into a stream.
     * Check both StandardSerializationPolicy and LegacySerializationPolicy
     * 
     * @param clazz the class to validate
     * @throws SerializationException if the class is not allowed to be serialized
     */
    @Override
    public void validateSerialize(Class<?> clazz)
        throws SerializationException{
        boolean throwedFromStandardSerializationPolicy = false; 
        boolean throwedFromLegacySerializationPolicy = false;
        try{
            super.validateSerialize(clazz);
        }catch(SerializationException e){
            throwedFromStandardSerializationPolicy = true;
        }
        try{
            legacySerializationPolicy.validateSerialize(clazz);
        }catch(SerializationException e){
            throwedFromLegacySerializationPolicy = true;
        }

        if(throwedFromStandardSerializationPolicy && 
                throwedFromLegacySerializationPolicy){
            throw new SerializationException(
                    "Type '"
                        + clazz.getName()
                        + "' was not included in the set of types which can be serialized by this SerializationPolicy or its Class object could not be loaded. For security purposes, this type will not be serialized.");
        }
    }
}
