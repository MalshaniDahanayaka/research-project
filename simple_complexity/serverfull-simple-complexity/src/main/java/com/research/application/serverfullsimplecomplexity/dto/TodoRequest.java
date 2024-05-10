package com.research.application.serverfullsimplecomplexity.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TodoRequest {

    private String title;
    private String description;
    private boolean completed;
}
