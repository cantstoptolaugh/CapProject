package com.cap.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequest {

    private String title;
    private String content;

    @Builder
    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }



}
