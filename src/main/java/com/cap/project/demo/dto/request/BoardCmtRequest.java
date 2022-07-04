package com.cap.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCmtRequest {

    private String cmt;


    @Builder
    public BoardCmtRequest(String cmt) {
        this.cmt = cmt;
    }
}
