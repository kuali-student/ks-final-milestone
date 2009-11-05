/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.Date;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.exceptions.OperationFailedException;

/**
 *
 * @author nwright
 */
public class MetaInfoUtility
{


 public MetaInfo getForCreate ()
  throws OperationFailedException
 {
  MetaInfo info = new MetaInfo ();
  String user = ServiceContext.get ().getAuthenticatedUser ();
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
  String user = ServiceContext.get ().getAuthenticatedUser ();
  Date now = new Date ();
  info.setUpdateId (user);
  info.setUpdateTime (now);
  int version = Integer.parseInt (info.getVersionInd ());
  version ++;
  info.setVersionInd (version + "");
 }

}
