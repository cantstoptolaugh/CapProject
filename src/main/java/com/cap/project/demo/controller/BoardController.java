package com.cap.project.demo.controller;

import com.cap.project.demo.config.auth.PrincipalDetails;
import com.cap.project.demo.config.auth.PrincipalDetailsForExpert;
import com.cap.project.demo.dto.request.BoardCmtRequest;
import com.cap.project.demo.dto.request.BoardRequest;
import com.cap.project.demo.dto.response.BoardResponse;
import com.cap.project.demo.dto.response.BoardResponseSecurity;
import com.cap.project.demo.dto.response.ExpertResponse;
import com.cap.project.demo.dto.response.UserResponse;
import com.cap.project.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * 게시글의 목록을 보여주는 컨트롤러
     * @return 게시글 보기 페이지
     */
    @GetMapping("/boards")
    public String board(Model model) {

        List<BoardResponse> boards = boardService.findAll();
        model.addAttribute("boards", boards);

        return "board/boards";
    }

    /**
     * 상세 게시글을 확인하는 컨트롤러
     * @param id 상세 게시글의 고유 db_id
     * @param model
     * @return 상세 게시글의 페이지
     */
    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {

        BoardResponse board = boardService.findById(id);
        model.addAttribute("board", board);

        return "board/boardDetail";
    }

    /**
     * 게시글을 작성할 수 있는 폼을 반환
     * @return 게시글 작성 폼
     */
    @GetMapping("/board/form")
    public String boardWriteForm(){
        return "board/boardWriteForm";
    }


    /**
     * 게시글을 작성하는 컨트롤러
     * @param board 작성하는 게시글의 정보
     * @param authentication
     * @return 게시글 목록 페이지
     */
    @PostMapping("/boards")
    public String boardCreate(BoardRequest board , Authentication authentication) {

        // get user object from session
        Long db_id = ((PrincipalDetails) authentication.getPrincipal()).getUser().getDb_id();

        boardService.post(board , db_id);

        return "redirect:/boards";
    }

    /**
     * 게시글의 댓글을 작성하는 컨트롤러
     * @param id 게시글 고유 db_id
     * @param boardCmt 작성하는 댓글의 정보
     * @param authentication
     * @return 상세 게시글 페이지
     */
    @PostMapping("/board/{id}/comment")
    public String boardComment(@PathVariable Long id, BoardCmtRequest boardCmt , Authentication authentication) {

        if(authentication.getPrincipal() instanceof com.cap.project.demo.config.auth.PrincipalDetails){
            UserResponse user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
            boardService.postCommentForUser(id, boardCmt ,user);

        }else{
            ExpertResponse expert = ((PrincipalDetailsForExpert) authentication.getPrincipal()).getExpert();
            boardService.postCommentForExpert(id, boardCmt , expert);
        }

        return "redirect:/board/" + id;
    }






}
