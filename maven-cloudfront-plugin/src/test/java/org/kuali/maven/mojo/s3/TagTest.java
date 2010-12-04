/**
 * 
 */
package org.kuali.maven.mojo.s3;

import junit.framework.TestCase;

/**
 * @author jcaddel2
 * 
 */
public class TagTest extends TestCase {

    public void testTheTag() {
        String id = "abc";
        Tag tag = new Tag();
        tag.setId(id);
        assertTrue(tag.getId().equals(id));
    }
}
