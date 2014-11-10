package org.appfuse.webapp.menutree;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.model.OpRegister;

/**
 * @author shuhuan
 * @date 2010-3-4
 * @version 1.0
 *
 */
public class TreeNode {

    private TreeNode parent;
    private List<TreeNode> children = new ArrayList<TreeNode>();
    private String nodeId;
    private String parentId;
    private OpRegister bindData;
    private String cdjb;
    
    public List<TreeNode> getChildren() {
		return children;
	}

	public String getCdjb() {
		return cdjb;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public void setCdjb(String cdjb) {
		this.cdjb = cdjb;
	}


    public void addChild(TreeNode node) {
        children.add(node);
    }
    
	public TreeNode getParent() {
		return parent;
	}
	public String getNodeId() {
		return nodeId;
	}
	public String getParentId() {
		return parentId;
	}
	public OpRegister getBindData() {
		return bindData;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public void setBindData(OpRegister bindData) {
		this.bindData = bindData;
	}

    
}
