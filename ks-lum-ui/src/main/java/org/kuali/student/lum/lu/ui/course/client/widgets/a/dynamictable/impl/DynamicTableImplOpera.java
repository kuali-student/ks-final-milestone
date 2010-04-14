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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.impl;

import com.google.gwt.user.client.Timer;

/**
 * Opera specific implementation of DynamicTable
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class DynamicTableImplOpera<T> extends DynamicTableImplDefault<T> {

	private Timer keyDownTimer = null;

	@Override
	protected int getRelativeMouseWheelVelocity(final int velocity) {
		return velocity / 4;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.impl.DynamicTableImplDefault#handleKeyDown(int)
	 */
	@Override
	protected boolean handleKeyDown(final int keyCode) {
		final boolean result = super.handleKeyDown(keyCode);
		if (result) {
			if (keyDownTimer != null) {
				keyDownTimer.cancel();
			}
			keyDownTimer = new Timer() {
				@Override
				public void run() {
					repeatKeyDown(keyCode);
				}
			};
			keyDownTimer.scheduleRepeating(100);

		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.impl.DynamicTableImplDefault#handleKeyUp(int)
	 */
	@Override
	protected boolean handleKeyUp(final int keyCode) {
		super.handleKeyUp(keyCode);
		if (keyDownTimer != null) {
			keyDownTimer.cancel();
			keyDownTimer = null;
		}
		return false;
	}

	private void repeatKeyDown(final int keyCode) {
		super.handleKeyDown(keyCode);
	}

}
