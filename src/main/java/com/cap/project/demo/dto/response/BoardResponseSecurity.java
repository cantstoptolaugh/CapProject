package com.cap.project.demo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseSecurity {

    private Long id;
    private UserResponse user;
    private String title;
    private String content;
    private String emotion;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder
    public BoardResponseSecurity(Long id, UserResponse user, String title, String content, String emotion ,
                                 LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.emotion = emotion;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
