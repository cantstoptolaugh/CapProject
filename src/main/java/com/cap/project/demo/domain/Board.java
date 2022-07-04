package com.cap.project.demo.domain;

import jdk.jfr.Enabled;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "boards")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    //join with user table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false , foreignKey = @ForeignKey(name = "fk_board_user"))
    private User user;

    // board title
    @Column(name = "board_title" , nullable = false)
    private String title;

    // board content
    @Column(name = "board_content" , nullable = false)
    private String content;

    // board_emotion
    @Column(name = "board_emotion" , nullable = false)
    private String emotion;

    // board know onw boardcmt
    @OneToMany(mappedBy = "board")
    private List<BoardCmt> boardCmts = new ArrayList<>();

    public Board() {
    }

    @Builder
    public Board(User user, String title, String content, String emotion) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.emotion = emotion;
    }
}
