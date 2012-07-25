/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Helps perform merge operations that take place when an entity is loaded from
 * the database and then updated from the dto content being provided through the
 * service.
 * 
 * @author ocleirig
 * 
 * @param <E> The entity being merged into.
 * @param <INFO> the dto providing the data to be merged.
 */
public final class EntityMergeHelper<E, INFO> {

	/**
	 * Options when merging the entity from the dto.
	 *
	 * @param <E>
	 * @param <INFO>
	 */
	public static interface EntityMergeOptions<E, INFO> {
		
		/**
		 * Extracts the id from the entity object.
		 * 
		 * @param entity the entity.
		 * @return the database id for the entity.
		 */
		public String getEntityId(E entity);

		/**
		 * Extracts the id from the dto info object.
		 * 
		 * @param info the dto,
		 * @return the database id encoded in the dto.
		 */
		public String getInfoId(INFO info);

		/**
		 * Merges the dto info into the entity.
		 * 
		 * @param entity the target entity.
		 * @param info the source dto
		 * @return the list of objects that should be detatched due to the merge.
		 */
		public List<Object> merge(E entity, INFO info);

		/**
		 * Creates a new instance of the target entity.
		 * 
		 * @param info the source dto.
		 * @return the new entity.
		 */
		public E create(INFO info);

	}

	/**
     * Options for merging a list of strings into a list of entities.
	 * 
	 * @param <E> The Entity object
	 */
	public static interface StringMergeOptions<E> {

		/**
		 * Extract a comparison key from the entity that will match the value of the string list items (for comparison purposes)
		 * 
		 * @param entity
		 * @return the comnparison key.
		 */
		public String getKey(E entity);

		/**
		 * Create a new entry based on the string value provided.
		 * 
		 * @param string
		 * @return the new entity.
		 */
		public E create(String value);

	}

	/**
	 * The results of a merge are the merged list and the list of orphaned data.
	 * 
	 * @param <E> the target entity.
	 */
	public static final class EntityMergeResult<E> {

		private final List<E> mergedList;

		private final List<Object> orphanList;

		public EntityMergeResult(List<E> mergedList, List<Object> orphanList) {
			super();
			this.mergedList = mergedList;
			this.orphanList = orphanList;
		}

		public List<E> getMergedList() {
			return mergedList;
		}

		public List<Object> getOrphanList() {
			return orphanList;
		}

	}

	
	public EntityMergeHelper() {
	}

	/**
	 * Merges a simple string list against a persisted list. We assume that if
	 * the string value does not exist then we can delete the item.
	 * 
	 * @param entityList the list of entities
	 * @param stringList the list of values
	 * @return the merge results.
	 */
	public EntityMergeResult<E> mergeStringList(List<E> entityList,
			List<String> stringList, StringMergeOptions<E> options) {

		/*
		 * Steps:
		 * 1. create existing map
		 * 2. create new entries or remove existing from the map
		 * 3. return new entity list and orphan list.
		 */
		List<E> mergedList = new ArrayList<E>();

		List<Object> orphanDataList = new ArrayList<Object>();

		Map<String, E> existingEntityMap = new LinkedHashMap<String, E>();

		if (entityList != null) {
			for (E e : entityList) {

				String key = options.getKey(e);

				existingEntityMap.put(key, e);

			}
		}
		for (String string : stringList) {

			E entity = existingEntityMap.get(string);

			if (entity == null) {
				// create

				entity = options.create(string);

			} else {
				// remove from existing map
				existingEntityMap.remove(string);
			}

			mergedList.add(entity);

		}

		orphanDataList.addAll(existingEntityMap.values());

		return new EntityMergeResult<E>(mergedList, orphanDataList);
	}

	/**
	 * Performs a Merge of the current entity list from the info list and
	 * options provided.
	 * 
	 * The results are the list of merged entities and a list of orphaned objects that can be deleted from the database.
	 * 
	 * @param entityList the target list of entities
	 * @param infoList the source list of dto info objects.
	 * @param options the logic for extracting the keys and creating/merging the entities.
	 * @return the results of the merge.
	 */
	public EntityMergeResult<E> merge(List<E> entityList, List<INFO> infoList,
			EntityMergeOptions<E, INFO> options) {

		List<Object> orphanDataList = new ArrayList<Object>();

		Map<String, E> existingEntityMap = new LinkedHashMap<String, E>();

		if (entityList != null) {
			for (E e : entityList) {

				String id = options.getEntityId(e);

				existingEntityMap.put(id, e);

			}
		}

		List<E> mergedList = new ArrayList<E>();

		for (INFO info : infoList) {

			String id = options.getInfoId(info);

			E entity = null;
			if (existingEntityMap.containsKey(id)) {
				// merge
				entity = existingEntityMap.remove(id);

				orphanDataList.addAll(options.merge(entity, info));

			} else {
				// new entry

				entity = options.create(info);
			}

			mergedList.add(entity);
		}

		orphanDataList.addAll(existingEntityMap.values());

		return new EntityMergeResult<E>(mergedList, orphanDataList);

	}

}
