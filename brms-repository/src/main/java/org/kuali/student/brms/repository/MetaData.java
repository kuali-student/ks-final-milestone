package org.kuali.student.brms.repository;

import java.util.Calendar;

public interface MetaData 
{
    /**
     * Returns the rule name.
     * 
     * @return Rule name
     */
	public String getName();
    
    /**
     * Returns the rule description.
     * 
     * @return Rule description
     */
    public String getDescription();
    
    /**
     * Returns the rule status. E.g. Active, Inactive, Archived etc.
     * 
     * @return Rule status
     */
    public String getStatus();
    
    /**
     * Returns rule format. E.g. for Drools, FUNCTION, DSL, DECISION_SPREADSHEET_XLS, ENUMERATION
     * 
     * @return Rule format
     */
    public String getFormat();
    
    /**
     * Gets the asset/rule creator.
     * 
     * @return Creator
     */
    public String getCreator();
    
    /**
     * Gets the asset/rule subject.
     * 
     * @return Subject
     */
    public String getSubject();
    
    /**
     * Get the asset/rule pulisher.
     * 
     * @return Publisher
     */
    public String getPublisher();   

    /**
     * Returns the checkin comments.
     * 
     * @return Checkin comments
     */
    public String getCheckinComment();

    /**
     * Returns the rule effective date.
     * 
     * @return Effective date
     */
    public Calendar getEffectiveDate();
    
    /**
     * Returns the rule expiry date.
     * 
     * @return Expiry date
     */
    public Calendar getExpiredDate();
    
    /**
     * Returns the last date the rule was modified.
     * 
     * @return Last modified date
     */
    public Calendar getLastModificationDate();

    /**
     * Returns the version number.
     * 
     * @return Version number
     */
    public long getVersionNumber();

    /**
     * Gets all categories for the rule.
     * 
     * @return Array of categories
     */
    public String[] getCategories();
    
    /**
     * Removes a category.
     * @param idx The index of the cat to remove.
     */
    public void removeCategory( int index );

    /**
     * Adds a category to the end of the category list.
     */
    public void addCategory( String category );
}
