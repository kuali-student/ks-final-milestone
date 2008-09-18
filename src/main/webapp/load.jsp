<html>
<head>
<title>Load Document</title>
<body>
<h2>Load Document</h2>
<form action="CourseClientServlet" method="post">
<p>
Document ID: <input type="text" name="documentId" value="${param["documentId"]}"/>
Load document as User: <input type="text" name="userName"/><br/>
<input type="submit" value="Load Document" name="load"/>
</p>
</form>
</body>
</html>