package SprintPrep.demo.Security;

import SprintPrep.demo.Entity.AppUser;
import SprintPrep.demo.Repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService
implements org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AppUserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attrs = oAuth2User.getAttributes();

        Long githubId = ((Number) attrs.get("id")).longValue();
        String login = (String) attrs.get("login");
        String avatarUrl = (String) attrs.get("avatar_url");
        String email = (String) attrs.get("email"); // pode vir null

        AppUser user = userRepository.findByGithubId(githubId)
                .orElseGet(() -> {
                    AppUser u = new AppUser();
                    u.setGithubId(githubId);
                    u.setCreatedAt(LocalDateTime.now());
                    return u;
                });

        user.setUsername(login);
        user.setAvatarUrl(avatarUrl);
        user.setEmail(email);
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        // Usa "login" como username attribute
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                attrs,
                "login"
        );
    }


}
