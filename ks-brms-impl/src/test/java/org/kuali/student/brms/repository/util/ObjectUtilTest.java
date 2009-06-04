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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Test;
import org.kuali.student.brms.repository.util.ObjectUtil;

public class ObjectUtilTest {
    /** Object utility class */
    private ObjectUtil objectUtil = ObjectUtil.getInstance();

    @Test
    public void testDeepCopy() throws Exception {
        CompiledObject object = new CompiledObject( "ACompiledObjectTest" );
        CompiledObject copy = (CompiledObject) objectUtil.deepCopy( object );
        assertFalse( object.equals( copy ) );
        String expected = (String) object.getObject();
        String actual = (String) copy.getObject();
        assertEquals( expected, actual );
    }

    @Test
    public void testDeepCopy_NullObject() throws Exception {
        Object copy = objectUtil.deepCopy( null );
        assertNull( copy );
    }

    @Test
    public void testDeepCopy_NoObjectReference() throws Exception {
        String expected = "ACompiledObjectTest";
        CompiledObject object = new CompiledObject( expected );
        CompiledObject copy = (CompiledObject) objectUtil.deepCopy( object );
        
        assertFalse( object.equals( copy ) );

        object.setObject( "ANewString" );
        String actual = (String) copy.getObject();
        assertEquals( expected, actual );
        
        object = null;
        actual = (String) copy.getObject();
        assertEquals( expected, actual );
    }
    
    @Test
    public void testArrayCopy() throws Exception {
        byte[] b = "A String Test".getBytes();
        byte[] copy = objectUtil.arrayCopy( b );
        // Compare the content of the copy
        assertTrue( Arrays.equals(b,  copy ) );
        // Comparing whether the copy is a true copy of b
        assertFalse( b == copy );
    }

    @Test
    public void testArrayCopy_NullObject() throws Exception {
        byte[] copy = objectUtil.arrayCopy( null );
        assertNull( copy );
    }
}
