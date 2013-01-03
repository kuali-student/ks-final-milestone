<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error 403 - Page Forbidden</title>
    <link href="/student/krad/css/global/fss-reset.css" rel="stylesheet" type="text/css">
    <link href="/student/ks-myplan/css/error.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="https://use.typekit.net/jpx3dug.js"></script>
    <script type="text/javascript">try{Typekit.load();}catch(e){}</script>
</head>
<body>
<div id="container">
    <h1>UH-OH...</h1>
    <div id="content">
        <h2>403 Forbidden</h2>
        <p>You do not have permission to perform that trick.</p>
    </div>
    <h3>Try these links to the MyPlan site:</h3>
    <ul id="links">
        <li class="home"><a href="/student/myplan/plan?methodToCall=start&viewId=PlannedCourses-FormView">Plan</a></li>
        <li><a href="/student/myplan/course?methodToCall=start&viewId=CourseSearch-FormView">Find Courses</a></li>
        <li><a href="/student/myplan/audit?methodToCall=audit&viewId=DegreeAudit-FormView">Audit Degree</a></li>
    </ul>
    <div id="more">Still can't find it? Visit our <a href="http://depts.washington.edu/myplan/help-site/">help page</a> or <a href="https://depts.washington.edu/myplan/contact-the-myplan-team/feedback/">let us know</a>.</div>
</div>
</body>

</html>