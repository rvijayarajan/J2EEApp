<%@ page import= "java.io.*,java.net.*" %><%
    try{
        //ȡ�������·��
        String fn = "attachment; filename=a.xls";  //�����ΪUniCode������ַ���
        System.out.println(fn);
        //�ѱ��⡢����д���������
        response.setHeader("Content-Disposition", new String(fn.getBytes("GB2312"),
                                        "ISO-8859-1"));
        createOutput( response.getOutputStream(),"c:\\ltf.xls");
    }catch( Exception ee ){
        ee.printStackTrace();
    }
%><%!
public void createOutput( OutputStream out,String realpath ) throws IOException {
    int b;
    BufferedInputStream  m_input =
                new BufferedInputStream( new  FileInputStream(realpath) );
    while( (b = m_input.read()) != -1 ){
        out.write(b);
    }
    m_input.close();
    out.flush();
    out.close();
}
%>
<%--
		// �õ��ļ����ֺ�·��
		String filename = request.getParameter("zipfilename");
		//String jsppath = "";             getServletConfig().getServletContext().getRealPath("") + 
 		String filepath = "c:\\ltf.xls";        //jsppath.substring(0,jsppath.lastIndexOf("")) + "reports\\";
		System.out.println("---------------=================" + filepath);
		java.io.FileInputStream fileInputStream = null;
		//out.println(filepath);
		// ������Ӧͷ�����ر�����ļ���
		response.setHeader("Content-Disposition",
		"attachment; filename=" + "a.xls");
		response.setContentType("application/msexcel");
	
		// ��ָ���ļ�������Ϣ
		try{
		fileInputStream =
			new java.io.FileInputStream(filepath);
		
		// д������Ϣ
		int i;
		while ((i=fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.close();
		System.out.println("---OK---------------");
		
		}catch(Exception ee){
		  ee.printStackTrace();
		}
--%>
