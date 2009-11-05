/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author nwright
 */
public class StringListCopier
{
 private List<String> orig;

 public StringListCopier (List<String> orig)
 {
  this.orig = orig;
 }

 public List copy ()
 {
  if (orig == null)
  {
   return null;
  }
  List<String> dest = new ArrayList ();
  for (String item : orig)
  {
   dest.add (item);
  }
  return dest;
 }
}
