package com.example.effectivemobiletest;

import com.example.effectivemobiletest.dto.CommentDto;
import com.example.effectivemobiletest.dto.LoginRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.model.Comment;
import com.example.effectivemobiletest.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/init_for_task_controller.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTestIT extends AbstractTestContainers {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentRepository commentRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getCommentsById_withValidToken_shouldReturnIsOk() throws Exception {
        Long taskId = 1L;

        var result = get("/api/v1/comment")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .param("taskId", String.valueOf(taskId));

        this.mockMvc.perform(result).andExpectAll(
                status().isOk(),
                (jsonPath("$", notNullValue()))
        );
    }

    @Test
    void createComment_withValidToken_shouldReturnIsCreated() throws Exception {
        Long taskId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setComment("new_comment_in_test");

        var result = post("/api/v1/comment/" + taskId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("user1@example.com", "string"))
                .content(objectMapper.writeValueAsString(commentDto))
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(result).andExpect(
                status().isCreated()
        );
        Page<Comment> list = commentRepository.findByTaskId(taskId, Pageable.unpaged());
        Assertions.assertEquals(3, list.getSize());
    }

    private String obtainAccessToken(String email, String password) throws Exception {
        LoginRequestDto preparedRequest = new LoginRequestDto(email, password);

        var requestBuilder = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preparedRequest));

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        TokenResponseDto token = objectMapper.readValue(result.getResponse().getContentAsString(), TokenResponseDto.class);
        return token.getAccessToken();
    }
}
