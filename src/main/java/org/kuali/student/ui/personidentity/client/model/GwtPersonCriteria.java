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
    /**
     * This overridden method ...
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean bRet = false;
        if(obj instanceof GwtPersonCriteria){
            GwtPersonCriteria crit = (GwtPersonCriteria)obj;
            if(String.CASE_INSENSITIVE_ORDER.compare(this.firstName, crit.getFirstName()) == 0 &&
                    String.CASE_INSENSITIVE_ORDER.compare(this.lastName, crit.getLastName()) == 0)
                bRet = true;                        
        }else{
            bRet = super.equals(obj);
        }
            
        return bRet;
    }


}
