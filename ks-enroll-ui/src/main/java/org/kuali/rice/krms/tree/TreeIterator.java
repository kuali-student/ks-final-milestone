package org.kuali.rice.krms.tree;

import org.kuali.rice.krms.api.repository.NaturalLanguageTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeIterator implements Serializable, Iterator<String> {

    private Stack<String> nl;

    public TreeIterator(NaturalLanguageTree tree){
        nl = new Stack<String>();
        this.addToStack(tree);
    }

    private void addToStack(NaturalLanguageTree tree){
        if (tree == null){
            return;
        }

        nl.push(tree.getNaturalLanguage());
        if (tree.getChildren() != null){
            for (NaturalLanguageTree child : tree.getChildren()){
                addToStack(child);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !nl.empty();
    }

    @Override
    public String next() {

        return nl.pop();
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
