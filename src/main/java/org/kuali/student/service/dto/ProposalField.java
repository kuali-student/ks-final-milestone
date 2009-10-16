/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.dto;

/**
 *
 * @author nwright
 */
public enum ProposalField
{

 name ("name"),
proposerPerson ("proposerPerson"),
proposerOrg ("proposerOrg"),
proposalReferenceType ("proposalReferenceType"),
proposalReference ("proposalReference"),
rationale ("rationale"),
detailDesc ("detailDesc"),
effectiveDate ("effectiveDate"),
expirationDate ("expirationDate"),
attributes ("attributes"),
metaInfo ("metaInfo"),
type ("type"),
state ("state"),
id ("id");
 private final String fieldName;

 ProposalField (String fieldName)
 {
  this.fieldName = fieldName;
 }

 public String getFieldName ()
 {
  return fieldName;
 }

}

