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
package org.kuali.student.core.assembly.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;

public class MetadataFormatter {

	private StringBuilder builder = new StringBuilder(5000);
	private Metadata structureMetadata;
	private String type;
	private String rowSeperator = "\n";
	private String colSeperator = "|";
	private String structureName;
	private int level;
//	private Map<String, Metadata> subStructuresToProcess = new LinkedHashMap<String, Metadata>();
	private Set<Metadata> structuresAlreadyProcessed;
	private MetadataFormatter parent;

	public MetadataFormatter(String structureName, Metadata structureMetadata,
			String type, MetadataFormatter parent,
			Set<Metadata> structuresAlreadyProcessed, int level) {
		this.structureName = structureName;
		this.structureMetadata = structureMetadata;
		this.type = type;
		this.parent = parent;
		this.structuresAlreadyProcessed = structuresAlreadyProcessed;
		this.level = level;
	}

	public String getStructureName() {
		return structureName;
	}

	public MetadataFormatter getParent() {
		return parent;
	}

	public String getRowSeperator() {
		return rowSeperator;
	}

	public void setRowSeperator(String rowSeperator) {
		this.rowSeperator = rowSeperator;
	}

	public String getColSeparator() {
		return colSeperator;
	}

	public void setColSeparator(String separator) {
		this.colSeperator = separator;
	}

	private String pad(String str, int size) {
		StringBuilder padStr = new StringBuilder(size);
		padStr.append(str);
		while (padStr.length() < size) {
			padStr.append(' ');
		}
		return padStr.toString();
	}

