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

    @PostMapping("/saveQuiz")
    public ResponseEntity<QuizDTOResponse> registerQuiz(@RequestBody QuizDTORequest quizDTO){
        return quizService.saveQuiz(quizDTO);
    }

    @GetMapping("/{idQuiz}")
    public ResponseEntity<QuizDTOResponse> getQuiz(@PathVariable(value = "idQuiz") String idQuiz){
        return quizService.getQuiz(idQuiz);
    }

    @GetMapping("/listAll/{idUser}")
    public ResponseEntity<List<QuizDTOResponse>> getAllQuizByUser(@PathVariable(value = "idUser") String idUser){
        return quizService.getAllQuizByUser(idUser);
    }

    @DeleteMapping("/{idQuiz}")
    public ResponseEntity deleteQuiz(@PathVariable(value = "idQuiz") String idQuiz){
        return quizService.deleteQuiz(idQuiz);
    }


}
