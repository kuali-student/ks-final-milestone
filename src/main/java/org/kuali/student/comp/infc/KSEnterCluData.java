/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import org.kuali.student.service.dto.CluField;
import org.kuali.student.comp.*;
import java.util.List;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public interface KSEnterCluData
{

 public void setCluInfo (CluInfo initialClu);

 public void setFields (List<CluField> fields);

 public interface Callback
 {

  public void onDone (CluInfo updatedClu);

  public void onError (Exception ex);

 }

 public void enterData (Callback callback);

 public void cancel ();

}
