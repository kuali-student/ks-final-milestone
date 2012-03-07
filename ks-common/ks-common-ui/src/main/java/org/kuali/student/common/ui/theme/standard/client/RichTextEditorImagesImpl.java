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

package org.kuali.student.common.ui.theme.standard.client;

import org.kuali.student.common.ui.client.theme.RichTextEditorImages;

import com.google.gwt.user.client.ui.Image;

@Deprecated
public class RichTextEditorImagesImpl implements RichTextEditorImages{

	@Override
	public Image bold() {
		
		return new Image(KSClientBundle.INSTANCE.bold());
	}

	@Override
	public Image createLink() {
		
		return new Image(KSClientBundle.INSTANCE.createLink());
	}

	@Override
	public Image hr() {
		
		return new Image(KSClientBundle.INSTANCE.hr());
	}

	@Override
	public Image indent() {
		
		return new Image(KSClientBundle.INSTANCE.indent());
	}

	@Override
	public Image insertImage() {
		
		return new Image(KSClientBundle.INSTANCE.insertImage());
	}

	@Override
	public Image italic() {
		
		return new Image(KSClientBundle.INSTANCE.italic());
	}

	@Override
	public Image justifyCenter() {
		
		return new Image(KSClientBundle.INSTANCE.justifyCenter());
	}

	@Override
	public Image justifyLeft() {
		
		return new Image(KSClientBundle.INSTANCE.justifyLeft());
	}

	@Override
	public Image justifyRight() {
		
		return new Image(KSClientBundle.INSTANCE.justifyRight());
	}

	@Override
	public Image ol() {
		
		return new Image(KSClientBundle.INSTANCE.ol());
	}

	@Override
	public Image outdent() {
		
		return new Image(KSClientBundle.INSTANCE.outdent());
	}

	@Override
	public Image popout() {
		
		return new Image(KSClientBundle.INSTANCE.popout());
	}

	@Override
	public Image removeFormat() {
		
		return new Image(KSClientBundle.INSTANCE.removeFormat());
	}

	@Override
	public Image removeLink() {
		
		return new Image(KSClientBundle.INSTANCE.removeLink());
	}

	@Override
	public Image strikeThrough() {
		
		return new Image(KSClientBundle.INSTANCE.strikeThrough());
	}

	@Override
	public Image subscript() {
		
		return new Image(KSClientBundle.INSTANCE.subscript());
	}

	@Override
	public Image superscript() {
		
		return new Image(KSClientBundle.INSTANCE.superscript());
	}

	@Override
	public Image ul() {
		
		return new Image(KSClientBundle.INSTANCE.ul());
	}

	@Override
	public Image underline() {
		
		return new Image(KSClientBundle.INSTANCE.underline());
	}

}
