package com.youx.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.http.HeadersBeanDefinitionParser;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.youx.ApplicationContexLoad;
import com.youx.model.Cliente;
import com.youx.repository.ClienteRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
//Gera o Token e valida o Token
public class JWTTokenAutenticacaoService {
	
	 
	private static final long EXPIRATION_TIME = 2073600000;//24 dias
	
	//Uma senha única para compor a autenticação e ajudar na segurança
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	//Prefixo padrão de TOKEN
    private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	//Gerando token de autenticação adicionando ao cabeçalho e resposta Http
	public void addAuthentication(HttpServletResponse response, String username)throws IOException {
		 
		  //Montagem do Token
		   String JWT = Jwts.builder()
				        .setSubject(username)
				        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				        .signWith(SignatureAlgorithm.HS512, SECRET).compact();
		   
		 //Junta o token com o prefixo
		   String token = TOKEN_PREFIX + " " + JWT;
		   
		 //Adiciona o cabeçalho http
		   response.addHeader(HEADER_STRING, token);
		   
		   //Liberando resposta para portas diferentes que usam a API ou caso clientes Web
			  liberacaoCors(response);
			 
			 //Liberando resposta para porta diferente do projeto Angular
	          //response.addHeader("Access-Control-Allow-Origin", "*");
			
		  //Escreve token como resposta no corpo http
		  response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		   
		   
		}
	
	 public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
	     
		    String token = request.getHeader(HEADER_STRING);
		    
		    if(token != null) {
		    	
		    	 String user = Jwts.parser().setSigningKey(SECRET)
		    			                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
		    			                    .getBody().getSubject();
		    	 
		    	 if(user != null) {
		    		 
		    		 Cliente usuario = ApplicationContexLoad.getApplicationContext()
		    				            .getBean(ClienteRepository.class).findUserByLogin(user);
		    	 
		    		 if(usuario != null) {
		    			 
		                  return new UsernamePasswordAuthenticationToken(
		                		  usuario.getLogin(),
		                		  usuario.getSenha(),
		                		  usuario.getAuthorities());
		    		 }
		    		 
		    	 }
		    }
		    
		    liberacaoCors(response);
		    
		    return null;
	 }
	 
	 private void liberacaoCors(HttpServletResponse response) {
		 
		 if (response.getHeader("Access-Control-Allow-Origin") == null) {
				response.addHeader("Access-Control-Allow-Origin", "*");
			}
			
			if (response.getHeader("Access-Control-Allow-Headers") == null) {
				response.addHeader("Access-Control-Allow-Headers", "*");
			}
			
			
			if (response.getHeader("Access-Control-Request-Headers") == null) {
				response.addHeader("Access-Control-Request-Headers", "*");
			}
			
			 if(response.getHeader("Access-Control-Allow-Methods") == null) {
				 response.addHeader("Access-Control-Allow-Methods", "*");
			 }
			
			 
	 }
 
}
