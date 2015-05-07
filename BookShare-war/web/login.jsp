<%--
 Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 Use is subject to license terms.
--%>

<%@ page language="java" import="javax.naming.*" %> 
<fmt:setBundle basename="LocalStrings"/>
<html>
<head><title><fmt:message key="title"/></title>
</head>

<h1><b><center></center></b></h1>
<h2> <fmt:message key="login_page"/> </h2>
<BR>

<fmt:message key="please_login"/>

<BR>
<HR>
<FORM ACTION="j_security_check" METHOD=POST>
<fmt:message value="username"/>: <INPUT TYPE="text" NAME="j_username" VALUE=""> <BR>
<fmt:message value="password"/>: <INPUT TYPE="password" NAME="j_password" VALUE=""> <BR>
<BR>
<INPUT TYPE="submit" value="Login"> <INPUT TYPE="reset" value="Clear">
</FORM>
</html>
