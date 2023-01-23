package dev.vorstu.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	@Autowired
	private AuthUserRepo authUserRepo;
	
	@GetMapping("user")
	@ResponseBody
	public Principal user(Principal user) {		
		return user;
	}
	
	
	@GetMapping("AuthorizedUser")
	@ResponseBody
	public Long getAuthorizedUser(Principal user) {
		AuthUserEntity authorizedUser = null;
		for (AuthUserEntity itVar : authUserRepo.findAll())
        {
            if (itVar.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            	authorizedUser = itVar;
                break;
            }
        }
		return authorizedUser.getId();
	}
	

	@PostMapping(path = "/logout", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Principal logout(Principal user, HttpServletRequest request, HttpServletResponse response) {
		CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		cookieClearingLogoutHandler.logout(request, response, null);
		securityContextLogoutHandler.logout(request, response, null);
		return user;
	}
}
