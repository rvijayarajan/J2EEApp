package org.appfuse.webapp.menutree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.appfuse.Resource;
import org.appfuse.model.OpRegister;


public  class Tree {

	private StringBuffer strBuf = new StringBuffer();
	
	private final String defaultUrl = "http://localhost:8081/buptlab";	//�����ʱ�佫��ֵ�Ƶ������ļ���ȥ˵���

	private Map<String, TreeNode> treeNodeMaps = new HashMap<String, TreeNode>();
	List<TreeNode> childNodes= new ArrayList<TreeNode>();
	    
    private void  initMenu(){
    	strBuf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">").append("\n")
    	//.append("<% @ page language=\"java\" errorPage=\"/error.jsp\" pageEncoding=\"utf-8\" contentType=\"text/html;charset=utf-8\" %>").append("\n")
    	.append(" <head>")																														.append("\n")
    	.append("<title> Menu </title>")																									.append("\n")
    	.append("	<style type=\"text/css\">")																						.append("\n")
    	.append("         body{margin:0px;		padding:0px; 	}")																	.append("\n")
    	.append("         	ul,li {	margin:0px;		padding:0px;		list-style-type:none;		font-size:12px;	} ")	.append("\n")
    	.append("         a{	display:block;		text-decoration:none;	}")														.append("\n")
    	.append("         	img{	border:none;	}")																								.append("\n")
    	.append("	       #narBar{width:155px;		border:1px solid #0000ff;	}")												.append("\n")
    	.append("        .item1{	background:url(menuImages/menubar_bg.gif) repeat-x left top;}")			.append("\n")
    	.append("       	.item1 a.title {height:31px;line-height:31px;font-size:14px;	}")									.append("\n")
    	.append("         	.item1 a.title img{	padding:5px 0px;	vertical-align:middle;		border:none;	}")			.append("\n")
    	.append("         .item2 a.title{		height:28px;		line-height:28px;		font-size:12px;	}")					.append("\n")
    	.append("         .item2 a.title img{		padding:5px 0px;		vertical-align:middle;		border:none;	}").append("\n")
    	.append("         .option{		display:block;	}")																					.append("\n")
    	.append("         	.option li a{		height:25px;		line-height:25px;	}")												.append("\n")
    	.append("        .option li a img{		padding:0px;		vertical-align:middle;		border:none;	}")				.append("\n")
    	.append(" </style>")																													.append("\n")
    	.append("<script language=\"javascript\" type=\"text/javascript\" src=\"scripts/jquery.js\"></script>").append("\n")
    	.append("<script language=\"javascript\" type=\"text/javascript\">")											.append("\n");
    	int deep = childNodes.size();
    	if (deep<Resource.getDefaulMenus() )
    		strBuf.append("	$(document).ready(function(){	$(\"#option1\").height(document.documentElement.clientHeight -").append(deep*32).append(");	});").append("\n");
    	else
       		strBuf.append("	$(document).ready(function(){	$(\"#option1\").height(document.documentElement.clientHeight - ").append(Resource.getDefaulMenus()*32).append(");	});").append("\n");
   		
    	strBuf.append("function fold(obj){ ")																										.append("\n")
    	.append("     if (\"block\" == $(obj).next().css(\"display\")){ ")															.append("\n")
    	.append("           $(obj).next().css(\"display\",\"none\");")													.append("\n")
    	.append("      } else { ")																															.append("\n")
    	.append("     		 $(obj).next().css(\"display\",\"block\");")														.append("\n     	}")
    	.append("\n      } ")    	.append("\n")
    	.append("function scroll(index){").append("\n")
    	.append("		for (var i=0; i<$(\".item1\").length ;i++ ){").append("\n")
    	.append("			$(\"#option\" + (i+1)).css(\"display\",\"none\");").append("\n		}").append("\n")
    	.append("		$(\"#option\" + index).css(\"display\",\"block\");").append("\n")
    	;
    	
    	
    	if (deep<Resource.getDefaulMenus())
    		strBuf.append("      $(\"#option\" + index).height(document.documentElement.clientHeight -").append(deep*32).append(");")	.append("\n");
    	else
    		strBuf.append("      $(\"#option\" + index).height(document.documentElement.clientHeight - ").append(Resource.getDefaulMenus()*32).append(");")		.append("\n");
    	
