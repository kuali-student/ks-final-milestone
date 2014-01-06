<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>
        Kuali :: Simple Performance Test Page
    </title>
    <link href="/ks-with-rice-bundled-dev/themes/ksboot/stylesheets/ksboot.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/themes/ksboot/stylesheets/fontello.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/jqueryUI/jquery-ui-1.9.2.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/menu/tabs.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/jgrowl/jquery.jgrowl.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/textpopout/popoutTextarea.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/datatables/TableTools.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/prettify/prettify.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/tooltip/jquery.bubblepopup.v2.3.1.css" rel="stylesheet"
          type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/plugins/tooltip/ks.jquery.bubblepopup.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/themes/ksboot/stylesheets/enroll.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/themes/ksboot/stylesheets/link-tabs.css" rel="stylesheet" type="text/css"/>
    <link href="/ks-with-rice-bundled-dev/ks-common-ksink/css/kitchensink.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="/ks-with-rice-bundled-dev/plugins/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="/ks-with-rice-bundled-dev/themes/ksboot/scripts/jquery.sticky.js"></script>
    <script type="text/javascript"
            src="/ks-with-rice-bundled-dev/themes/bootstrap/scripts/bootstrap.js"></script>
</head>
<body>
<div id="Uif-Application" class="uif-application">
<!-- APPLICATION HEADER -->
<div id="Uif-ApplicationHeader-Wrapper">
    <script type="text/javascript">
        function logout() {
            var url = "${appUrl}/backdoorlogin.do?methodToCall=logout";
            window.location = url;
        }
    </script>
    <div class="ks-uif-viewHeader-container navbar-inverse navbar">
        <img class="ks-logo-image" title="Kuali Student" src="../themes/ksboot/images/header/logo_kuali.png">
        <span class="ks-header-student">Student</span>

        <div class="header-right-group">
            <ul class="ks-header-list nav pull-right">
                <li class="ks-header-action-list"><a
                        href="${appUrl}/kew/ActionList.do">Action List</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-user icon-white"></i>
                        admin
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#" onclick="logout();">Logout</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <!-- Backdoor info (here to inherit stickyness with the header, if set) -->
</div>
<div id="Uif-BreadcrumbWrapper">
    <ol class="uif-breadcrumbs" role="navigation">
        <li>
            <a id="u16" data-role="breadcrumb" href="${appUrl}/cr-performance-test/action">Simple
                Performance Test Page</a>
        </li>
        <li>
            <span data-role="breadcrumb" id="u32">Table Generation Test</span>
        </li>
    </ol>
</div>
<script>
    $(document).ready(function(){
        $("#stickyme").sticky({topSpacing:0});
    });
</script>
<div id="Uif-ViewHeaderUpdate">
    <div id="stickyme" style="width:100%" class="uif-viewHeader-contentWrapper"><!--class="navbar navbar-fixed-top"-->
        <div id="u19" class="ks-unified-header">
            <h1 class="uif-headerText">
<span class="uif-headerText-span">
Simple Performance Test Page
</span>
<span class="uif-supportTitle-wrapper">
<span id="u22_span" class="uif-viewHeader-supportTitle">
Table Generation Test </span>
</span>
            </h1>
        </div>
    </div>
</div>
<div id="Uif-ViewContentWrapper" class="uif-viewContentWrapper">
<div id="Uif-PageContentWrapper" class="uif-pageContentWrapper">

    <form id="kualiForm" action="${appUrl}/cr-performance-test/action"
          method="get" accept-charset="UTF-8">
        <!-- PAGE CONTENT -->

        <div id="u30" class="uif-page">
            <div class="clearfix uif-header-contentWrapper">
                <div id="u33" class="uif-pageHeader clearfix uif-hideHeaderText">
                    <h2 class="uif-headerText">
<span class="uif-headerText-span">
Table Generation Test
</span>
                    </h2>
                </div>
            </div>
            <div id="u31_boxLayout" class="uif-verticalBoxLayout clearfix">
                <div id="KS-KitchenSink-TextField-Perf-Section"
                     class="uif-boxSection uif-boxLayoutVerticalItem clearfix">
                    <div id="u43_boxLayout" class="uif-verticalBoxLayout clearfix">
                        <div id="KS-KitchenSink-TextField-Input-Section"
                             class="uif-boxSection uif-boxLayoutVerticalItem clearfix">
                            <div id="u49_boxLayout" class="uif-horizontalBoxLayout clearfix">
                                <div id="KS-KitchenSink-TextField-Perf"
                                     class="uif-inputField uif-boxLayoutHorizontalItem">
