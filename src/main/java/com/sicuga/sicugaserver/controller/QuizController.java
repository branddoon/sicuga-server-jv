package com.sicuga.sicugaserver.controller;

import com.sicuga.sicugaserver.dto.QuizDTORequest;
import com.sicuga.sicugaserver.dto.QuizDTOResponse;
import com.sicuga.sicugaserver.service.QuizServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private QuizServiceImpl quizService;

    @PostMapping("/registerQuiz")
    public ResponseEntity<QuizDTOResponse> registerQuiz(@RequestBody QuizDTORequest quizDTO){
        return quizService.registerQuiz(quizDTO);
    }

    @GetMapping("/{idQuiz}")
    public ResponseEntity<QuizDTOResponse> getQuiz(@PathVariable(value = "idQuiz") String idQuiz){
        return quizService.getQuiz(idQuiz);
    }

    @GetMapping("/listAllQuiz")
    public ResponseEntity<List<QuizDTOResponse>> getAllQuizByUser(){
        return quizService.getAllQuizByUser();
    }

    @DeleteMapping("/{idQuiz}")
    public ResponseEntity deleteQuiz(@PathVariable(value = "idQuiz") String idQuiz){
        return quizService.deleteQuiz(idQuiz);
    }


}
