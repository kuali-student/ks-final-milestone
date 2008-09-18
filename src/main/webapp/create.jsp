<html>
<head>
<title>Propose Course</title>
<body>
<h2>Propose Course</h2>
<form action="CourseClientServlet" method="post">
Document User: <input type="text" name="userName" /><br /><br/>

Document Type:
<select name="docType">
	<option value="CourseDocument">CourseDocument</option>
	<option value="SimpleDocument">SimpleDocument</option>
</select>

<br />
<br />
<table>
	<tr>
		<td>Course:</td>
		<td><input type="text" name="course" size="10"></td>
	</tr>
	<tr>
		<td>Course Name:</td>
		<td><input type="text" name="name"></td>
	</tr>
	<tr>
		<td>Course Description:</td>
		<td><textarea name="description" cols="30" rows="5"></textarea></td>
	</tr>
	<tr>
		<td>Level:</td>
		<td><select name="level">
			<option value="UG">Undergraduate</option>
			<option value="GR">Graduate</option>
		</select></td>
	</tr>
	<tr>
		<td>Start Date:</td>
		<td><input type="text" name="startDate"></td>
	</tr>
	<tr>
		<td>End Date:</td>
		<td><input type="text" name="endDate"></td>
	</tr>
	<tr>
		<td>Grading Method:</td>
		<td><select name="gradeMethod">
			<option value="R">Regular</option>
			<option value="A">Audit</option>
		</select></td>
	</tr>
	<tr>
		<td>Credits:</td>
		<td><input type="text" name="credits"></td>
	</tr>
</table>
<br />
Adhoc route document to User: <input type="text" name="routeToUser" /><br />
<input type="submit" value="Route a new Document" name="create" /></form>
</body>
</html>