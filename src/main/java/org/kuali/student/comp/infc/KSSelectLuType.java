/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import org.kuali.student.service.dto.LuTypeKey;
import java.util.List;

/**
 *
 * @author nwright
 */
public interface KSSelectLuType
{

 public void setOptions (List<LuTypeKey> options);

 public interface Callback
 {

  public void onDone (LuTypeKey selection);

  public void onError (Exception ex);

 }

 public void execute (Callback callback);

 public void cancel ();

}
