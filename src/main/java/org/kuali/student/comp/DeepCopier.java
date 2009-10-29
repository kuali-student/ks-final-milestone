/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author nwright
 */
public class DeepCopier
{

 private Serializable orig;

 public DeepCopier (Serializable orig)
 {
  this.orig = orig;
 }

 /**
  * Returns a copy of the object, or null if the object cannot
  * be serialized.
  */
 public Serializable copy ()
 {
  Serializable obj = null;
  try
  {
   // Write the object out to a byte array
   ByteArrayOutputStream bos = new ByteArrayOutputStream ();
   ObjectOutputStream out = new ObjectOutputStream (bos);
   out.writeObject (orig);
   out.flush ();
   out.close ();

   // Make an input stream from the byte array and read
   // a copy of the object back in.
   ObjectInputStream in = new ObjectInputStream (
    new ByteArrayInputStream (bos.toByteArray ()));
   obj = (Serializable) in.readObject ();
  }
  catch (IOException e)
  {
   e.printStackTrace ();
  }
  catch (ClassNotFoundException cnfe)
  {
   cnfe.printStackTrace ();
  }
  return obj;
 }

}
