package com.cap.project.demo.service;

import com.cap.project.demo.domain.Board;
import com.cap.project.demo.domain.BoardCmt;
import com.cap.project.demo.domain.Expert;
import com.cap.project.demo.domain.User;
import com.cap.project.demo.dto.request.BoardCmtRequest;
import com.cap.project.demo.dto.request.BoardRequest;
import com.cap.project.demo.dto.response.BoardResponse;
import com.cap.project.demo.dto.response.BoardResponseSecurity;
import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.repository.BoardCmtRepository;
import com.cap.project.demo.repository.BoardRepository;
import com.cap.project.demo.repository.ExpertRepository;
import com.cap.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardCmtRepository boardCmtRepository;
    @Autowired
    private ExpertRepository expertRepository;


    //findAll()메소드를 호출하여 게시글 목록을 반환하는 메소드
    public List<BoardResponse> findAll() {

        List<Board> boards = boardRepository.findAll();

        // get collection of list of BoardResponse
        List<BoardResponse> boardResponses = boards.stream().map(board -> {
            return BoardResponse.builder()
                    .id(board.getId())
                    .user(UserResponse.builder()
                            .db_id(board.getUser().getId())
                            .name(board.getUser().getName())
                            .nickname(board.getUser().getNickname())
                            .age(board.getUser().getAge())
                            .role(board.getUser().getRoleType())
                            .build())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .emotion(board.getEmotion())
                    .createdDate(board.getCreatedDate())
                    .modifiedDate(board.getModifiedDate())
                    .build();
        }).collect(Collectors.toList());

        return boardResponses;
    }

    public BoardResponse findById(Long id) {

        // get Board object from boardRepository
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // get BoardResponse object from board
        BoardResponse boardResponse = BoardResponse.builder()
                .id(board.getId())
                .user(UserResponse.builder()
                        .db_id(board.getUser().getId())
                        .name(board.getUser().getName())
                        .nickname(board.getUser().getNickname())
                        .age(board.getUser().getAge())
                        .role(board.getUser().getRoleType())
                        .build())
                .title(board.getTitle())
                .content(board.getContent())
                .emotion(board.getEmotion())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .boardCmts(board.getBoardCmts())
                .build();

        return boardResponse;
    }

    public void post(BoardRequest boardRequest , Long db_id) {

        User user = userRepository.findById(db_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // get Board object from boardRepository
        Board board = boardRepository.save(Board.builder()
                .user(user)
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent()).build());
    }


    public void postCommentForUser(Long id, BoardCmtRequest boardCmtRequest, UserResponse userResponse) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        User user = userRepository.findById(userResponse.getDb_id()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        BoardCmt boardCmt = boardCmtRepository.save(BoardCmt.builder()
                .board(board)
                .user(user)
                .content(boardCmtRequest.getCmt())
                .build());

    }


    public void postCommentForExpert(Long id, BoardCmtRequest boardCmtRequest, ExpertResponse expertResponse) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Expert expert = expertRepository.findById(expertResponse.getDb_id()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        BoardCmt boardCmt = boardCmtRepository.save(BoardCmt.builder()
                .board(board)
                .expert(expert)
                .content(boardCmtRequest.getCmt())
                .build());

    }
}
