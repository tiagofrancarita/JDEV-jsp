<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<! -- Página referente ao cabeçalho --!>
<jsp:include page="head.jsp"></jsp:include>

<body> 
 
	<! -- Página referente ao theme-load --!>
	<jsp:include page="theme-load.jsp"></jsp:include>

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">

						<!-- Page-header start -->

						<jsp:include page="page-header.jsp"></jsp:include>
 
						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">

									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
													<h3 class="sub-title" align="center">Cadastro de Usuários</h3>
													
														<form class="form-material" action="<%=request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
														
															<input type="hidden" id="acao" name="acao" value="">
														
															 <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" placeholder="id"  readonly="readonly" value="${modologin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Código:</label>
                                                            </div>
														
															<div class="form-group form-default form-static-label">
														       <input type="text" name="nome" id="nome" class="form-control" placeholder="Nome" required="required" autocomplete="off" value="${modologin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
														
															<div class="form-group form-default form-static-label">
														       <input type="text" name="login" id="login" class="form-control" placeholder="Username" required="required" autocomplete="off" value="${modologin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Username:</label>
                                                            </div>
															
															<div class="form-group form-default form-static-label">
														       <input type="password" name="senha" id="senha" class="form-control" placeholder="Senha" required="required" autocomplete="off" value="${modologin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha:</label>
                                                            </div>
															
															<div class="form-group form-default form-static-label">
														       <input type="password" name="confirmaSenha" id="confirmaSenha" class="form-control" placeholder="Confirma Senha" required="required" autocomplete="off" value="${modologin.confirmaSenha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Confirma Senha:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
														        <input type="email" name="email" id="email" class="form-control" placeholder="E-mail" required="required" autocomplete="off" value="${modologin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">E-mail:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
														        <input type="date" name="dtNascimento" id="dtNascimento" class="form-control" placeholder="Data de Nascimento" required="required" autocomplete="off" value="${modologin.dtNascimento}" maxlength="10">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data de Nascimento:</label>
                                                            </div>
                                                            
                                                           	<div class="card-block" align="center">
													            <!-- button Rounded -->
													            <button type="button" class="btn btn-primary btn-round waves-effect waves-light" onclick="limparform();">Novo</button>
													            <button type="submit" class="btn btn-success btn-round waves-effect waves-light">Salvar</button>
													            <!-- Button trigger modal -->
																<button type="button" class="btn btn-info btn-round waves-effect waves-light" data-toggle="modal" data-target="#modalUsuario">Pesquisar</button>
													            <button type="button" class="btn btn-danger btn-round waves-effect waves-light" onclick="criarDelete();">Excluir</button>
													           <!--   <button class="btn btn-inverse btn-round waves-effect waves-light">Cancelar</button>-->
											        		</div>
															
															
														</form>

													</div>
												</div>
											</div>
										</div>
										  <span>${msg}</span>
									<!--<span id="msg">${msg}</span>-->
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="javascriptfile.jsp"></jsp:include>
	
	<!-- Modal -->
<div class="modal fade" id="modalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
     <div class="modal-body">
     
	<div class="input-group mb-3">
	  <input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
	  <div class="input-group-append">
	    <button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
	  </div>
	</div>
	
   </div>
   
   <div style="height: 300px; overflow: scroll;">
   <table class="table" id="tabelaresultados">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Nome</th>
      <th scope="col">E-mail</th>
      <th scope="col">Ação</th>
    </tr>
  </thead>
  <tbody>
    <!-- Preenchido conforme o JS -->
  </tbody>
</table>

</div>
  <span id="totalResultado"></span> 
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
    </div>
  </div>
</div>
	
	
	<script type="text/javascript">
	
	/**
	function deleteAjax() {
		if(confirm('Deseja realmente excluir o usuário(a) ?')){
			var urlAction = document.getElementById('formUser').action;
			var idUser = document.getElementById('id').value;
			$.ajax({
				method: "get",
				url: urlAction,
				data: "id=" + idUser + '&acao=deletarAjax',
				succes: function(response){
					 limparform();
					 document.getElementById('msg').textContent = response;
				}
			}).fail(function(xhr,status,errorThrown){
				alert('Erro ao deletar usuário por id' + xhr.responseText);
			});
		}
	}*/
	
	function verEditar(id) {
		
		var urlAction = document.getElementById('formUser').action;
		window.location.href = urlAction + '?acao=buscarEditar&id='+id;

		}

		function buscarUsuario() {

			var nomeBusca = document.getElementById('nomeBusca').value;

			if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') { /*Validando que tem que ter valor pra buscar no banco*/

				var urlAction = document.getElementById('formUser').action;

				$
						.ajax(
								{

									method : "get",
									url : urlAction,
									data : "nomeBusca=" + nomeBusca
											+ '&acao=buscarUserAjax',
									success : function(response) {

										var json = JSON.parse(response);

										$('#tabelaresultados > tbody > tr').remove;
										//
										for (var p = 0; p < json.length; p++) {
											$('#tabelaresultados > tbody')
													.append(
															'<tr> <td>'
																	+ json[p].id
																	+ '</td> <td>'
																	+ json[p].nome
																	+ '</td> <td>'
																	+ json[p].email
																	+ '</td> <td><button type="button" class="btn btn-info" onclick="verEditar('
																	+ json[p].id
																	+ ');">Ver</button></td> </tr>');
										}

										document
												.getElementById('totalResultado').textContent = 'Usuários:'
												+ json.length;

									}

								}).fail(
								function(xhr, status, errorThrown) {
									alert('Erro ao buscar usuário por nome: '
											+ xhr.responseText);
								});

			}

		}

		function criarDelete() {

			if (confirm('Deseja realmente excluir o usuário(a) ?')) {

				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		function limparform() {

			//document.getElementById("formUser")..reset;

			var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do formulario*/
			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	</script>
</body>
</html>