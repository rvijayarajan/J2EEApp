<%@ page language="java" errorPage="/error.jsp" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<%if("true".equals(request.getSession().getAttribute("sessionWasInvalid") ) ){ %>
<script type="text/javascript" >
top.window.location.href="frameset.jsp"
</script>
<%
request.getSession().setAttribute("sessionWasInvalid", "false");
}%>

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">


<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/interface.js"></script>

<script type="text/javascript" src="scripts/dock.js"></script>

<link href="styles/style.css" rel="stylesheet" type="text/css"/>


</head>

<body>



<div class="dock" id="dock2">
<div class="dock-container2">
	  <a class="dock-item2" href="index_examp.jsp" target="mainFrame"><span>Home</span><img src="images/home.png" alt="home" /></a> 
	  <a class="dock-item2" href="#"><span>Contact</span><img src="images/email.png" alt="contact" /></a> 
	  <a class="dock-item2" href="#"><span>Portfolio</span><img src="images/portfolio.png" alt="portfolio" /></a> 
	  <a class="dock-item2" href="#"><span>Music</span><img src="images/music.png" alt="music" /></a> 
	  <a class="dock-item2" href="#"><span>Video</span><img src="images/video.png" alt="video" /></a> 
	  <a class="dock-item2" href="#"><span>History</span><img src="images/history.png" alt="history" /></a> 
	  <a class="dock-item2" href="#"><span>Calendar</span><img src="images/calendar.png" alt="calendar" /></a> 
	  <a class="dock-item2" href="#"><span>Links</span><img src="images/link.png" alt="links" /></a> 
	  <a class="dock-item2" href="#"><span>RSS</span><img src="images/rss.png" alt="rss" /></a> 
	  <a class="dock-item2" href="#"><span>RSS2</span><img src="images/rss2.png" alt="rss" /></a> 
</div>
</div>

</body>

</html>