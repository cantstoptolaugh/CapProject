package com.cap.project.demo.dto.response;

import com.cap.project.demo.domain.BoardCmt;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponse {

    private Long id;
    private UserResponse user;
    private String title;
    private String content;
    private String emotion;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    // Arraylist of boardCmt
    private List<BoardCmt> boardCmts;

    @Builder
    public BoardResponse(Long id, UserResponse user, String title, String content, String emotion ,
                                 LocalDateTime createdDate, LocalDateTime modifiedDate , List<BoardCmt> boardCmts) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.emotion = emotion;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.boardCmts = boardCmts;
    }
}


