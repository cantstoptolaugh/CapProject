package com.cap.project.demo.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "boards_cmt")
public class BoardCmt extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_cmt_id")
    private Long id;

    // join with user table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , foreignKey = @ForeignKey(name = "fk_board_cmt_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // join with expert table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id" , foreignKey = @ForeignKey(name = "fk_board_cmt_expert"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Expert expert;

    // join with board table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id" , nullable = false , foreignKey = @ForeignKey(name = "fk_board_cmt_board"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    // board_cmt_content
    @Column(name = "board_cmt_content" , nullable = false)
    private String content;

    public BoardCmt() {
    }

    @Builder
    public BoardCmt(User user, Board board, Expert expert , String content) {
        this.user = user;
        this.board = board;
        this.content = content;
        this.expert = expert;
    }

}
