/*
 * Copyright 2014 The Kuali Foundation 
 * 
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasPrimaryKey;
import org.kuali.student.r2.common.infc.HasType;

/**
 * @author Kuali Student Team (ks.collab@kuali.org)
 * 
 * Helper methods for retrieving single and bulk elements from a cache.
 *
 */
public final class KSCacheUtils {

	/**
	 * 
	 */
	private KSCacheUtils() {
		// intentionally private
	}
	
	/**
	 * 
	 * A mechanism to load a single element using its key when it is not available in the cache.
	 *
	 * @param <T>
	 */
	public static interface SingleCacheElementLoader<T extends HasPrimaryKey> {
		/**
		 * Load the element from its data source (not-cached) to be used within the helper.
		 * 
		 * @param key
		 * @return
		 * @throws DoesNotExistException
		 * @throws OperationFailedException
		 * @throws InvalidParameterException
		 * @throws MissingParameterException
		 * @throws PermissionDeniedException
		 */
		public T load (String key) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException;
	}
	
	/**
	 * A mechanism to load multiple elements from its data source (non-cached) to be used within the helper to acquire those that
	 * are not presently in the cache.
	 * 
	 * @param <T>
	 */
	public static interface BulkCacheElementLoader<T extends HasPrimaryKey> { 
	
		/**
		 * Used to load the elements from the given element keys.
		 * 
		 * @param elementIds
		 * @param context
		 * @return
		 * @throws OperationFailedException 
		 * @throws DoesNotExistException 
		 * @throws PermissionDeniedException 
		 * @throws MissingParameterException 
		 * @throws InvalidParameterException 
		 */
		public List<T> load(List<String>cacheMissKeys) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException;
		
	}
	
	/**
	 * A helper to be used in single element load scenarios.  If not in the cache the loader is used to load it at which point it is added to the cache.
	 * 
	 * @param cache
	 * @param key
	 * @param loader
	 * @return 
	 * @throws PermissionDeniedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws OperationFailedException 
	 * @throws DoesNotExistException 
	 */
	public static <T extends HasPrimaryKey> T cacheAwareLoad (Cache cache, String key, SingleCacheElementLoader<T>loader) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
		return cacheAwareLoad (cache, key, null, loader);
	}
	
	/**
	 * Helper that load using the multikey if it is not null.  Otherwise it loads and stores using the key.
	 * 
	 * @param cache
	 * @param key
	 * @param multiKey
	 * @param loader
	 * @return the element that matches the key and multikey provided
	 * @throws DoesNotExistException
	 * @throws OperationFailedException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws PermissionDeniedException
	 */
	public static <T extends HasPrimaryKey> T cacheAwareLoad (Cache cache, String key, MultiKey multiKey, SingleCacheElementLoader<T>loader) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
		
		Element cachedResult = null;
		
		if (multiKey != null) 
			cachedResult = cache.get(multiKey);
		else
			cachedResult = cache.get(key);
		
		if (cachedResult == null) {
			// not in the cache
			T loadedElement = loader.load(key);
			
			if (loadedElement == null)
				throw new DoesNotExistException("cacheAwareLoad no such element for key = " + key);
			
			// store the loaded element into the cache
			if (multiKey != null) 
				cache.put(new Element(multiKey, loadedElement));			
			else {
				
				if (loadedElement instanceof HasId) 
					cache.put(new Element(((HasId)loadedElement).getId(), loadedElement));
				else if (loadedElement instanceof HasType)
					cache.put(new Element(((HasType)loadedElement).getTypeKey(), loadedElement));
				else
					throw new OperationFailedException("unsupported cache element of type: " + loadedElement.getClass().getName());
			}
			
			return loadedElement;
		}
		else {
			// use cached value
			return (T)cachedResult.getValue();
		}
	}
	/**
	 * A helper to be used in bulk load scenarios within caching decorator's.  This will find elements using the cache if they exist otherwise bring them in using the loader.
	 * 
	 * The loaded elements will then be added to the cache.
	 * 
	 * @param cache
	 * @param elementIds
	 * @param loader
	 * @return the list of elements that match the elementKeys provided.
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 */
	public static <T extends HasPrimaryKey> List<T> cacheAwareBulkLoad (Cache cache, List<String>elementKeys, BulkCacheElementLoader<T> loader) throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
	        
			List<T>result = new ArrayList<T>();
			
	        List<String>cacheMissKeys = new ArrayList<String>();
	        
	        for (String key : elementKeys) {
	        	  
	              Element cachedResult = cache.get(key);
	              
	              if (cachedResult != null) { 
	            	  // a value exists for the key in the cache so use it
	            	  T cachedInfo = (T)cachedResult.getValue();
	            	  result.add(cachedInfo);
	              }
	              else {
	            	  // a cache-miss so accumulate the key
	            	  cacheMissKeys.add(key);
	              }
	        }
	        
	        try {
	        	
				if (cacheMissKeys.size() > 0) {
					// load all of the cache-miss keys
					List<T> fetchedResults = loader.load(cacheMissKeys);
	
					for (T fetchedDto : fetchedResults) {
	
						// add to the result
						result.add(fetchedDto);
	
						// store into the cache
						if (fetchedDto instanceof HasId)
							cache.put(new Element(((HasId) fetchedDto).getId(),
									fetchedDto));
						else if (fetchedDto instanceof HasType)
							cache.put(new Element(((HasType) fetchedDto)
									.getTypeKey(), fetchedDto));
						else
							throw new OperationFailedException(
									"unsuppoeted cache element of type: "
											+ fetchedDto.getClass().getName());
	
					}
				}
			} catch (DoesNotExistException e) {
				throw new OperationFailedException("getLuiLuiRelationsByIds: calling next decorator failed.", e);
			}
	        
	        return result;
	}

}
