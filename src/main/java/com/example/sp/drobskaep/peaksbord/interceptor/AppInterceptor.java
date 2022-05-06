package com.example.sp.drobskaep.peaksbord.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.sp.drobskaep.peaksbord.annotation.Private;
import com.example.sp.drobskaep.peaksbord.annotation.Public;
import com.example.sp.drobskaep.peaksbord.rest.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variável para obter a URI
		String uri= request.getRequestURI();
		// variável para sessão
		HttpSession session = request.getSession();
		// se for página de erro libera
		if (uri.startsWith("/error")) {
			return true;
		}
		
		// verificar se Handler é um HandlerMethod
		// o que indica é que ele está chamando o método 
		// em algum controller
		
		if (handler instanceof HandlerMethod) {
			// casting de Object para HandlerMehod
			HandlerMethod metodo = (HandlerMethod) handler;
			if (uri.startsWith("/api")) {
				//	variável para o token
				String token = null;
				// verificar se é um método privado
				if (metodo.getMethodAnnotation(Private.class) != null) {
					try {
					//se o método for privado recupera o token
					token = request.getHeader("Authorization");
					// cria o algoritmo para assinar
					Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
					// objeto para verificar o token 
					JWTVerifier verifier = JWT.require(algoritmo).
							withIssuer(UsuarioRestController.EMISSOR).build();
					// decodifica o Token
					DecodedJWT jwt = verifier.verify(token);
					// recupera os dados do payload
					Map<String, Claim> claims = jwt.getClaims();
					System.out.println(claims.get("name"));
					return true;
					}catch (Exception e) {
						e.printStackTrace();
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}
				}
				
				return true;
			}else {
				
				// verifica se este método é público
				if (metodo.getMethodAnnotation(Public.class) != null) {
					return true;
				}
				// verifica se existe um úsuario logado
				if (session.getAttribute("usuarioLogado")!= null) {
					return true;
				}
				// redireciona para a página inicial
				response.sendRedirect("/");
				return false;
			}		
		}
		
		return true;
	}
}
