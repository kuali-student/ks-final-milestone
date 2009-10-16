/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.dto;

/**
 * Base key to provide typing for the different types of keys, luTypeKey, SearchKey, etc
 * @author nwright
 */
public class Key
{
 private String key;

 public Key (String key)
 {
  this.key = key;
 }


 public String getKey ()
 {
  return key;
 }
}
