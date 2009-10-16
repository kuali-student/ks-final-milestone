/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import org.kuali.student.service.dto.ProposalTypeKey;
import java.util.List;
import org.kuali.student.core.exceptions.OperationFailedException;

/**
 *
 * @author nwright
 */
public interface KSSelectProposalType
{

 public void setOptions (List<ProposalTypeKey> options);

 public abstract class Callback <T>
 {
  private T caller;
  public Callback (T caller)
  {
   this.caller = caller;
  }

  public abstract void onDone (ProposalTypeKey selection);

  public abstract void onCancel ();

  public abstract void onExit ();

  public abstract void onError (Exception ex);
 }

 public void execute (Callback callback);

 public void cancel ();

}
