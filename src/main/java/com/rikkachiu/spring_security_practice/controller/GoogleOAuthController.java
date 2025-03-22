package com.rikkachiu.spring_security_practice.controller;

import com.rikkachiu.spring_security_practice.model.dto.ExchangeTokenDTO;
import com.rikkachiu.spring_security_practice.model.dto.RefreshTokenDTO;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@CrossOrigin
@RestController
public class GoogleOAuthController {

    private String GOOGLE_CLIENT_ID = "x";

    private String GOOGLE_CLIENT_SECRET = "x";

    private String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    private String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    private String codeChallenge;

    private String codeChallengeMethod;

    private String codeVerifier;

    private String generateRandomState() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] data = new byte[6];
        secureRandom.nextBytes(data);

        return Base64.getUrlEncoder().encodeToString(data);
    }

    private String generateRandomCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] data = new byte[96];
        secureRandom.nextBytes(data);

        return Base64.getUrlEncoder().encodeToString(data);
    }

    private String createHash(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(value.getBytes(StandardCharsets.US_ASCII));

        return  Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    @GetMapping("/google/buildAuthUrl")
    public String buildAuthUrl() throws NoSuchAlgorithmException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(GOOGLE_AUTH_URL)
                .queryParam("response_type", "code")
                .queryParam("client_id", GOOGLE_CLIENT_ID)
                .queryParam("scope", "profile+email+openid+https://www.googleapis.com/auth/youtube.readonly")
                .queryParam("redirect_uri", "http://localhost:3000/oauth2-google-demo.html")
                .queryParam("state", generateRandomState())
                .queryParam("access_type", "offline");

        codeChallengeMethod = "S256";
        codeVerifier = generateRandomCodeVerifier();
        codeChallenge = createHash(codeVerifier);
        uriComponentsBuilder
                .queryParam("code_challenge_method", codeChallengeMethod)
                .queryParam("code_challenge", codeChallenge);

        return uriComponentsBuilder.toUriString();
    }

    @PostMapping("/google/exchangeToken")
    public String exchangeToken(@RequestBody ExchangeTokenDTO exchangeTokenDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", GOOGLE_CLIENT_ID);
        body.add("client_secret", GOOGLE_CLIENT_SECRET);
        body.add("code", exchangeTokenDTO.getCode());
        body.add("redirect_uri", "http://localhost:3000/oauth2-google-demo.html");

        body.add("code_verifier", codeVerifier);

        String result = new RestTemplate().postForObject(GOOGLE_TOKEN_URL, new HttpEntity<>(body, headers), String.class);

        return result;
    }

    @GetMapping("/google/getGoogleUser")
    public String getGoogleUser(@RequestParam String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
//        headers.setBearerAuth(accessToken);

        // call Google 的 api，取得使用者在 Google 中的基本資料
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";

        String result = new RestTemplate()
                .exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class)
                .getBody();

        return result;
    }

    @PostMapping("/google/refreshToken")
    public String refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", GOOGLE_CLIENT_ID);
        body.add("client_secret", GOOGLE_CLIENT_SECRET);
        body.add("refresh_token", refreshTokenDTO.getRefreshToken());

        String result = new RestTemplate()
                .postForObject(GOOGLE_TOKEN_URL,
                        new HttpEntity<>(body, headers),
                        String.class);

        return result;
    }
}
