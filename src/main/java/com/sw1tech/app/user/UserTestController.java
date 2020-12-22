package com.sw1tech.app.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/test")
public class UserTestController {
    
    @GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public String me() {
		return "Teste para qualquer coisa a retornar para USER....";
	}

	@GetMapping("/ge")
	@PreAuthorize("hasRole('GERENTE')")
	public String ge() {
		return "Teste para qualquer coisa a retornar para GERENTE....";
	}

	@GetMapping("/mege")
	@PreAuthorize("hasRole('GERENTE') or hasRole('USER')")
	public String mege() {
		return "Teste para qualquer coisa a retornar para USER OU GERENTE....";
	}

	@GetMapping("/fi")
	@PreAuthorize("hasRole('FINANCEIRO')")
	public String fi() {
		return "Teste para qualquer coisa a retornar para FINANCEIRO....";
	}

	@GetMapping("/to")
	public String to() {
		return "Teste para qualquer coisa a retornar para TODOS....";
	}    
}