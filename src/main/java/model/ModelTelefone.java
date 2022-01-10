package model;

import java.io.Serializable;

public class ModelTelefone implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String telefoneResidencial;
	private String telefoneCelular;
	
	private ModelLogin usuarioID;
	private ModelLogin usuarioCadID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public ModelLogin getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(ModelLogin usuarioID) {
		this.usuarioID = usuarioID;
	}

	public ModelLogin getUsuarioCadID() {
		return usuarioCadID;
	}

	public void setUsuarioCadID(ModelLogin usuarioCadID) {
		this.usuarioCadID = usuarioCadID;
	}
	
}
