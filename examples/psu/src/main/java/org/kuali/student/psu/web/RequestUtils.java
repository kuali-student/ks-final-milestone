package org.kuali.student.psu.web;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Some convenience methods for GWT/RPC related things
 */
public class RequestUtils {
	private static final int LANGUAGE_MODIFIERS = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE | Modifier.ABSTRACT | Modifier.STATIC | Modifier.FINAL | Modifier.SYNCHRONIZED | Modifier.NATIVE;

	public List<NameValuesBean> getSortedParameters(Map<?, ?> parameterMap) {
		List<NameValuesBean> parameters = new ArrayList<NameValuesBean>();
		for (Map.Entry<?, ?> pair : parameterMap.entrySet()) {
			String key = (String) pair.getKey();
			String[] values = (String[]) pair.getValue();
			NameValuesBean bean = new NameValuesBean();
			bean.setName(key);
			bean.setValues(values);
			parameters.add(bean);
		}
		Collections.sort(parameters);
		return parameters;
	}

	/**
	 * Convert a request attribute to a NameValuesBean
	 */
	public NameValuesBean getNameValuesBean(HttpServletRequest request, String name) {
		String params = request.getAttribute(name) + "";
		return getNameValuesBean(name, params);
	}

	/**
	 * Convert a string to a NameValuesBean
	 */
	public NameValuesBean getNameValuesBean(String name, String params) {
		NameValuesBean bean = new NameValuesBean();
		bean.setName(name);
		bean.setValues(new String[] { params });
		return bean;
	}

	/**
	 * Store the stacktrace for this exception into a string
	 */
	public String toString(Throwable e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		e.printStackTrace(out);
		return baos.toString();
	}

	/**
	 * Convert a parameter list to an XML string
	 */
	public String toXML(Object object) {
		// No parameters
		if (object == null) {
			return "";
		}

		// Otherwise use an XMLEncoder
		StringBuffer sb = new StringBuffer();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder e = new XMLEncoder(out);
		e.writeObject(object);
		e.close();
		sb.append(out.toString());
		return sb.toString();
	}

	/**
	 * Convert a parameter list to an XML string
	 */
	public String toXML(Object[] parameters) {
		// No parameters
		if (parameters == null || parameters.length == 0) {
			return "";
		}

		// All the parameters are primitive
		if (isPrimitivesOrStringsOnly(parameters)) {
			return getPrimitivesAndStrings(parameters);
		}

		// Otherwise use an XMLEncoder
		return toXML((Object) parameters);
	}

	protected String getPrimitivesAndStrings(Object[] parameters) {
		if (parameters.length == 1) {
			return parameters[0] + "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object parameter : parameters) {
			sb.append("<value>" + parameter + "</value>\n");
		}
		return sb.toString();
	}

	protected boolean isPrimitivesOrStringsOnly(Object[] parameters) {
		for (Object parameter : parameters) {
			if (!isPrimitiveOrString(parameter)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	protected boolean isPrimitiveOrString(Object object) {
		if (object instanceof Byte) {
			return true;
		}
		if (object instanceof Short) {
			return true;
		}
		if (object instanceof Integer) {
			return true;
		}
		if (object instanceof Long) {
			return true;
		}
		if (object instanceof Float) {
			return true;
		}
		if (object instanceof Double) {
			return true;
		}
		if (object instanceof Boolean) {
			return true;
		}
		if (object instanceof Character) {
			return true;
		}
		if (object instanceof String) {
			return true;
		}
		return false;
	}

	/**
	 * Convert a Method object to a string format that is easy to read. This is
	 * mostly copied from Method.toString();
	 */
	public String toString(Method m) {
		try {
			StringBuffer sb = new StringBuffer();
			int mod = m.getModifiers() & LANGUAGE_MODIFIERS;
			if (mod != 0) {
				sb.append(Modifier.toString(mod) + " ");
			}
			sb.append(getTypeName(m.getReturnType()) + " ");
			sb.append(m.getDeclaringClass().getName() + ".");
			sb.append(m.getName() + "(");
			Class<?>[] params = m.getParameterTypes();
			for (int j = 0; j < params.length; j++) {
				sb.append(getTypeName(params[j]));
				if (j < (params.length - 1))
					sb.append(",");
			}
			sb.append(")");
			Class<?>[] exceptions = m.getExceptionTypes();
			if (exceptions.length > 0) {
				sb.append(" throws ");
				for (int k = 0; k < exceptions.length; k++) {
					sb.append(getGeneric(exceptions[k].getName()));
					if (k < (exceptions.length - 1))
						sb.append(",");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			return "[" + e + "]";
		}
	}

	/**
	 * Utility routine to paper over array type names
	 */
	protected String getTypeName(Class<?> type) {
		if (type.isArray()) {
			try {
				Class<?> cl = type;
				int dimensions = 0;
				while (cl.isArray()) {
					dimensions++;
					cl = cl.getComponentType();
				}
				StringBuffer sb = new StringBuffer();
				sb.append(cl.getName());
				for (int i = 0; i < dimensions; i++) {
					sb.append("[]");
				}
				return getGeneric(sb.toString());
			} catch (Throwable e) { /* FALLTHRU */
			}
		}
		return getGeneric(type.getName());
	}

	protected String getGeneric(String s) {
		int pos = s.lastIndexOf(".");
		if (pos == -1) {
			return s;
		}
		return s.substring(pos + 1);
	}

	protected boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		if (object.toString().trim().equals("")) {
			return true;
		}
		return false;
	}

}
