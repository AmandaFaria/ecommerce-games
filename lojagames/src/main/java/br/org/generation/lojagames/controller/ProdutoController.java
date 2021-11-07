package br.org.generation.lojagames.controller;

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

import br.org.generation.lojagames.model.Produto;
import br.org.generation.lojagames.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
		//select * from tb_produtos;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable long id){
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))  //se o id existir mostrar
				.orElse(ResponseEntity.notFound().build());	  //senão mostrar como erro 404
			// select * from tb_produtos where id = 1;
	}
	
	@GetMapping("/nomeproduto/{nomeproduto}")
	public ResponseEntity<List<Produto>> getByNomeproduto(@PathVariable String nomeproduto){
		return ResponseEntity.ok(produtoRepository.findAllByNomeprodutoContainingIgnoreCase(nomeproduto));
		//select * from tb_produto
		//ele vai trazer todo o conteúdo de acordo com a busca pelo nome do produto
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
		return produtoRepository.findById(produto.getId())
				.map(resposta -> {
					Produto updateProduto = produtoRepository.save(produto);
					return ResponseEntity.ok().body(updateProduto);			
				})
				.orElse(ResponseEntity.notFound().build());
		// realiza a atualização de algum produto
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable long id){
		return produtoRepository.findById(id)
				.map(resposta -> {
					produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
		// deletando pelo id
	}
	
	
}
