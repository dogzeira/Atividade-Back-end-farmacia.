package com.generation.farmacia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;  

@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired // injeção de dependência
	private ProdutoRepository produtoRepository;

	@Autowired // injeção de dependência
	private CategoriaRepository categoriaRepository;

	@GetMapping // método get para listar todos os produtos
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/{id}") // método get para listar um produto pelo id
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id) // select * from tb_postagens where id = id
				.map(resposta -> ResponseEntity.ok(resposta)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}") // consultar produtos por nome
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping // método post para criar um produto
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		return categoriaRepository.findById(produto.getCategoria().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PutMapping // método put para atualizar um produto
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		if (produtoRepository.existsById(produto.getId())) {
			return categoriaRepository.findById(produto.getCategoria().getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
					.orElse(ResponseEntity.badRequest().build());
		}
		return ResponseEntity.notFound().build();
	}  

	@DeleteMapping("/{id}") // método delete para deletar um produto
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> {
			produtoRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());

	}
}
