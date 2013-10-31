package org.kuali.student.ap.schedulebuilder.dto;

import org.kuali.student.ap.schedulebuilder.infc.ScheduleBuildOption;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleBuildOptionInfo", propOrder = { "uniqueId", "selected",
		"lockedIn", "shuffle", "discarded", "_futureElements" })
public class ScheduleBuildOptionInfo implements ScheduleBuildOption {

	private static final long serialVersionUID = 5508638346763031614L;

	@XmlAttribute
	private String uniqueId;

	@XmlAttribute
	private boolean selected;

	@XmlAttribute
	private boolean lockedIn;

	@XmlAttribute
	private int shuffle;

	@XmlAttribute
	private boolean discarded;

	@XmlAnyElement
	private List<?> _futureElements;

	public ScheduleBuildOptionInfo() {
	}

	public ScheduleBuildOptionInfo(ScheduleBuildOption copy) {
		uniqueId = copy.getUniqueId();
		selected = copy.isSelected();
		lockedIn = copy.isLockedIn();
		shuffle = copy.getShuffle();
		discarded = copy.isDiscarded();
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean isLockedIn() {
		return lockedIn;
	}

	public void setLockedIn(boolean lockedIn) {
		this.lockedIn = lockedIn;
	}

	@Override
	public int getShuffle() {
		return shuffle;
	}

	public void setShuffle(int shuffle) {
		this.shuffle = shuffle;
	}

	@Override
	public boolean isDiscarded() {
		return discarded;
	}

	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

}
