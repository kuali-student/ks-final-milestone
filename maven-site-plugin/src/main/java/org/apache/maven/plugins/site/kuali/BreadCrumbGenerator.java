package org.apache.maven.plugins.site.kuali;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.maven.project.MavenProject;

/**
 * Generate breadcrumbs based on the project hierarchy
 */
public class BreadCrumbGenerator {
	/**
	 * Recursively build the tree
	 */
	protected DefaultMutableTreeNode getTree(MavenProject project) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(project);
		if (project.getParent() == null) {
			return node;
		}
		DefaultMutableTreeNode parent = getTree(project.getParent());
		node.setParent(parent);
		return node;
	}

	/**
	 * Generate relative pathing
	 */
	protected String getHref(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append("../");
		}
		sb.append("index.html");
		return sb.toString();
	}

	/**
	 * Return true if this project is a child of the top level project
	 */
	protected boolean isRoot(MavenProject project) {
		MavenProject parent = project.getParent();
		if (parent == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Generate a list of breadcrumbs
	 */
	public List getBreadCrumbs(MavenProject project) {
		DefaultMutableTreeNode node = getTree(project);
		Object[] userObjectPath = node.getUserObjectPath();
		List breadCrumbs = new ArrayList();
		for (int i = 0; i < userObjectPath.length; i++) {
			MavenProject mavenProject = (MavenProject) userObjectPath[i];
			// Figure out relative pathing, each project is one directory
			int count = (userObjectPath.length - i) - 1;
			// count = isRoot(mavenProject) ? count++ : count;
			NameHref nh = new NameHref();
			nh.setName(mavenProject.getName());
			nh.setHref(getHref(count));
			breadCrumbs.add(nh);
		}
		return breadCrumbs;
	}
}