    	strBuf.append(" } ")	.append("\n")
    	.append("</script>").append("\n").append("</head>").append("\n")
    	.append("<body>").append("\n").append("<div>")
    			.append("\n").append("	<ul id=\"narBar\">").append("\n")
    	;
    }
    
    public void reload(Set<OpRegister> nodes) {
              treeNodeMaps.clear();

            List<TreeNode> treeNodes = new ArrayList<TreeNode>(nodes.size());
            for(OpRegister opreg : nodes){
            	TreeNode node = this.transform(opreg);
            	if (node.getBindData().getHasDisp().equals("1")&&node.getBindData().getId()>=Resource.getStartMenuID() ){
            		treeNodes.add(node);
            		treeNodeMaps.put(node.getNodeId(), node);
            	}
            }

            for(TreeNode node : treeNodes){

            	String parentId = node.getParentId();
                if (parentId.equals("0")) {
                	childNodes.add(node);
                } else {
                    TreeNode parent = (TreeNode) treeNodeMaps.get(parentId);
                    if (parent != null) {
                        parent.addChild(node);
                        node.setParent(parent);
                    }
                }
                
            }
            
            initMenu();
            getTreeNodes();
    		strBuf.append("    </ul>").append("\n").append("   </div>").append("\n").append("</body>").append("\n").append("</html>");
    }
    
    private void getTreeNodes() {
    	for (int i=0; i<childNodes.size();i++){
    		TreeNode node = (TreeNode) childNodes.get(i);
			strBuf.append("		<li class=\"item1\"><a href=\"#")	// .append(node.getBindData().getLoc())
				.append("\" target=\"mainFrame\" class=\"title\" onclick=\"scroll(").append(String.valueOf(i+1)).append(");return false;\"> <img src=\"")
				.append(node.getBindData().getIconUrl()==null?"":node.getBindData().getIconUrl() )	.append("\" />")
				.append(node.getBindData().getCdmc()).append("</a>").append("\n")
				.append("			<ul id=\"option").append(String.valueOf(i+1));
			
    		if (i==0){
    			strBuf.append("\" class=\"option\" style=\"display:block;\">").append("\n")		;
    		}		else		{
    			strBuf.append("\" class=\"option\" style=\"display:none;\">").append("\n")		;
    		}
    		
    		getTreeChlidNodes(node.getChildren());
       		strBuf.append("			</ul>").append("\n").append("	</li>").append("\n");

    	}
    }
    
    private void getTreeChlidNodes(List<TreeNode> nodes){
    	
    	for (TreeNode node :nodes){
    		
    		strBuf.append("			<li class=\"item2\"><a href=\"")
    		.append(node.getBindData().getLoc()==null?defaultUrl:node.getBindData().getLoc())
    		.append("\"  target=\"mainFrame\"   class=\"title\" ");
    		
    		if (node.getChildren().size()>0){ // has children menu
    			strBuf.append(" onclick=\"fold(this);return false;\"><img src=\"")
    				.append(node.getBindData().getIconUrl()==null?" ":node.getBindData().getIconUrl() ).append("\" />")
    				.append(node.getBindData().getCdmc()).append("</a> ").append("\n")
    				.append("					<ul class=\"option\" style=\"display:none;\"> ").append("\n");
    			
    			List<TreeNode> lstNodes = node.getChildren();
    			for (TreeNode nodek : lstNodes){

    				strBuf.append("						    <li><a href=\"").append(nodek.getBindData().getLoc()==null?defaultUrl:nodek.getBindData().getLoc() )
    					.append("\"  target=\"mainFrame\" ><img src=\"" )
    					.append(nodek.getBindData().getIconUrl()==null?" ": nodek.getBindData().getIconUrl() ).append("\" />")
    					.append(nodek.getBindData().getCdmc())	.append("</a> </li>").append("\n");
    			}
    			strBuf.append("						</ul>").append("\n").append("			  </li>").append("\n");
    		}else{
    			strBuf.append(" ><img src=\"")
					.append(node.getBindData().getIconUrl()==null?" ": node.getBindData().getIconUrl() ).append("\" />")
					.append(node.getBindData().getCdmc()).append("</a> </li>").append("\n");	
    			
    		}
     		
    	}
    	
    }
    
    public  String getMenuStream(){
    	return strBuf.toString();
    }
    
/*    public void showMenu(JspWriter out) throws Exception{
 
    	out.println(getMenuStream() );
    }*/
    
    protected TreeNode transform(OpRegister opreg){
        TreeNode node = new TreeNode();
        node.setNodeId(opreg.getId().toString());
        if (opreg.getPid()!=null)
        	node.setParentId(opreg.getPid().toString());
        else
        	node.setParentId("0");
        node.setBindData(opreg);
        return node;
    }

}