<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags BOOTSTRAP -->
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-uWxY/CJNBR+1zjPWmfnSnVxwRheevXITnMqoEIeG1LJrdI0GlVs/9cVSyPYXdcSF" crossorigin="anonymous">

<title>Curso JSP</title>

<style type="text/css">

form{
position: absolute;
top: 40%;
left: 33%;
right: 33%;
}

h5{
position: absolute;
top: 35%;
left: 40%;

}

h4{
position: absolute;
top: 1%;
right: 1%;
color: black;
}

</style>

</head>
<body>
<h5>Bem-vindo ao Curso JSP</h5>

<% 

%>

<h4><span class="badge rounded-pill bg-danger">${msg}</h4>

<form action="<%=request.getContextPath() %>/ServletLogin" method="post" class="row g-3">
	<input type="hidden" value="<%=request.getParameter("url")  %>" name="url">
	
	<div class="col-md-6">
		<label for="validationServerUsername" class="form-label">Username</label>
	<div class="input-group has-validation">
		<span class="input-group-text" id="inputGroupPrepend">@</span> 
		<input type="text" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required name="login">
	<div id="validationServerUsernameFeedback" class="invalid-feedback">Favor informar o username</div>
	</div>
	</div>
	
	<div class="col-md-6">
		<label for="validationServerUsername" class="form-label">Senha</label>
	<div class="input-group has-validation">
		<span class="input-group-text" id="inputGroupPrepend">*</span> 
		<input type="password" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required name="senha">
	<div id="validationServerUsernameFeedback" class="invalid-feedback">Favor informar o username</div>
	</div>
	</div>

		<div class="col-12">
			<button type="submit" class="btn btn-primary">Entrar</button>
		</div>

  
</form>



<!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-kQtW33rZJAHjgefvhyyzcGF3C5TFyBQBA13V1RKPf4uH+bwyzQxZ6CmMZHmNBEfJ" crossorigin="anonymous"></script>

<script type="text/javascript">

//Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()



</script>
</body>
</html>