<span id="KS-KitchenSink-TextField-Perf_label_span" class="uif-label">
<label id="KS-KitchenSink-TextField-Perf_label" for="KS-KitchenSink-TextField-Perf_control">
    Course Search
</label>
<span id="u56_span" class="uif-requiredMessage" style=";display: none;">
* </span>
</span>
                                <span id="KS-KitchenSink-TextField-Perf_instructional_span"
                                      class="uif-instructionalMessage">
Please enter a partial course code to search </span>
                                    <input id="KS-KitchenSink-TextField-Perf_control" name="inputOne"
                                           value="${inputOne}"
                                           size="25" class="uif-textControl"
                                           tabindex="0"
                                           maxlength="8">
                                </div>
                                <button id="generate_button"
                                        class="btn btn-primary btn btn-primary uif-boxLayoutHorizontalItem">
                                    Generate Table
                                </button>
                            </div>
                        </div>

                        <table id="u63" class="table table-condensed table-bordered uif-tableCollectionLayout">
                            <thead>
                            <tr>
                                <th scope="col">
<span id="u83_c1_span" class="infoline">
<label id="u83_c1" for="">
    courseOfferingId
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c2_span" class="infoline">
<label id="u83_c2" for="">
    courseOfferingCode
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c3_span" class="infoline">
<label id="u83_c3" for="">
    courseOfferingDesc
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c4_span" class="infoline">
<label id="u83_c4" for="">
    courseOfferingCreditOptionDisplay
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c5_span" class="infoline">
<label id="u83_c5" for="">
    courseOfferingGradingOptionDisplay
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c5_span" class="infoline">
<label id="u83_c5" for="">
    studentSelectablePassFail
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c5_span" class="infoline">
<label id="u83_c5" for="">
    auditCourse
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c5_span" class="infoline">
<label id="u83_c5" for="">
    honorsCourse
</label>
</span>
                                </th>
                                <th scope="col">
<span id="u83_c6_span" class="infoline">
<label id="u83_c6" for="">
    Actions
</label>
</span>
                                </th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list courseResults as row>
                            <tr>
                                <td role="presentation">
                                    <div id="u110_line${row_index}" class="uif-field">
<span id="u110_line${row_index}_control" class="uif-readOnlyContent">
${row.courseOfferingId}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u115_line${row_index}" class="uif-field">
<span id="u115_line${row_index}_control" class="uif-readOnlyContent">
${row.courseOfferingCode}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u120_line${row_index}" class="uif-field">
<span id="u120_line${row_index}_control" class="uif-readOnlyContent">
${row.courseOfferingDesc}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u125_line${row_index}" class="uif-field">
<span id="u125_line${row_index}_control" class="uif-readOnlyContent">
${row.courseOfferingCreditOptionDisplay}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u130_line${row_index}" class="uif-field">
<span id="u130_line${row_index}_control" class="uif-readOnlyContent">
${row.courseOfferingGradingOptionDisplay}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u130_line${row_index}" class="uif-field">
<span id="u130_line${row_index}_control" class="uif-readOnlyContent">
${row.studentSelectablePassFail?c}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u130_line${row_index}" class="uif-field">
<span id="u130_line${row_index}_control" class="uif-readOnlyContent">
${row.auditCourse?c}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u130_line${row_index}" class="uif-field">
<span id="u130_line${row_index}_control" class="uif-readOnlyContent">
${row.honorsCourse?c}
</span>
                                    </div>
                                </td>
                                <td role="presentation">
                                    <div id="u85_line${row_index}" class="uif-horizontalFieldGroup">
                                        <fieldset aria-labelledby="u85_line${row_index}_label"
                                                  id="u85_line${row_index}_fieldset">
                                            <legend style="display: none">Actions</legend>

                                            <div id="u89_line${row_index}_boxLayout"
                                                 class="uif-horizontalBoxLayout clearfix">
                                                <button id="KS-KitchenSink-Performance-Collection_del_line${row_index}"
                                                        class="btn btn-small uif-smallActionButton uif-boxLayoutHorizontalItem"
                                                        onclick="$(this).closest('tr').remove()">
                                                    delete
                                                </button>
                                            </div>
                                        </fieldset>
                                    </div>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</div>
</div>
<!-- custom script for the view -->
</body>
</html>
