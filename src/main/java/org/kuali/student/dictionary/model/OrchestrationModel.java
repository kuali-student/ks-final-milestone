/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model;

import java.util.List;
import java.util.Map;
import org.kuali.student.common.assembly.client.LookupMetadata;

/**
 *
 * @author nwright
 */
public interface OrchestrationModel
{

 /**
  * get Orchestration Objects
  * @return
  */
 public Map<String, OrchestrationObject> getOrchestrationObjects ();

 /**
  * get look ups for bank of lookups
  * @return
  */
 public List<LookupMetadata> getLookups ();

}
