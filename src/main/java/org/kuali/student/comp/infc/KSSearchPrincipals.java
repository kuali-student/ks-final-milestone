/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import java.util.List;
import org.kuali.rice.kew.identity.PrincipalId;

import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.QueryParamValue;

/**
 *
 * @author nwright
 */
public interface KSSearchPrincipals
{

 public void setSearchType (String searchType);

 public void setSearchParms (List<QueryParamValue> params);

 public void setResultValueKey (String principalIdKey);

 public List<PrincipalId> search ()
  throws OperationFailedException;

}
