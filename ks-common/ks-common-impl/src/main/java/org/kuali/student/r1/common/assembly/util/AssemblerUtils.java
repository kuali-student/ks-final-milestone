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

package org.kuali.student.r1.common.assembly.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.data.Data.DataType;
import org.kuali.student.r1.common.assembly.data.Data.Key;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;
import org.kuali.student.r1.common.assembly.helper.RuntimeDataHelper;

public class AssemblerUtils {
	public enum VersionProperties implements PropertyEnum {
		TYPENAME("typeName"), ID("id"), VERSION_INDICATOR("versionIndicator");

		private final String key;

		private VersionProperties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public static String getVersionIndicator(Data data) {
		String result = null;
		Data versions = getVersions(data);
		if (versions == null) {
			return null;
		}
		if (versions.size() > 0) {
			Data v = versions.get(0);
			if (v != null) {
				result = v.get(VersionProperties.VERSION_INDICATOR.getKey());
			}
		}
		return result;
	}

	public static String getVersionIndicator(Data data, String typeName) {
		return getVersionIndicator(data, typeName, null);
	}

	public static String getVersionIndicator(Data data, String typeName, String id) {
		Data version = getVersionData(data, typeName, id);
		if (version == null) {
			return null;
		} else {
			return version.get(VersionProperties.VERSION_INDICATOR.getKey());
		}
	}

	public static Data getVersionData(Data data, String typeName, String id) {
		Data result = null;
		Data versions = getVersions(data);
		if (versions == null) {
			return null;
		}
		for (Data.Property p : versions) {
			Data v = p.getValue();
			if (typeName.equals((String) v.get(VersionProperties.TYPENAME.getKey())) && (id == null || id.equals((String) v.get(VersionProperties.ID.getKey())))) {
				result = v;
				break;
			}
		}
		return result;

	}

	public static Data getVersions(Data data) {
		Data result = null;

		if (data != null) {
			// TODO need a "standard properties" enum for values that could be present on any object?
			Data runtime = data.get("_runtimeData");
			if (runtime != null) {
				result = runtime.get(RuntimeDataHelper.Properties.VERSIONS.getKey());
			}
		}

		return result;
	}

	public static void addVersionIndicator(Data data, String typeName, String id, String version) {
		Data existing = getVersionData(data, typeName, id);
		if (existing == null) {
			Data d = new Data();
			d.set(VersionProperties.TYPENAME.getKey(), typeName);
			d.set(VersionProperties.ID.getKey(), id);
			d.set(VersionProperties.VERSION_INDICATOR.getKey(), version);
			Data versions = getVersions(data);
			if (versions == null) {
				versions = new Data();
				setVersions(data, versions);
			}
			versions.add(d);
		} else {
			existing.set(VersionProperties.VERSION_INDICATOR.getKey(), version);
		}
	}
	public static void setVersions(Data data, Data versions) {
		if (data != null) {
			// TODO need a "standard properties" enum for values that could be present on any object?
			Data runtime = data.get("_runtimeData");
			if (runtime == null) {
				runtime = new Data();
				data.set("_runtimeData", runtime);
			}
			runtime.set(RuntimeDataHelper.Properties.VERSIONS.getKey(), versions);
		}
	}

	public static boolean isCreated(Data data) {
		return isFlagSet(data, RuntimeDataHelper.Properties.CREATED.getKey());
	}

	public static boolean isDeleted(Data data) {
		return isFlagSet(data, RuntimeDataHelper.Properties.DELETED.getKey());
	}

	public static boolean isUpdated(Data data) {
		return isFlagSet(data, RuntimeDataHelper.Properties.UPDATED.getKey());
	}

	public static boolean isModified(Data data) {
		return isCreated(data) || isUpdated(data) || isDeleted(data); //|| isFlagSet(data, RuntimeDataHelper.Properties.DIRTY.getKey());
	}

	public static void setCreated(Data data, boolean flag) {
		setFlag(data, RuntimeDataHelper.Properties.CREATED.getKey(), flag);
	}

	public static void setDeleted(Data data, boolean flag) {
		setFlag(data, RuntimeDataHelper.Properties.DELETED.getKey(), flag);
	}

	public static void setUpdated(Data data, boolean flag) {
		setFlag(data, RuntimeDataHelper.Properties.UPDATED.getKey(), flag);
	}

	private static void setFlag(Data data, String key, Boolean flag) {
		if (data != null) {
			Data runtime = data.get("_runtimeData");
			if (runtime == null) {
				runtime = new Data();
				data.set("_runtimeData", runtime);
			}
			runtime.set(key, flag);
		}
	}
	private static boolean isFlagSet(Data data, String key) {
		boolean result = false;

		if (data != null) {
			Data runtime = data.get("_runtimeData");
			if (runtime != null) {
				result = runtime.get(key) != null && (Boolean) runtime.get(key);
			}
		}

		return result;
	}


	 public static List<QueryPath> findDirtyElements(Data data) {
	     List<QueryPath> results = new ArrayList<QueryPath>();
	     _findDirtyElements(data, results, new QueryPath());
	     return results;
	 }
	 private static void _findDirtyElements(Data data, List<QueryPath> results, QueryPath currentFrame) {
	     if (data == null) {
	         return;
	     }

	     Data flags = getDirtyFlags(data);
	     if (flags != null && flags.size() > 0) {
	         for (Data.Property p : flags) {
	             QueryPath q = new QueryPath();
	             q.addAll(currentFrame);
	             Key key = p.getWrappedKey();
	             q.add(key);
	             results.add(q);
	         }
	     }

	     for (Data.Property p : data) {
	         if (p.getValueType().equals(Data.class) && p.getValue() != null) {
	                QueryPath q = new QueryPath();
	                q.addAll(currentFrame);
	                Key key = p.getWrappedKey();
	                q.add(key);

	                _findDirtyElements((Data) p.getValue(), results, q);
	         }
	     }
	 }
	 public static Data getDirtyFlags(Data data) {
	     Data result = null;
	     Data runtime = data.get("_runtimeData");
	     if (runtime != null) {
	         result = runtime.get("dirty");

	     }
	     return result;
	 }
	 /*public static Metadata get(Metadata metadata, QueryPath path) {

	 }*/
	 public static Metadata get(Metadata metadata, QueryPath frame) {
	     if(frame.size() == 1) {
	         return metadata.getProperties().get(frame.get(0).get());
	     } else {
	         if (metadata.getDataType() == DataType.LIST){
	        	 return get(metadata, frame, DataType.LIST);
	         }
	         else{
	        	 return get(metadata.getProperties().get(frame.get(0).get()), frame.subPath(1, frame.size()));
	         }
	     }
	 }

	 private static Metadata get(Metadata metadata, QueryPath frame, DataType type){
		 if(type == DataType.LIST){
			 return get(metadata.getProperties().get(QueryPath.getWildCard()), frame.subPath(1, frame.size()));
		 }
		 else{
			return get(metadata.getProperties().get(frame.get(0).get()), frame.subPath(1, frame.size()));
		 }
	 }
}
