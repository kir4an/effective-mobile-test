package com.example.effectivemobiletest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ResponseErrorDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private HttpStatus status;

    @JsonProperty("error_message")
    private List<String> errorMessage;
}
