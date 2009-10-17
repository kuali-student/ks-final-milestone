/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

/**
 *
 * @author nwright
 */
public interface KSContext
{

 public void register (Class infc, Object obj);

 public Object getInstance (Class infc);

}
