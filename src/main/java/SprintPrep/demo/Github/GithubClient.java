package SprintPrep.demo.Github;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class GithubClient {
    private final OAuth2AuthorizedClientService clientService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.github.com")
            .build();

    public List<Map<String, Object>> getCommits(String owner, String repo, OAuth2AuthenticationToken authToken) {
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authToken.getAuthorizedClientRegistrationId(),
                authToken.getName()
        );

        String token = client.getAccessToken().getTokenValue();

        return webClient.get()
                .uri("/repos/{owner}/{repo}/commits", owner, repo)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
    }
}
