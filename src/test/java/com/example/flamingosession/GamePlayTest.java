package com.example.flamingosession;

import com.example.flamingosession.client.dto.GameStateResponse;
import com.example.flamingosession.controller.dto.GameStateData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GamePlayTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private static WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
    }

    @AfterEach
      void tearDown() {
        wireMockServer.stop();
    }
    @Test
    public void getSessionAndSimulate() throws InterruptedException {
        setupStartStub();
        ResponseEntity<String> sessionResponse = restTemplate
                .postForEntity("/sessions", "", String.class);
        assertEquals(HttpStatus.OK, sessionResponse.getStatusCode());
        String session = sessionResponse.getBody();
        restTemplate.postForEntity("/sessions/" + session + "/simulate", "", String.class);
        setupStubO();
        TimeUnit.SECONDS.sleep(2);
        setupStubX();
        TimeUnit.SECONDS.sleep(3);
        ResponseEntity<GameStateResponse> gameStateResponse = restTemplate
                .getForEntity("/sessions/" + session, GameStateResponse.class);
        assertEquals(HttpStatus.OK, gameStateResponse.getStatusCode());
        long movesCount = Arrays.stream(gameStateResponse.getBody().data().board())
                .filter(a -> a != 0)
                .count();
        assertEquals(2 , movesCount);
    }

    private void setupStartStub(){
        wireMockServer.stubFor(post(urlMatching("/games/.*/start"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "moveStatus": "X_TURN"
                                }
                                """)
                        .withStatus(200)));
    }
    private void setupStubO(){
        wireMockServer.stubFor(post(urlMatching("/games/.*/move"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "moveStatus": "O_TURN"
                                }
                                """)
                        .withStatus(200)));
    }
    private void setupStubX(){
        wireMockServer.stubFor(post(urlMatching("/games/.*/move"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "moveStatus": "X_TURN"
                                }
                                """)
                        .withStatus(200)));
    }
}