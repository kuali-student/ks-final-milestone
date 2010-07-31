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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.util;

import com.google.gwt.dom.client.Element;

// TODO A11y needed to be revamped completely, and the new method is bound to be much faster anyway, so no need to slow things down with the old way

/**
 * Defines methods for adding accessibility features.
 * This class does nothing at the moment (the method bodies are commented out), as this functionality is undergoing an overhaul
 * @author wilj
 *
 */
public class A11y {
	public static void addContextualText(final Element e, final String text) {
		//		addContextualText(e, text, 0, 0);
	}

	public static void addContextualText(final Element e, final String text, final int pauseBefore, final int pauseAfter) {
		//		Element context = DOM.createSpan();
		//		context.setAttribute("isContextualElement", "true");
		//		context.getStyle().setProperty("position", "absolute");
		//		context.getStyle().setProperty("left", "-999em");
		//		context.getStyle().setProperty("width", "1em");
		//		context.getStyle().setProperty("overflow", "hidden");
		//		if (pauseBefore > 0) {
		//			context.getStyle().setProperty("pauseBefore", pauseBefore + "s");	
		//		}
		//		if (pauseAfter > 0) {
		//			context.getStyle().setProperty("pauseAfter", pauseAfter + "s");	
		//		}
		//		context.setInnerText(text);
		//		e.appendChild(context);
	}

	public static void disableTts(final Element e, final boolean recurse) {
		//		e.getStyle().setProperty("speak", "none");
		//		e.setAttribute("alt", "");
		//		e.setTitle("");
		//		if (recurse) {
		//			NodeList<Node> children = e.getChildNodes();
		//			for (int i=0; i<children.getLength(); i++) {
		//				if (Element.is(children.getItem(i))) {
		//					disableTts(Element.as(children.getItem(i)), recurse);
		//				}
		//			}
		//		}
	}

	public static void removeContextualText(final Element e) {
		//		NodeList<Element> spans = e.getElementsByTagName("span");
		//		List<Element> toRemove = new ArrayList<Element>();
		//		for (int i=0; i<spans.getLength(); i++) {
		//			Element span = spans.getItem(i);
		//			String attr = span.getAttribute("isContextualElement");
		//			if (attr != null && attr.equals("true")) {
		//				toRemove.add(span);
		//			}
		//		}
		//		for (Element span : toRemove) {
		//			e.removeChild(span);
		//		}
	}
}
