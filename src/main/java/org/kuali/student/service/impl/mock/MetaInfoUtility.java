/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.impl.mock;

import org.kuali.student.comp.impl.ContextHelper;
import org.kuali.student.comp.infc.KSContext;
import java.util.Date;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.exceptions.OperationFailedException;

/**
 *
 * @author nwright
 */
public class MetaInfoUtility
{

 private KSContext context;

 public MetaInfoUtility (KSContext context)
 {
  this.context = context;
 }

 public MetaInfo getForCreate ()
  throws OperationFailedException
 {
  MetaInfo info = new MetaInfo ();
  String user = new ContextHelper (context).getAuthenticatedUser ().getId ();
  Date now = new Date ();
  info.setCreateId (user);
  info.setCreateTime (now);
  info.setUpdateId (user);
  info.setUpdateTime (now);
  info.setVersionInd ("0");
  return info;
 }

 public void update (MetaInfo info)
  throws OperationFailedException
 {
  String user = new ContextHelper (context).getAuthenticatedUser ().getId ();
  Date now = new Date ();
  info.setUpdateId (user);
  info.setUpdateTime (now);
  int version = Integer.parseInt (info.getVersionInd ());
  version ++;
  info.setVersionInd (version + "");
 }

}
