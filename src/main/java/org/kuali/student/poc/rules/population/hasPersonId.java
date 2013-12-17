package org.kuali.student.poc.rules.population;

/**
 * Represents an entity that has a personal identification number.
 * User: mahtabme
 * Date: 12/17/13
 * Time: 7:24 AM
 */
public interface hasPersonId {

    /**
     * Obtains the personal identification number of a person entity.
     */
    public String getPersonId();

    /**
     * Ontains the government issued identification number for a person.
     */
    public String getGovernmentIssuedIdentificationNumber();
}
