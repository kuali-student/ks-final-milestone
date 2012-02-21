package org.kuali.student.r1.common.ui.client.widgets;

/**
 * Widgets which can have watermark text must implement this interface
 * 
 * @author Kuali Student Team
 *
 */
public interface HasWatermark {
	public void setWatermarkText(String waterMark);
	public boolean hasWatermark();
	public boolean watermarkShowing();
}
