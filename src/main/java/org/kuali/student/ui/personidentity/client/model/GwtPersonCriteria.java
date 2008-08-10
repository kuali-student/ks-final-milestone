/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model;

import java.io.Serializable;

/**
 * @author Garey
 *
 */
public class GwtPersonCriteria implements Serializable{




	/**
	 * 
	 */
	private static final long serialVersionUID = 208658732910401998L;

	private String firstName = "%";

	private String lastName = "%";
    
    public GwtPersonCriteria() {
        
    }
    public GwtPersonCriteria(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
