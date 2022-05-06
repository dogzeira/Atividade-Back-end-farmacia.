package com.generation.farmacia.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // CREATE
@Table(name = "tb_categoria")
public class Categoria {

	@Id // PRIMARY KEY
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GenerateValue - Indica que a PK será gerada automaticamente
														// pelo db(banco de dados)
														// strategy - indica a estrategia, ou seja, qual estrategia que
														// a PK irá utilizar para ser gerada
														// GenerationType.IDENTITY - indica que a estratégia de criação
														// é a IDENTITY
	private Long id;

	@NotBlank
	@Size(min = 4, max = 50, message = " O atributo nome deve conter no minímo 4 e no maxímo 50 caracter :0")
	private String nome;

	@NotBlank
	@Size(min = 10, max = 200, message = " O atributo descrição deve conter no minímo 10 e no maxímo 200 caracter :0")
	private String descricao;

	@OneToMany (mappedBy = "categoria", cascade = CascadeType.REMOVE) // 
	@JsonIgnoreProperties("Categoria")
	private List<Produto> produto;
	/*  A Anotação @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL): indica 
    *  que a Classe Tema terá um relacionamento do tipo One To Many (Um para Muitos) com a Classe 
    *  Postagem
    *
    *  mappedBy = "categoria": Indica qual Objeto será utilizado como "chave estrangeira" no relacionamento,
    *  em nosso exemplo será o objeto tema inserido na Classe produto
    * 
    *  cascade = CascadeType.REMOVE: Indica que apenas a ação apagar um Objeto da Classe categoria,
    *  se propagará para todos os respectivos objetos associados ao Objeto categoria apagado.
    *  Exemplo: Se eu apagar uma categoria (Java), todas os produtos associadas a categoria apagado também serão apagadas.
    * 
    *  A Anotação @JsonIgnoreProperties("produto") desabilita a recursividade
    *  infinita durante a exibição dos dados no formato JSON
    *
    *  private List<Postagem> produto: Collection do tipo List composta por Objetos do tipo Produto
    *  que listará todas as produto associadas com os respectivas categorias.
    */
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

}
