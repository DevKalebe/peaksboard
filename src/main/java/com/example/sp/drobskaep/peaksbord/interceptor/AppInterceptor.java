package com.example.sp.drobskaep.peaksbord.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.sp.drobskaep.peaksbord.annotation.Public;

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