	public String formatForWiki() {
		if (!this.structuresAlreadyProcessed.add(structureMetadata)) {
			return "";
		}
		if (level == 1) {
			builder.append(rowSeperator);
			// builder.append
			// ("======= start dump of object structure definition ========");
			builder.append(rowSeperator);
			String header = "h1. " + this.calcSimpleName(structureName);
			if (type != null) {
				header = "h2. " + type;
			}
			builder.append(header);
			builder.append("{anchor:" + structureName + "}");
			builder.append(rowSeperator);
			builder.append("The object key is " + structureName);
			builder.append(rowSeperator);
			builder.append("The type is " + type);
			builder.append(rowSeperator);
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Field");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Required?");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("DataType");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Length");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Dynamic");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Default");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Repeats?");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Valid Characters");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Lookup Widget");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append("Lookup");
			builder.append(colSeperator);
			builder.append(colSeperator);
			builder.append(rowSeperator);
		}

		List<String> keys = new ArrayList<String>();
		keys.addAll(structureMetadata.getProperties().keySet());
		Collections.sort(keys);
		for (String key : keys) {
   if (key.equalsIgnoreCase ("_runtimeData"))
   {
    continue;
   }
			Metadata fieldMeta = structureMetadata.getProperties().get(key);
			builder.append(colSeperator);
			builder.append(calcFieldInfo (key, fieldMeta));
			builder.append(colSeperator);
			builder.append(pad(calcRequired(fieldMeta), 10));
			builder.append(colSeperator);
			builder.append(pad(calcDataType(fieldMeta), 25));
			builder.append(colSeperator);
			builder.append(pad(calcLength(fieldMeta), 15));
			builder.append(colSeperator);
			builder.append(pad(calcDynamic(fieldMeta), 7));
			builder.append(colSeperator);
			builder.append(pad(calcDefaultValue(fieldMeta), 15));
			builder.append(colSeperator);
			builder.append(calcRepeating(fieldMeta));
			builder.append(colSeperator);
			builder.append(calcValidChars(fieldMeta));
			builder.append(colSeperator);
			builder.append(calcWidget(fieldMeta));
			builder.append(colSeperator);
			builder.append(calcLookup(fieldMeta));
			builder.append(colSeperator);
			builder.append(rowSeperator);
			if (fieldMeta.getDataType().equals(Data.DataType.DATA)
					|| fieldMeta.getDataType().equals(Data.DataType.LIST)) {
				if (fieldMeta.getProperties() == null) {
					throw new IllegalArgumentException(
							fieldMeta.getName()
									+ " is DATA but does not have a sub-structure defined");
				}
				MetadataFormatter formatter = new MetadataFormatter(key,
						fieldMeta, null, this, structuresAlreadyProcessed,
						level + 1);
				builder.append(formatter.formatForWiki());
			}
		}

		return builder.toString();
	}

	private String calcDataType(Metadata fieldMeta) {
		if (fieldMeta.getDataType().equals(Data.DataType.LIST)) {
			StringBuilder type = new StringBuilder();
			type.append("LIST of ");
			String comma = "";
			for (String key : fieldMeta.getProperties().keySet()) {
				type.append(comma);
				type.append(fieldMeta.getProperties().get(key).getDataType()
						.toString());
				comma = ", ";
			}
			return type.toString();
		}
		return fieldMeta.getDataType().toString();
	}

	private String calcDefaultValue(Metadata fieldMeta) {
		if (fieldMeta.getDefaultValue() != null) {
			return fieldMeta.getDefaultValue().toString();
		}
		return " ";
	}

	private String calcDynamic(Metadata meta) {
		if (meta.isDynamic()) {
			return "dynamic";
		}
		return " ";
	}

 private String calcFieldInfo (String key, Metadata fieldMeta)
 {
  StringBuilder bldr = new StringBuilder (40);
  bldr.append (pad(calcFullyQualifiedFieldName(key), 30));
  if (fieldMeta.getLabelKey () != null)
  {
   bldr.append ("\\\\\n");
   bldr.append ("Label: ");
   bldr.append (fieldMeta.getLabelKey ());
  }
  return bldr.toString ();
 }

	public String calcFullyQualifiedFieldName(String fieldName) {
		if (parent == null) {
			return escapeWiki(fieldName);
		}
		return parent.calcFullyQualifiedFieldName(structureName) + "."
				+ escapeWiki(fieldName);
	}

	private String calcSimpleName(String name) {
		if (name.lastIndexOf(".") != -1) {
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		return name;
	}

	private String calcNotSoSimpleName(String name) {
		if (name.lastIndexOf(".") == -1) {
			return name;
		}
		String simpleName = calcSimpleName(name);
		String fieldName = calcSimpleName(name.substring(0, name.length()
				- simpleName.length() - 1));
		return fieldName + "." + simpleName;
	}

	private String calcRequired(Metadata fieldMeta) {
		for (ConstraintMetadata cons : fieldMeta.getConstraints()) {
			if (cons.getMaxOccurs() != null) {
				if (cons.getMaxOccurs() == 0) {
					return "NOT USED";
				}
			}

			if (cons.getMinOccurs() != null) {
				if (cons.getMinOccurs() >= 1) {
					return "required";
				}
			}
		}
		return " ";
		// return "optional";
	}

	private static final String LINK_TO_DEFINITIONS = "KULSTG:Formatted View of Base Dictionary#Valid Character Definitions";

	private String calcValidChars(Metadata fieldMeta) {
		for (ConstraintMetadata cons : fieldMeta.getConstraints()) {
			if (cons.getValidChars() == null) {
				continue;
			}
			String validChars = escapeWiki(cons.getValidChars());
			String descr = "[" + "See" + "|" + LINK_TO_DEFINITIONS + "]"
					+ "\\\\\n" + validChars;
			return descr;
		}
		return " ";
	}

	private String escapeWiki(String str) {
		StringBuilder bldr = new StringBuilder(str.length());
		boolean precededByBackSlash = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '\\':
			case '[':
			case '*':
			case ']':
			case '|':
				if (!precededByBackSlash) {
					bldr.append('\\');
				}
				break;
			default:
				break;
			}
			bldr.append(c);
			if (c == '\\') {
				precededByBackSlash = true;
			} else {
				precededByBackSlash = false;
			}
		}
		return bldr.toString();
	}

	private String calcWidget(Metadata fieldMeta) {
		StringBuilder bldr = new StringBuilder();
		String comma = "";
		if (!fieldMeta.isCanEdit()) {
			bldr.append(comma);
			bldr.append("not editable");
			comma = ", ";
		}
		if (!fieldMeta.isCanView()) {
			bldr.append(comma);
			bldr.append("not viewable");
			comma = ", ";
		}
		if (!fieldMeta.isCanUnmask()) {
			bldr.append(comma);
			bldr.append("Not unmaskable");
			comma = ", ";
		}
		if (fieldMeta.getInitialLookup() != null) {
			bldr.append(comma);
			bldr.append(fieldMeta.getInitialLookup().getWidget());
			comma = ", ";
		}
		if (bldr.length() == 0) {
			bldr.append(" ");
		}
		return bldr.toString();
	}

	private String calcLookup(Metadata fieldMeta) {
  StringBuilder bldr = new StringBuilder ();
		if (fieldMeta.getInitialLookup() != null) {
		 bldr.append (calcLookup (fieldMeta.getInitialLookup ()));
		}
   
  if (fieldMeta.getAdditionalLookups () != null) {
   if (fieldMeta.getAdditionalLookups ().size () > 0) {
    if (fieldMeta.getInitialLookup() == null) {
		   bldr.append ("No initial lookup but...");
		  }
		  bldr.append("\\\\");
		  bldr.append("\n");   
		  bldr.append("\\\\");
		  bldr.append("\n");
    bldr.append ("Additional Lookups:");
		  bldr.append("\\\\");
		  bldr.append("\n");
   }
   for (LookupMetadata lm : fieldMeta.getAdditionalLookups ())  {
		  bldr.append("\\\\");
  		bldr.append("\n");
    bldr.append (calcLookup (lm));
    bldr.append("\\\\");
		  bldr.append("\n");
   }  
  }
  if (bldr.length () == 0)
  {
   bldr.append (" ");
  }
  return bldr.toString ();
 }

 private String calcLookup(LookupMetadata lm) {
		StringBuilder bldr = new StringBuilder();
		bldr.append(lm.getId());
//  if (lm.getUsage () != null) {
//   bldr.append (" usage " + lm.getUsage ());
//  }
		// this is the search description not the lookup description
		bldr.append (" - ");
		bldr.append (lm.getName());
  bldr.append (" " + lm.getWidget ());
  String and = " with option ";
  if (lm.getWidgetOptions () != null) {
   for (LookupMetadata.WidgetOption wo: lm.getWidgetOptions ().keySet ())
   {
    bldr.append (" and ");
    bldr.append (wo);
    bldr.append ("=");
    bldr.append (lm.getWidgetOptions ().get (wo));
   }
  }
		and = "";
		bldr.append("\\\\\n");
		bldr.append("Implemented using search: ");
		String searchPage = calcWikiSearchPage(lm.getSearchTypeId());
		bldr.append("[" + lm.getSearchTypeId() + "|" + searchPage + "#"
				+ lm.getSearchTypeId() + "]");
		List<LookupParamMetadata> configuredParameters = filterConfiguredParams(lm
				.getParams());
		if (configuredParameters.size() > 0) {
			bldr.append("\\\\");
			bldr.append("\n");
			bldr.append(" where ");
			and = "";
			for (LookupParamMetadata param : configuredParameters) {
				bldr.append(and);
				and = " and ";
				bldr.append(param.getName());
    bldr.append (" (" + param.getDataType () + ") ");
				bldr.append("=");
				if (param.getDefaultValueString() != null) {
					bldr.append(param.getDefaultValueString());
				}
				if (param.getDefaultValueList() != null) {
					String comma = "";
					for (String defValue : param.getDefaultValueList()) {
						bldr.append(comma);
						comma = ", ";
						bldr.append(defValue);
					}
				}
			}
  }
  List<LookupParamMetadata> userEnterableParameters = this.filterUserEnterableParams (lm
				.getParams());
		if (userEnterableParameters.size() > 0) {
			bldr.append("\\\\");
			bldr.append("\n");
			bldr.append(" and the user can enter: ");
			for (LookupParamMetadata param : userEnterableParameters) {
				bldr.append ("\\\\\n");
				bldr.append(param.getName());
    bldr.append (" (" + param.getDataType () + ")");
    if (param.getWidget () != null) {
				 bldr.append(" using widget ");
     bldr.append (param.getWidget ());
    }
				if (param.getDefaultValueString() != null) {
					bldr.append("defaulted to " + param.getDefaultValueString());
				}
				if (param.getDefaultValueList() != null) {
					String comma = "defaulted to ";
					for (String defValue : param.getDefaultValueList()) {
						bldr.append(comma);
						comma = ", ";
						bldr.append(defValue);
					}
				}
    if (param.getChildLookup () != null)
    {
			  bldr.append("\\\\");
			  bldr.append("\n");
     bldr.append ("using a child lookup: ");
			  bldr.append("\\\\");
			  bldr.append("\n");
     bldr.append (calcLookup (param.getChildLookup ()));
    }
   }
		}
		return bldr.toString();
 }

	private static final String PAGE_PREFIX = "Formatted View of ";
	private static final String PAGE_SUFFIX = " Searches";

	private String calcWikiSearchPage(String searchType) {
		return PAGE_PREFIX + calcWikigPageAbbrev(searchType) + PAGE_SUFFIX;
	}

	private String calcWikigPageAbbrev(String searchType) {
		if (searchType == null) {
			return null;
		}
		if (searchType.equals("enumeration.management.search")) {
			return "EM";
		}
		if (searchType.startsWith("lu.")) {
			return "LU";
		}
		if (searchType.startsWith("cluset.")) {
			return "LU";
		}
		if (searchType.startsWith("lo.")) {
			return "LO";
		}
		if (searchType.startsWith("lrc.")) {
			return "LRC";
		}
		if (searchType.startsWith("comment.")) {
			return "Comment";
		}
		if (searchType.startsWith("org.")) {
			return "Organization";
		}
		if (searchType.startsWith("atp.")) {
			return "ATP";
		}
		if (searchType.startsWith("person.")) {
			return "Person";
		}
  if (searchType.startsWith("proposal.")) {
			return "Proposal";
		}
		throw new IllegalArgumentException("Unknown type of search: "
				+ searchType);
	}

	private List<LookupParamMetadata> filterConfiguredParams(
			List<LookupParamMetadata> params) {
		List<LookupParamMetadata> list = new ArrayList<LookupParamMetadata>();
		if (params == null) {
			return list;
		}
		if (params.size() == 0) {
			return list;
		}
		for (LookupParamMetadata param : params) {
			if (param.getDefaultValueString() != null) {
				list.add(param);
				continue;
			}
			if (param.getDefaultValueList() != null) {
				list.add(param);
    continue;
			}
		}
		return list;
	}

 	private List<LookupParamMetadata> filterUserEnterableParams (
			List<LookupParamMetadata> params) {
		List<LookupParamMetadata> list = new ArrayList<LookupParamMetadata>();
		if (params == null) {
			return list;
		}
		if (params.size() == 0) {
			return list;
		}
		for (LookupParamMetadata param : params) {
			if (param.getWriteAccess () != null) {
    if ( ! param.getWriteAccess ().equals (Metadata.WriteAccess.NEVER)) {
			 	list.add(param);
			 	continue;
    }
			}
		}
		return list;
	}

	private String calcRepeating(Metadata fieldMeta) {
		if (!fieldMeta.getDataType().equals(Data.DataType.LIST)) {
			return " ";
		}
		// return "repeating";
		MetadataInterrogator mi = new MetadataInterrogator(fieldMeta);
		if (mi.getSmallestMaxOccurs() == null) {
			if (mi.getLargestMinOccurs() != null
					&& mi.getLargestMinOccurs() > 1) {
				return "repeating: minimum " + mi.getLargestMinOccurs()
						+ " times";
			}
			return "repeating: unlimited";
		}
		if (mi.getSmallestMaxOccurs() == 0) {
			return "NOT USED";
		}
		if (mi.getSmallestMaxOccurs() == 1) {
			return " ";
			// return "single";
		}

		if (mi.getLargestMinOccurs() != null) {
			if (mi.getLargestMinOccurs() > 1) {
				return "repeating: " + mi.getLargestMinOccurs() + " to "
						+ mi.getSmallestMaxOccurs() + " times";
			}
		}
		return "repeating: maximum " + mi.getSmallestMaxOccurs() + " times";
	}

	private String calcLength(Metadata fieldMeta) {
		MetadataInterrogator mi = new MetadataInterrogator(fieldMeta);
		if (mi.getSmallestMaxLength() != null) {
			if (mi.getLargestMinLength() != null
					&& mi.getLargestMinLength() != 0) {
				if (mi.getSmallestMaxLength() == mi.getLargestMinLength()) {
					return ("(must be " + mi.getSmallestMaxLength() + ")");
				}
				return "(" + mi.getLargestMinLength() + " to "
						+ mi.getSmallestMaxLength() + ")";
			}
			return "(up to " + mi.getSmallestMaxLength() + ")";
		}
		if (mi.getLargestMinLength() != null) {
			return "(over " + mi.getLargestMinLength() + ")";
		}
		return " ";
	}
}
