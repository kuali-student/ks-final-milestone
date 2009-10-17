/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import org.kuali.student.service.dto.CluField;
import org.kuali.student.service.dto.LuStateKey;
import org.kuali.student.service.dto.LuTypeKey;
import java.util.List;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public interface KSCreateCluFromBlank
{

 public void setLuService (LuService luService);

 public void setLuTypeKey (LuTypeKey type);

 public void setLuStateKey (LuStateKey type);

 public void setOtherRequiredFields (List<CluField> fields);

 public CluInfo createClu ();

}
