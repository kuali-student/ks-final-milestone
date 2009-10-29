/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import org.kuali.student.core.dto.RichTextInfo;

/**
 *
 * @author nwright
 */
public class RichTextInfoCopier
{
 private RichTextInfo orig;

 public RichTextInfoCopier (RichTextInfo orig)
 {
  this.orig = orig;
 }

 public RichTextInfo copy ()
 {
  if (orig == null)
  {
   return null;
  }
  RichTextInfo info = new RichTextInfo ();
  info.setFormatted (orig.getFormatted ());
  info.setPlain (orig.getPlain ());
  return info;
 }
}
