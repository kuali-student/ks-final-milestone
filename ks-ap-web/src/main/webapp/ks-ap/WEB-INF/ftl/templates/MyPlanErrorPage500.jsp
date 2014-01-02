<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error 500 - Internal Server Error</title>
    <link href="/student/krad/css/global/fss-reset.css" rel="stylesheet" type="text/css">
    <link href="/student/ks-ap/css/error.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/student/krad/plugins/jquery/jquery-1.7.2.js"></script>
</head>
<body>
<div id="container">
    <h1>UH-OH...</h1>

    <div id="content">
        <h2>500 Internal Server Error</h2>

        <p>It looks like our server is playing dead.</p>

        <p>Don&#39;t worry-- we&#39;re working on the problem and will have it up and running again soon.</p>
    </div>
    <h3>Try these links to the MyPlan site:</h3>
    <ul id="links">
        <li class="home"><a href="/kr-krad/plan?methodToCall=start&viewId=PlannedCourses-FormView">Plan</a></li>
        <li><a href="/kr-krad/course?methodToCall=start&viewId=CourseSearch-FormView">Find Courses</a></li>
        <li><a href="/kr-krad/audit?methodToCall=audit&viewId=DegreeAudit-FormView">Audit Degree</a></li>
    </ul>
    <div id="more">Completely lost? Visit our <a href="http://depts.washington.edu/myplan/help-site/">help page</a> or
        <a href="https://depts.washington.edu/myplan/contact-the-myplan-team/feedback/">let us know</a>.
    </div>
</div>
</body>

</html>