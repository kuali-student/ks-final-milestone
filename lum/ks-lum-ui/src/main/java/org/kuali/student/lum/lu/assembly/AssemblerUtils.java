package org.kuali.student.lum.lu.assembly;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.RuntimeDataHelper;

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
		return isCreated(data) || isUpdated(data) || isDeleted(data);
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
}
