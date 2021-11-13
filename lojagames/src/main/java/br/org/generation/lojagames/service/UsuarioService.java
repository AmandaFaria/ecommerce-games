package br.org.generation.lojagames.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.lojagames.model.Usuario;
import br.org.generation.lojagames.model.UsuarioLogin;
import br.org.generation.lojagames.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	//verificando se existe um usuario cadastrado
	public Optional<Usuario> cadastrarUsuario(Usuario usuario){
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 
			return Optional.empty();  //se ele nao existe retorna o vazio
		
		if(calcularIdade(usuario.getDataNascimento()) < 18) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário é menor de idade!", null);
		}
		usuario.setSenha(criptografarSenha(usuario.getSenha())); //pega a senha do usuario, criptografa e grava de novo no usuario
		
		return Optional.of(usuarioRepository.save(usuario));  //salvei no meu usuario a senha
	}
	
	//atualizar o usuario, verificando se ele existe pelo id
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			
			if(buscaUsuario.isPresent()) {
				if(buscaUsuario.get().getId() != usuario.getId()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário já existente!", null);
				}
				
				if(calcularIdade(usuario.getDataNascimento()) < 18) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário é menor de idade!", null);
				}
			}
			usuario.setSenha(criptografarSenha(usuario.getSenha())); //pega a senha do usuario, criptografa e grava de novo no usuario
			return Optional.of(usuarioRepository.save(usuario));  //salvei no meu usuario a senha
		}
		return Optional.empty();
	}
	
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {
			if (compararSenha(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				String token = gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());

				usuarioLogin.get().setId(usuario.get().getId());				
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				usuarioLogin.get().setToken(token);

				return usuarioLogin;

			}
		}	
		
		return Optional.empty();
		
	}
	
	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}
	
	//ta fazendo a diferença entre a data atual e a data de nascimento em anos.
	private int calcularIdade(LocalDate dataNascimento) {
		return Period.between(dataNascimento, LocalDate.now()).getYears();   
	}
	
	//criptografando a senha
	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
	}

	private Boolean compararSenha(String senhaDigitada, String senhaBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
		return encoder.matches(senhaDigitada, senhaBanco);
	}
}
