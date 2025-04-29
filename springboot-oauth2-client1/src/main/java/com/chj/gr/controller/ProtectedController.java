package com.chj.gr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
//@PreAuthorize("hasAuthority('SCOPE_read')") // not working
public class ProtectedController {

	@GetMapping("/secure")
    public String protectedEndpoint(@RequestHeader("Authorization") String token) {
        return "This is a protected API. Token received: " + token;
    }
	
//    @GetMapping("/principal")
//    public String hello(@AuthenticationPrincipal AuthenticatedPrincipal principal) {
//        return "Hello from client1! Client ID: " + principal.getName();
//    }
    
    
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/call-client2")
    public String callClient2(JwtAuthenticationToken authentication) {
    	String accessToken = null;
        // Récupérer le client autorisé pour client1
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                "client1", authentication.getName()); // dindn't work..

        if (authorizedClient != null) {
        	// Obtenir le token d'accès
            accessToken = authorizedClient.getAccessToken().getTokenValue();
        } else if (authentication.getCredentials() instanceof org.springframework.security.oauth2.jwt.Jwt) {
        	accessToken = ((org.springframework.security.oauth2.jwt.Jwt) authentication.getCredentials()).getTokenValue();
        } else {
        	return "Erreur: Aucun client OAuth2 autorisé trouvé";
        }

        // Configurer les headers avec le token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Appeler l'endpoint sécurisé de client2
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8082/api/secure",
//            		"http://Z-CLIENT2/api/secure",  // didn't work yet, i need to check it..
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return "Réponse de client2: " + response.getBody();
        } catch (Exception e) {
            return "Erreur lors de l'appel à client2: " + e.getMessage();
        }
    }
}