package SprintPrep.demo.Controller;

import SprintPrep.demo.Service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import SprintPrep.demo.Github.GithubClient;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnalyzeController {
    private final GithubClient gitHubClient;
    private final AnalysisService analysisService;

    @GetMapping("/analyze")
    public AnalysisService.RepoAnalysis analyze(
            @RequestParam String owner,
            @RequestParam String repo,
            OAuth2AuthenticationToken authToken) {

        List<Map<String, Object>> commits = gitHubClient.getCommits(owner, repo, authToken);
        return analysisService.analyze(commits);
    }
}
