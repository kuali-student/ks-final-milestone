/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.dto;

/**
 * Base id to provide typing for the different types, org, clu, etc.
 * @author nwright
 */
public class Id
{
 private String id;

 public Id (String id)
 {
  this.id = id;
 }

 public String getId ()
 {
  return id;
 }
}
