package org.kuali.rice.krad.uif.layout.extension;

import java.util.List;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.collections.LineBuilderContext;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.TableLayoutManagerBase;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Layout manager for controlling and optimizing section details presentation.
 */
public class CourseSectionDetailsLayoutManager extends TableLayoutManagerBase {

	private static final long serialVersionUID = 3056313875988089648L;

	private static final Logger LOG = LoggerFactory.getLogger(CourseSectionDetailsLayoutManager.class);

	@Override
	public void buildLine(LineBuilderContext lineBuilderContext) {
        int lineIndex = lineBuilderContext.getLineIndex();
        Object currentLine = lineBuilderContext.getCurrentLine();

		super.buildLine(lineBuilderContext);

		// This logic replaces onDocumentReadyScript entries previously in
		// CourseDetailsUI.xml by assigning the correct CSS class to table rows
		// at rendering time, rather than relying on the browser to query for
		// and assign classes on document ready.
		if (lineIndex != -1 && (currentLine instanceof ActivityOfferingItem)) {
			ActivityOfferingItem aoi = (ActivityOfferingItem) currentLine;
			List<String> rowCss = getRowCssClasses();
			rowCss.remove(rowCss.size() - 1); // super adds a row, remove it
			StringBuilder r1css = new StringBuilder();
			r1css.append("row");
			r1css.append(aoi.isPrimary() ? " primary" : " secondary");
			boolean offered = LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY
					.equals(aoi.getStateKey());
			StringBuilder r2css = offered ? new StringBuilder() : null;
			if (offered && !aoi.isPrimary())
				r2css.append("collapsible");
			if (aoi.getPlanItemId() != null) {
				r1css.append(r1css.length() == 0 ? " " : "").append(
						"ksap-section-planned");
				if (offered)
					r2css.append(r2css.length() == 0 ? " " : "").append(
							"ksap-section-planned");
			}
			rowCss.add(r1css.toString());
			if (offered) {
				rowCss.add(r2css.toString());
				rowCss.add(r2css.toString());
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("AO luiId {} lineIndex = {} css(1) {} css(2,3) {}",
                        aoi.getLuiId(), lineIndex, r1css, r2css);
            }
		}
	}

	/**
	 * This method is written to override the default colSpan calculation done
	 * in TableLayoutManager. If this LayoutManager is used, it will rely on the
	 * row span and col spans set in the XML and not try to calculate the last
	 * column col span on its own
	 * 
	 * Temporary fix for KULRICE-9215
	 * 
	 * @Author kmuthu Date: 3/28/13
	 */
	@Override
	protected int calculateNumberOfRows(List<? extends Field> items) {
		int rowCount = 0;

		// check flag that indicates only one row should be created
		if (isSuppressLineWrapping()) {
			return 1;
		}

		if (items.size() % getNumberOfDataColumns() > 0) {
			rowCount = ((items.size() / getNumberOfDataColumns()) + 1);
		} else {
			rowCount = items.size() / getNumberOfDataColumns();
		}
		return rowCount;
	}
}
