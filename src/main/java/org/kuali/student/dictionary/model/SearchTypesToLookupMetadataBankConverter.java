/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model;

import org.kuali.student.dictionary.*;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.dictionary.model.SearchCriteriaParameter;
import org.kuali.student.dictionary.model.SearchType;

/**
 *
 * @author nwright
 */
public class SearchTypesToLookupMetadataBankConverter
{

 private DictionaryModel model;

 public SearchTypesToLookupMetadataBankConverter (DictionaryModel model)
 {
  this.model = model;
 }

 public List<LookupMetadata> getLookups ()
 {
  // basic conversion
  List<LookupMetadata> lookupMetas =
   new ArrayList (model.getSearchTypes ().size ());
  for (SearchType searchType : model.getSearchTypes ())
  {
//   //TODO: remove this once we have th spreadsheet filled out all the way
//   if (searchType.getImplementation () == null)
//   {
//    continue;
//   }
   lookupMetas.add (new SearchTypeToLookupMetadataConverter (searchType).convert ());
  }

  // attach childLookups
  for (SearchType searchType : model.getSearchTypes ())
  {
//   //TODO: remove this once we have th spreadsheet filled out all the way
//   if (searchType.getImplementation () == null)
//   {
//    continue;
//   }
   for (SearchCriteriaParameter param : searchType.getSearchCriteria ().
    getParameters ())
   {
    LookupMetadata parent = findLookup (lookupMetas, searchType.getLookupKey ());
    if (parent == null)
    {
     throw new DictionaryValidationException ("Could not find lookup for search [" +
      searchType.getKey () + "].");
    }
    if (param.getChildLookup () != null &&
      ! param.getChildLookup ().equals (""))
    {
     LookupParamMetadata paramMeta = findParam (parent, param.getKey ());
     if (paramMeta == null)
     {
      throw new DictionaryExecutionException ("Could not find lookup param [" +
       searchType.getKey () + "." + param.getKey () + "].");
     }
     LookupMetadata child = findLookup (lookupMetas, param.getChildLookup ());
     if (child == null)
     {
      throw new DictionaryExecutionException ("Child lookup not found [" +
       param.getChildLookup () + "].  It was specified on parameter [" +
       searchType.getKey () + "." + param.getKey () + "].");
     }
     paramMeta.setChildLookup (child);
    }
   }
  }

  return lookupMetas;
 }

 private LookupMetadata findLookup (List<LookupMetadata> metas, String key)
 {
  for (LookupMetadata meta : metas)
  {
   if (meta.getLookupKey ().equalsIgnoreCase (key))
   {
    return meta;
   }
  }
  return null;
 }

 private LookupParamMetadata findParam (LookupMetadata lookup, String key)
 {
  for (LookupParamMetadata meta : lookup.getParams ())
  {
   if (meta.getKey ().equalsIgnoreCase (key))
   {
    return meta;
   }
  }
  return null;
 }

}
