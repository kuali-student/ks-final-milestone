
package org.kuali.student.r2.common.util.taglets;

import java.util.Map;
import com.sun.tools.doclets.Taglet;


/**
 *  Defines the javadoc @notes taglet.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class ImplTaglet
    extends AbstractTaglet {

    private static final String name   = "impl";
    private static final String header = "Impl Notes";

    /**
     *  Gets the name of this tag.
     * 
     *  @return the tag name
     */
    @Override
    public String getName() {
	return (this.name);
    }

    /**
     *  Gets the header of this tag.
     * 
     *  @return the tag header
     */    
    @Override
    protected String getHeader() {
	return (this.header);
    }

    /**
     *  Registers a tag.
     * 
     *  @param tagletMap
     */
    public static void register(Map<String, Taglet> tagletMap) {
	Taglet tag = new ImplTaglet();
	tagletMap.put(tag.getName(), tag);
	return;
    }
}
