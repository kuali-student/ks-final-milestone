<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error 404 - Page Not Found</title>
    <link href="/student/krad/css/global/fss-reset.css" rel="stylesheet" type="text/css">
    <link href="/student/ks-myplan/css/error.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="https://use.typekit.net/jpx3dug.js"></script>
    <script type="text/javascript">try {
        Typekit.load();
    } catch (e) {
    }</script>
    <script type="text/javascript" src="/student/krad/plugins/jquery/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="/student/ks-myplan/scripts/myplan.google.js"></script>
    <script type="text/javascript" async="" src="https://ssl.google-analytics.com/ga.js"></script>
</head>
<body>
<div id="container">
    <h1>UH-OH...</h1>

    <div id="content">
        <h2>404 Page not found</h2>

        <p>We are unable to fetch what you&#39;re looking for.</p>

        <p>Please check the URL and try again.</p>
    </div>
    <h3>Try these links to the MyPlan site:</h3>
    <ul id="links">
        <li class="home"><a href="/student/myplan/plan?methodToCall=start&viewId=PlannedCourses-FormView">Plan</a></li>
        <li><a href="/student/myplan/course?methodToCall=start&viewId=CourseSearch-FormView">Find Courses</a></li>
        <li><a href="/student/myplan/audit?methodToCall=audit&viewId=DegreeAudit-FormView">Audit Degree</a></li>
    </ul>
    <div id="more">Completely lost? Visit our <a href="http://depts.washington.edu/myplan/help-site/">help page</a> or
        <a href="https://depts.washington.edu/myplan/contact-the-myplan-team/feedback/">let us know</a>.
    </div>
</div>
</body>

</html>