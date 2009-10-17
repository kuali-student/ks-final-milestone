/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.impl.ContextHelper;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSSelectProposalType;
import org.kuali.student.service.dto.ProposalTypeKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.service.ProposalService;

/**
 *
 * @author nwright
 */
public class SwingSelectProposalType implements KSSelectProposalType,
                                                   Runnable
{

 private KSContext context;

 public SwingSelectProposalType (KSContext context)
 {
  this.context = context;
 }

 private List<ProposalTypeKey> options;

 @Override
 public void setOptions (List<ProposalTypeKey> options)
 {
  this.options = options;
 }

 @Override
 public void cancel ()
 {
  callback.onCancel ();
 }

 private KSSelectProposalType.Callback callback;
 private boolean cancelled = false;

 @Override
 public void execute (KSSelectProposalType.Callback callback)
 {
  this.callback = callback;
  this.run ();
 }

 @Override
 public void run ()
 {
  try
  {
   ProposalService propServ = new ContextHelper (context).getProposalService ();
   while ( ! cancelled)
   {
    int selMax = 0;
    System.out.println ("Proposal types:");
    for (ProposalTypeKey key : options)
    {

     ProposalTypeInfo info = propServ.getProposalType (key.getKey ());
     selMax ++;
     System.out.println (selMax + ": " + info.getName ());
    }
    System.out.println ("Choose #, 0=exit:");
    BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
    try
    {
     String line = br.readLine ();
     try
     {
      int selection = Integer.parseInt (line);
      if (selection == 0)
      {
       callback.onExit ();
       return;
      }
      if (selection < 0)
      {
       System.out.println ("Negative selections are not allowed");
       continue;
      }
      if (selection > selMax)
      {
       System.out.println ("Please enter a number less than or equal to " +
        selMax);
       continue;
      }
      callback.onDone (options.get (selection));
      return;
     }
     catch (NumberFormatException ex)
     {
      System.out.println ("Please enter a valid number.");
     }
    }
    catch (IOException ex)
    {
     callback.onError (ex);
     return;
    }
   }
  }
  catch (Exception ex)
  {
   callback.onError (ex);
   return;
  }
 }
}
