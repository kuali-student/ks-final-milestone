package org.kuali.student.poc.personidentity.person.service.impl;

import org.kuali.student.poc.personidentity.person.dto.PersonDisplayDTO;

/**
 * Convenience class for creating a PersonDisplayDTO object
 *
 * @author lindholm
 *
 */
public class PersonDisplayDTODAO extends PersonDisplayDTO {
	private static final long serialVersionUID = 2673044810439545096L;

	public PersonDisplayDTODAO(final String personId, final String firstName, final String surname, final String lastName) {
		super();

		setPersonId(personId);

		final StringBuilder name = new StringBuilder();
		if (firstName != null && firstName.trim().length() > 0) {
			name.append(firstName.trim());
		}
		appendString(surname, name);
		appendString(lastName, name);
		setName(name.toString());
	}

	/**
	 * @param text
	 * @param name
	 */
	private static void appendString(final String text, final StringBuilder name) {
		if (text != null && text.trim().length() > 0) {
			if (name.length() > 0) {
				name.append(" ");
			}
			name.append(text.trim());
		}
	}

}
