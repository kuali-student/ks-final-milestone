package org.apache.maven.plugins.site.kuali;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.maven.project.MavenProject;

/**
 * Generate breadcrumbs based on the project hierarchy
 */
public class BreadCrumbGenerator {
	protected DefaultMutableTreeNode getTree(MavenProject project) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(project);
		if (project.getParent() == null) {
			return node;
		}
		DefaultMutableTreeNode parent = getTree(project.getParent());
		node.setParent(parent);
		return node;
	}

	protected String getHref(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append("../");
		}
		sb.append("index.html");
		return sb.toString();
	}

	public List getBreadCrumbs(MavenProject project) {
		DefaultMutableTreeNode node = getTree(project);
		Object[] userObjectPath = node.getUserObjectPath();
		List breadCrumbs = new ArrayList();
		for (int i = 0; i < userObjectPath.length; i++) {
			MavenProject mavenProject = (MavenProject) userObjectPath[i];
			NameHref nh = new NameHref();
			nh.setName(mavenProject.getName());
			nh.setHref(getHref((userObjectPath.length - i) - 1));
			breadCrumbs.add(nh);
		}
		return breadCrumbs;
	}

}
