<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Erro</title>
</head>
<body>
<h1>Algo de errado aconteceu.</h1>
<h3>Favor entrar em contato com o suporte do sistema.</h3>


<%

out.print(request.getAttribute("msg"));

%>


</body>
</html>