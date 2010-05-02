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

/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.download;

import java.util.LinkedList;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author wilj
 *
 */
public class ExportDownloaderDefault implements ExportDownloader {
	private final LinkedList<Frame> frames = new LinkedList<Frame>();

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.download.ExportDownloader#download(java.lang.String)
	 */
	public void download(final String url) {
		final Frame f = new Frame(url);
		f.setVisible(false);
		RootPanel.get().add(f);
		frames.add(f);
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				// in case the user is fast and the network is slow, give it a bit before removing the frame
				while (frames.size() > 3) {
					RootPanel.get().remove(frames.removeFirst());
				}
			}
		});
	}
}
