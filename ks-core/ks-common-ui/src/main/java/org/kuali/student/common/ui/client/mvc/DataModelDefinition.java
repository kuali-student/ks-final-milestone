/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.mvc;

import java.util.Iterator;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.Key;

public class DataModelDefinition implements ModelDefinition {
	private Metadata metadata;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataModelDefinition() {
		
	}
	public DataModelDefinition(Metadata metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public void ensurePath(Data root, QueryPath path, boolean includeLeafNode) {
		try {
			_ensurePath(root, metadata, path.iterator(), includeLeafNode);
		} catch (RuntimeException e){
			throw new IllegalArgumentException("Invalid property path: " + path.toString(), e);
		}
	}
	
	private void _ensurePath(Data data, Metadata meta, Iterator<Data.Key> itr, boolean includeLeafNode) {
		Data.Key key = itr.next();

		if (key.toString().startsWith("_run")){
			if (!data.containsKey(key)){
				data.set(key,new Data.DataValue(new Data()));
			}
		} else {
			Metadata currentMeta = meta.getProperties().get(key.toString());
			if (currentMeta == null) {
				currentMeta = meta.getProperties().get(QueryPath.getWildCard());
			}
			if (currentMeta == null) {
				throw new IllegalArgumentException("Invalid property path element: " + key.toString());
			}
			
			if (itr.hasNext()) {
				// not at leaf node yet
				if (!data.containsKey(key)) {
					// branch doesn't exist yet
					Data.DataType type = currentMeta.getDataType(); 
					if (type != DataType.DATA && type != DataType.LIST) {
						throw new RuntimeException("Non-leaf nodes cannot be a simple type: " + key.toString() + " " + (type == null ? "null" : type.toString()));
					} else {
						Data.Value value = currentMeta.getDefaultValue();
						if (value == null) {
							value = new Data.DataValue(new Data());
						}
						data.set(key, value);
					}
				
				}
				//commented out: this is a workaround for some reason runtime metadata was disappearing from time to time???
				//if(!(key.toString().equals("_runtimeData"))){
				_ensurePath((Data) data.get(key), currentMeta, itr, includeLeafNode);
				//}
			} else {
				// we're at the leaf node
				if (includeLeafNode) {
					data.set(key, currentMeta.getDefaultValue());
				}
			}
		}

	}

	@Override
	public DataType getType(QueryPath path) {
		Metadata meta = getMetadata(path);
		if (meta == null) {
			return null;
		} else {
			return meta.getDataType();
		}
	}
	
	@Override
	public Metadata getMetadata(QueryPath path) {
		Metadata meta = this.metadata;
		for (Key key : path) {
			Metadata m = meta.getProperties().get(key.toString());
			if (m == null) {
				m = meta.getProperties().get(QueryPath.getWildCard());
			}
			if (m == null) {
				return null;
			} else {
				meta = m;
			}
		}
		return meta;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata root) {
		this.metadata = root;
	}

}
