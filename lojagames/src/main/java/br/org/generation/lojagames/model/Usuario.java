package br.org.generation.lojagames.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="tb_usuarios")
public class Usuario {

	@Id			
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private long id;
	
	@NotBlank(message = "O campo nome é obrigatório!")	
	private String nome;
	
	@NotBlank(message = "O campo usuário é obrigatório!")	
	@Email(message="O campo email deve ser um email válido!")
	private String usuario;
	
	@NotBlank(message = "O campo senha é obrigatório!")	
	@Size(min = 8, message = "Min de caractéres 8")
	private String senha;
	
	@Column(name = "data_nascimento")  //troca o nome da tabela
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "O campor data de nascimento é obrigatório!")
	private LocalDate dataNascimento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
