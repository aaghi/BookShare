<%@ page language="java" %>
<fmt:setBundle basename="LocalStrings"/>
<html>
<head><title><fmt:message key="error_title"/></title>
</head>
<body>
Login Error
<h2><fmt:message key="error_title"/></h2>

<hr>
<fmt:message key="cannot_authenticate"/>. <BR>
<fmt:message key="check_user_password"/>.
<hr>
<fmt:message key="user_principal_is"/>:
<% 
if ( request.getUserPrincipal() == null )
     out.println(request.getUserPrincipal() + "<BR>");
else
     out.println(request.getUserPrincipal().getName() + "<BR>");
%>
<br>
</body>
</html>