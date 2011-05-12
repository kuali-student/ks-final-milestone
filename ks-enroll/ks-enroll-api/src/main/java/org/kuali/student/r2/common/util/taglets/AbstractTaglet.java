
package org.kuali.student.r2.common.util.taglets;

import com.sun.tools.doclets.Taglet;


/**
 *  Defines a generic taglet.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class AbstractTaglet
    implements Taglet {

    /**
     *  Gets the name of this tag.
     *
     *  @return the tag name
     */
    @Override
    public abstract String getName();

    /**
     *  Gets the header of this tag.
     *
     *  @return the tag header
     */
    protected abstract String getHeader();

    /**
     *  Tests if tag may appear in a field.
     * 
     *  @return <code>false</code>
     */
    @Override
    public boolean inField() {
	return (false);
    }

    /**
     *  Tests if tag may appear in a constructor.
     * 
     *  @return <code>false</code>
     */
    @Override
    public boolean inConstructor() {
	return (false);
    }

    /**
     *  Tests if tag may appear in a method.
     * 
     *  @return <code>true</code>
     */
    @Override
    public boolean inMethod() {
	return (true);
    }

    /**
     *  Tests if tag may appear in an overview.
     * 
     *  @return <code>false</code>
     */
    @Override
    public boolean inOverview() {
	return (false);
    }

    /**
     *  Tests if tag may appear in a package.
     * 
     *  @return <code>false</code>
     */
    @Override
    public boolean inPackage() {
	return (false);
    }

    /**
     *  Tests if tag may appear in a type.
     * 
     *  @return <code>false</code>
     */
    @Override
    public boolean inType() {
	return (false);
    }

    /**
     *  Tests if tag is inline.
     * 
     *  @return <code>false</code>
     */
    @Override
    public boolean isInlineTag() {
	return (false);
    }

    /**
     *  Converts tag to html.
     *
     *  @param tag tag to convert
     *  @return html
     */
    @Override
    public String toString(com.sun.javadoc.Tag tag) {	
	return ("<DT><B>" + getHeader() + ":</B><DD>"
		+ "<code>" + getFirstWord(tag.text()) + "</code> - " 
		+ getRestOfWords(tag.text()) + "</DD>\n");
    }

    /**
     *  Converts array of tags to html.
     *
     *  @param tags array of tags to convert
     *  @return html
     */
    @Override
    public String toString(com.sun.javadoc.Tag[] tags) {
	if (tags.length == 0) {
	    return null;
	}

	String result = "\n<DT><B>" + getHeader() + ": </B><DD>";
	for (int i = 0; i < tags.length; i++) {
	    if (i > 0) {
		result += "</DD><DD>";
	    }
	    result += "<code>" + getFirstWord(tags[i].text()) + "</code> - " 
		+ getRestOfWords(tags[i].text()) + " ";
	}

	result = result + "</DD>\n";
	return (result);
    }

    private String getFirstWord(String text) {	
	int pos = text.indexOf(' ');
	if (pos < 0) {
	    return (text);
	}

	return (text.substring(0, pos));
    }

    private String getRestOfWords(String text) {       
	int pos = text.indexOf(' ');
	if (pos < 0) {
	    return (text);
	}

	return (text.substring(pos + 1));
    }
}
