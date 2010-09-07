/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model;

import java.util.List;
import java.util.Map;
//import org.kuali.student.core.assembly.data.LookupMetadata;

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
// public List<LookupMetadata> getLookups ();
 public List<Object> getLookups ();
}
