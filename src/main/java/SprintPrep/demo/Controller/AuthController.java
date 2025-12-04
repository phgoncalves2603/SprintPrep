package SprintPrep.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2AuthorizedClientService clientService;

    @GetMapping("login")
    public void login(){

    }

    @GetMapping("/me")
    public Map<String, Object> me(
            @AuthenticationPrincipal OAuth2User principal,
            OAuth2AuthenticationToken authToken) {

        Map<String, Object> response = new HashMap<>();
        if (principal == null) {
            return null; // React pode tratar como "not logged in"
        }

        response.put("username", principal.getAttribute("login"));
        response.put("avatarUrl", principal.getAttribute("avatar_url"));
        response.put("githubId", principal.getAttribute("id"));

        // opcional: ver token (NÃO retornar inteiro)
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authToken.getAuthorizedClientRegistrationId(),
                authToken.getName()
        );
        if (client != null) {
            String token = client.getAccessToken().getTokenValue();
            response.put("tokenSnippet", token.substring(0, 6) + "..."); // só debug
        }

        return response;
    }



}
