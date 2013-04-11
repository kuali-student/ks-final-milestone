package org.kuali.student.ap.framework.context.support;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

/**
 * Data Storage for the Term and Year of a single atp. Formats different output
 * forms of the data
 */
public class DefaultYearTerm implements YearTerm, Comparable<YearTerm> {

	private static final Logger LOG = Logger.getLogger(DefaultYearTerm.class);

	private static String[] termTypeOrder;

	private static String[] getTermTypeOrder() {
		if (termTypeOrder == null) {
			try {
				List<TypeTypeRelationInfo> relations = KsapFrameworkServiceLocator
						.getTypeService()
						.getTypeTypeRelationsByOwnerAndType(
								AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY,
								TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY,
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
				if (relations == null || relations.isEmpty())
					throw new IllegalStateException(
							"No term types available using TypeService.getTypeTypeRelationsByOwnerAndType()");
				Collections.sort(relations,
						new Comparator<TypeTypeRelationInfo>() {
							@Override
							public int compare(TypeTypeRelationInfo o1,
									TypeTypeRelationInfo o2) {
								Integer r1 = o1.getRank() == null ? 0 : o1
										.getRank();
								Integer r2 = o2.getRank() == null ? 0 : o2
										.getRank();
								return r1.compareTo(r2);
							}
						});
				String[] tto = new String[relations.size()];
				for (int i = 0; i < tto.length; i++)
					tto[i] = relations.get(i).getRelatedTypeKey();
				termTypeOrder = tto;
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Type lookup error", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Type lookup error", e);
			}
		}
		return termTypeOrder;
	}

	private final String termType;
	private final int year;

	public DefaultYearTerm(String termType, int year) {
		if (!Arrays.asList(getTermTypeOrder()).contains(termType))
			throw new IllegalArgumentException("Term type " + termType
					+ " not supported");
		this.termType = termType;
		this.year = year;
	}

	public String getTermType() {
		return termType;
	}

	public int getYear() {
		return year;
	}

	@Override
	public int compareTo(YearTerm o) {
		int rv = new Integer(year).compareTo(o.getYear());
		if (rv != 0)
			return rv;
		int t1 = 0, t2 = 0;
		for (int i = 0; i < termTypeOrder.length; i++) {
			if (getTermTypeOrder()[i].equals(termType))
				t1 = i;
			if (getTermTypeOrder()[i].equals(o.getTermType()))
				t2 = i;
		}
		return new Integer(t1).compareTo(t2);
	}

	@Override
	public String getTermName() {
		String rv = (rv = getLongName()) == null ? null : rv.trim();
		if (rv != null && rv.length() > 7
				&& rv.endsWith(" " + Integer.toString(year)))
			return rv.substring(0, rv.length() - 5);
		LOG.warn("Not sure how to extract term name " + rv);
		return rv;
	}

	@Override
	public String getShortName() {
		String rv = (rv = getLongName()) == null ? null : rv.trim();
		if (rv != null && rv.length() > 7
				&& rv.endsWith(" " + Integer.toString(year)))
			return rv.substring(0, 2).toUpperCase() + " " + year;
		LOG.warn("Not sure how to shorten term name " + rv);
		return rv;
	}

	@Override
	public String getLongName() {
		return KsapFrameworkServiceLocator.getTermHelper().getTerm(this)
				.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((termType == null) ? 0 : termType.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultYearTerm other = (DefaultYearTerm) obj;
		if (termType == null) {
			if (other.termType != null)
				return false;
		} else if (!termType.equals(other.termType))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultYearTerm [termType=" + termType + ", year=" + year + "]";
	}

}
