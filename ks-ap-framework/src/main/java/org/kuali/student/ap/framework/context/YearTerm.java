package org.kuali.student.ap.framework.context;

public interface YearTerm {

    /**
     *
     * @return the stored year in int format
     */
    public int getYear();

    /**
     *
     * @return the stored year in string format
     */
    public String getYearAsString();

    /**
     *
     * @return the stored term index in int format
     */
    public int getTerm();

    /**
     *
     * @return the stored term index in string format
     */
    public String getTermAsString();

    /**
     * Converts the stored year and term index to the corresponding atp id
     *
     * @return the atp id created from the year and term index
     */
    public String toATP();

    /**
     * Creates the name of the term using stored the term index and year
     *
     * @return the name created from the year and term index
     */
    public String toTermName();

    /**
     *
     * @return the stored list of possible term names
     */
    public String[] getTermNameList();


    /**
     * Creates an abbrivated name from the stored term index and year
     *
     * @return the abbr. name created from the stored term index and year
     */
    public String toQTRYRParam() ;

    /**
     * Compares this yearterm with another
     *
     * @param that - YearTerm to compare against
     * @return an integer repersentation of less than, greater than and equal to
     */
    public int compareTo(YearTerm that);

    /**
     *
     * @return the highest term index repersentation
     */
    public int getMAX_TERM_INDEX();
}
