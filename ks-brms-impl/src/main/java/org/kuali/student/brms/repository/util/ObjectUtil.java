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
package org.kuali.student.brms.repository.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * This is an object utility class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class ObjectUtil implements java.io.Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Private constructor.
     */
    private ObjectUtil() {
    }

    /**
     * Returns a new instance of <code>ObjectUtil</code>
     * 
     * @return A new instance of <code>ObjectUtil</code>
     */
    public static ObjectUtil getInstance() {
        return new ObjectUtil();
    }
    
    /**
     * Creates a deep copy of an object by serialization.
     * 
     * @param object object to do a deep copy of
     * @return A copy of <code>object</code>
     * @throws Exception
     */
    public Object deepCopy( final Object object ) throws Exception {
        if ( object == null ) {
            return null;
        }
        
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // Serialize the object
            oos.writeObject( object );
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // Return the new object
            return ois.readObject();
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }
    
    /**
     * Makes a copy of a <code>binaryContent</code> byte array.
     * 
     * @param binaryContent Binary content to be copied
     * @return A copy of <code>binaryContent</code>
     */
    public byte[] arrayCopy( final byte[] binaryContent ) {
        if ( binaryContent == null ) {
            return null;
        }
        int size = binaryContent.length;
        byte[] copy = new byte[size];
        System.arraycopy(binaryContent, 0, copy , 0, size);
        return copy;
    }

	/**
	 * Serializes a byte array into an object.
	 * 
	 * @param bytes Bytes to serialize
	 * @return An object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object serialize(final byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInput in = null;
		ByteArrayInputStream bis = null;
		Object obj = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			in = new ObjectInputStream(bis);
	        obj = in.readObject();
		} finally {
	        if (in!= null) in.close();
	        if (bis!= null) bis.close();
		}
        return obj;
    }

	/**
	 * Deserializes an object.
	 * 
	 * @param obj Object deserialize
	 * @return A byte array
	 * @throws IOException
	 */
    public static byte[] deserialize(final Object obj) throws IOException {
    	ByteArrayOutputStream bos = null;
    	ObjectOutput out = null;
    	try {
    		// Serialize to a byte array
	        bos = new ByteArrayOutputStream();
	        out = new ObjectOutputStream(bos);
	        out.writeObject(obj);
    	} finally {
    		if (bos != null) bos.close();
    		if (out != null) out.close();
    	}
        // Get the bytes of the serialized object
        final byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
