<html>
<head>
<title>Login</title>
<script type="text/javascript">
    function setSubmitUrl(form){
        var hash = unescape(self.document.location.hash.substring(1));
        form.action = "j_spring_security_check#" + hash;
        return true;
    }
</script>
<style type="text/css">
    body { background-color:#a02919; font-size:80%; margin:0; }
    div.body { background-image: url("${ConfigProperties.application.url}/rice-portal/images/os-guy.gif");
        background-repeat:no-repeat; padding-top:3em; }
    div.build { color:#dfdda9; float:right; margin:0.3em; }
    table#login { background-color:#dfdda9; border:0.5em solid #fffdd8; font-size:inherit;
        border-radius:10px; -moz-border-radius:10px; -webkit-border-radius:10px; }
    table#login th { color:#a02919; font-size:2em; padding-top:0.5em; padding-bottom:0.5em; }
    table#login td.rightTd { padding-right:1.2em; }
    table#login td.leftTd { padding-left:1.2em; text-align:right; }
    table#login td.buttonRow { padding-top:1em; padding-bottom:0.6em; }
</style>
</head>
<body onLoad="document.loginForm.j_username.focus();">

<div class="build">${ConfigProperties.version} (${ConfigProperties.datasource.ojb.platform})</div>

<form name="loginForm" method="post" onSubmit="return setSubmitUrl(this);">

    <div class="body">
        <table id="login" cellspacing="0" cellpadding="2" align="center">
            <tbody>
            <tr>
                <th colspan="2">&nbsp;Kuali Student Login&nbsp;</th>
            </tr>
            <tr>
                <td class="leftTd">
                    <label for="j_username">Username:</label>
                </td>
                <td class="rightTd">
                    <input type="text" name="j_username" id="j_username" size="10"/>
                </td>
                <br/>
            </tr>
            <!-- password is not required yet - but it's needed for testing -->
            <tr>
                <td class="leftTd">
                    <label for="j_password">Password:</label>
                </td>
                <td class="rightTd">
                    <input type="password" name="j_password" id="j_password" size="10"/>
                </td>
                <br/>
            </tr>
            <tr>
                <td class="buttonRow" align="center" colspan="2">
                    <input type="submit" value="Login"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

</form>
</body>
</html>