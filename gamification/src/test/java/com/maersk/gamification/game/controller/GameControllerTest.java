package com.maersk.gamification.game.controller;

import com.maersk.gamification.challenge.domain.ChallengeSolvedDTO;
import com.maersk.gamification.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(GameController.class)
class GameControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ChallengeSolvedDTO> jsonRequestAttempt;

    @Test
    void newAttemptReceivedTest() throws Exception {
        // given
        ChallengeSolvedDTO solvedDTO =
                new ChallengeSolvedDTO( 1L, true,
                        20, 20, 1L, "Hariharan");
        when(gameService.newAttemptForUser(solvedDTO)).thenReturn(any());


        // when
        MockHttpServletResponse response = mvc.perform(post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(solvedDTO).getJson()))
                .andReturn()
                .getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}