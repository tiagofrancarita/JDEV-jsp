package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String login;
	private String senha;
	private String confirmaSenha;
	private String email;
	private String dtNascimento;
	private boolean userAdmin;
	private String perfil;
	private String situacao;
	private String fotoUser;
	private String extensaoFotoUser;
	
	
	
	public boolean isNovo() {
		
		if (this.id == null) { /*Insere um novo registro no banco*/
			
			return true; /**/
			
		}else if (this.id !=null && this.id > 0 ) {
			
			return false; /*Atualizar registro*/
		}
		
		return id == null; 
		
	}
	
	public boolean validaSenha() {
		
		if (this.senha == this.confirmaSenha) {
			
			return true;
			
		}else
			return false;
		
	}
	
	
	
	public String getFotoUser() {
		return fotoUser;
	}

	public void setFotoUser(String fotoUser) {
		this.fotoUser = fotoUser;
	}

	public String getExtensaoFotoUser() {
		return extensaoFotoUser;
	}

	public void setExtensaoFotoUser(String extensaoFotoUser) {
		this.extensaoFotoUser = extensaoFotoUser;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}
	
	public boolean getUserAdmin() {
		return userAdmin;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
}
