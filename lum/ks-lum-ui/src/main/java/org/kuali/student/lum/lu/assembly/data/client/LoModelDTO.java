/**
 * 
 */
package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;

/**
 * @author jimt
 *
 */
public class LoModelDTO extends ModelDTO {

	public static final String	LO_KEY = "LOs";
	
	private static final long serialVersionUID = 1L;

	public LoModelDTO() {}
	
	public LoModelDTO(ModelDTOValue value) {
		put(LO_KEY, value);
		commit();
	}
	
}
