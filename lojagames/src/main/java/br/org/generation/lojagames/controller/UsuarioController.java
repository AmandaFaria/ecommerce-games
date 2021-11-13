package br.org.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

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

import br.org.generation.lojagames.model.Usuario;
import br.org.generation.lojagames.model.UsuarioLogin;
import br.org.generation.lojagames.repository.UsuarioRepository;
import br.org.generation.lojagames.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders ="*")
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario){
		return usuarioService.cadastrarUsuario(usuario)
				.map(respostaCadastro -> ResponseEntity.status(HttpStatus.CREATED).body(respostaCadastro))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){
		return usuarioService.atualizarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	//usado para enviar e salvar dados, no caso do logar, enviar os dados
		@PostMapping("/logar")
		public ResponseEntity<UsuarioLogin> login(Optional <UsuarioLogin> usuarioLogin){
			return usuarioService.autenticarUsuario(usuarioLogin)
					.map(respostaAutenticacao -> ResponseEntity.status(HttpStatus.OK).body(respostaAutenticacao))
					.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		}
		
		@DeleteMapping("{id}")
		public ResponseEntity<?> deleteUsuario(@PathVariable long id){
			return usuarioRepository.findById(id)
					.map(respostaDelete -> {
						usuarioRepository.deleteById(id);
						return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
					})
					.orElse(ResponseEntity.notFound().build());
		}
}
