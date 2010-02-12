package org.kuali.student.common.ui.server.gwt;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Some convenience methods for GWT/RPC related things
 */
public class RPCUtils {
	private static final int LANGUAGE_MODIFIERS = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE | Modifier.ABSTRACT | Modifier.STATIC | Modifier.FINAL | Modifier.SYNCHRONIZED | Modifier.NATIVE;

	/**
	 * Convert a parameter list to an XML string
	 */
	public String toXML(Object[] parameters) {
		StringBuffer sb = new StringBuffer();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder e = new XMLEncoder(out);
		e.writeObject(parameters);
		e.close();
		sb.append(out.toString());
		return sb.toString();
	}

	/**
	 * Convert a Method object to a String that is easy for humans to read. This
	 * is mostly copied from Method.toString();
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

}
