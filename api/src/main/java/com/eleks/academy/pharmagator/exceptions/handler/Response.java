package com.eleks.academy.pharmagator.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String message;

    @Builder.Default()
    private LocalDateTime timestamp = LocalDateTime.now();

}
