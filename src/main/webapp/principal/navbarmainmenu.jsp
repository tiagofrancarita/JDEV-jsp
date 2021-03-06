<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set scope="session" var="perfil"
	value='<%=request.getSession().getAttribute("perfil").toString()%>'></c:set>

<!-- navbarmainmenu.jsp -->

<nav class="pcoded-navbar">
	<div class="sidebar_toggle">
		<a href="#"><i class="icon-close icons"></i></a>
	</div>
	<div class="pcoded-inner-navbar main-menu">
		<div class="">
			<div class="main-menu-header">
			
				<c:if test="${imagemUser != '' && imagemUser != null}">
					<img class="img-80 img-radius" src="${imagemUser}" alt="User-Profile-Image">
				</c:if>
				
				<c:if test="${imagemUser == '' || imagemUser == null}">
					<img class="img-80 img-radius" src="<%=request.getContextPath()%>/assets/images/sem-foto.png" alt="User-Profile-Image">
				</c:if>
				
				
				<div class="user-details">
					<span id="more-details"><%=session.getAttribute("nomeUsuario")%><i
						class="fa fa-caret-down"></i></span> <span id="more-perfil"><%=session.getAttribute("perfil")%><i
						class="fa fa-caret"></i></span>
				</div>
			</div>

			<div class="main-menu-content">
				<ul>
					<li class="more-details"><a href="user-profile.html"><i
							class="ti-user"></i>Ver Perfil</a> <a href="#!"><i
							class="ti-settings"></i>Configurações</a> <a
						href="<%=request.getContextPath()%>/ServletLogin?acao=logout"><i
							class="ti-layout-sidebar-left"></i>Sair</a></li>
				</ul>
			</div>
		</div>

		<div class="pcoded-navigation-label"
			data-i18n="nav.category.navigation">Inicio</div>
		<ul class="pcoded-item pcoded-left-item">
			<li class="active"><a
				href="<%=request.getContextPath()%>/principal/principal.jsp"
				class="waves-effect waves-dark"> <span class="pcoded-micon"><i
						class="ti-home"></i><b>Inicio</b></span> <span class="pcoded-mtext"
					data-i18n="nav.dash.main">Inicio</span> <span class="pcoded-mcaret"></span>
			</a></li>
			<li class="pcoded-hasmenu"><a href="javascript:void(0)"
				class="waves-effect waves-dark"> <span class="pcoded-micon"><i
						class="ti-layout-grid2-alt"></i></span> <span class="pcoded-mtext"
					data-i18n="nav.basic-components.main">Cadastros</span> <span
					class="pcoded-mcaret"></span>
			</a>

				<ul class="pcoded-submenu">
					<c:if test="${perfil == 'ADMIN'}">
						<li class=" "><a
							href="<%=request.getContextPath()%>/ServletUsuarioController?acao=listarUser"
							class="waves-effect waves-dark"> <span class="pcoded-micon"><i
									class="ti-angle-right"></i></span> <span class="pcoded-mtext"
								data-i18n="nav.basic-components.alert">Usuário</span> <span
								class="pcoded-mcaret"></span>
						</a></li>
					</c:if>

					<c:if test="${perfil == 'ADMIN'}">
						<li class=" "><a
							href="<%=request.getContextPath()%>/ServletUsuarioController?acao=listarUser"
							class="waves-effect waves-dark"> <span class="pcoded-micon"><i
									class="ti-angle-right"></i></span> <span class="pcoded-mtext"
								data-i18n="nav.basic-components.alert">Telefone</span> <span
								class="pcoded-mcaret"></span>
						</a></li>
					</c:if>

				</ul>
			<li class="pcoded-hasmenu"><a href="javascript:void(0)"
				class="waves-effect waves-dark"> <span class="pcoded-micon"><i
						class="ti-layout-grid2-alt"></i></span> <span class="pcoded-mtext"
					data-i18n="nav.basic-components.main">Relatórios</span> <span
					class="pcoded-mcaret"></span>
			</a>

				<ul class="pcoded-submenu">
					<c:if test="${perfil == 'ADMIN'}">
						<li class=" "><a
							href="<%=request.getContextPath()%>/ServletUsuarioController?acao=listarUser"
							class="waves-effect waves-dark"> <span class="pcoded-micon"><i
									class="ti-angle-right"></i></span> <span class="pcoded-mtext"
								data-i18n="nav.basic-components.alert">Usuário</span> <span
								class="pcoded-mcaret"></span>
						</a></li>
					</c:if>

					<c:if test="${perfil == 'ADMIN'}">
						<li class=" "><a
							href="<%=request.getContextPath()%>/ServletUsuarioController?acao=listarUser"
							class="waves-effect waves-dark"> <span class="pcoded-micon"><i
									class="ti-angle-right"></i></span> <span class="pcoded-mtext"
								data-i18n="nav.basic-components.alert">Telefone</span> <span
								class="pcoded-mcaret"></span>
						</a></li>
					</c:if>

				</ul>
		</ul>

	</div>
</nav>