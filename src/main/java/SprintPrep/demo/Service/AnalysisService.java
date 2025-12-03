package SprintPrep.demo.Service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalysisService {
    @Data
    public static class RepoAnalysis {
        private int totalCommits;
        private Map<String, Long> commitsByAuthor;
    }

    public RepoAnalysis analyze(List<Map<String, Object>> commits) {
        RepoAnalysis analysis = new RepoAnalysis();
        analysis.setTotalCommits(commits.size());

        Map<String, Long> byAuthor = commits.stream()
                .map(c -> (Map<String, Object>) c.get("author"))
                .filter(a -> a != null)
                .map(a -> (String) a.get("login"))
                .collect(Collectors.groupingBy(
                        login -> login,
                        Collectors.counting()
                ));

        analysis.setCommitsByAuthor(byAuthor);
        return analysis;
    }
}
