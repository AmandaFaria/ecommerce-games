package br.org.generation.lojagames.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id		//chave primária de auto-incremento
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Por favor, digite o nome do produto")
	@Size(min = 5, max = 255, message = "Mínimo de 5 caractéres necessários e máximo de 255 caractéres necessários")
	private String nomeproduto;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING) 
	@NotNull(message = "Por favor, digite um preço para o produto")
	@Positive(message = "O preço precisa ser um valor positivo!")
	private BigDecimal preco;
	
	@NotNull(message = "Por favor, digite uma descrição para o produto")
	@Size(min = 10, max = 1000, message = "Mínimo de 10 caractéres necessários e máximo de 1000 caractéres necessários")
	private String descricao;	
	
	@NotNull(message = "Por favor, insira uma foto do produto")
	private String imagem;
	
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeproduto() {
		return nomeproduto;
	}

	public void setNomeproduto(String nomeproduto) {
		this.nomeproduto = nomeproduto;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
