package com.sicuga.sicugaserver.service;

import com.sicuga.sicugaserver.dto.QuizDTORequest;
import com.sicuga.sicugaserver.dto.QuizDTOResponse;
import com.sicuga.sicugaserver.entity.GeneralUserAuth;
import com.sicuga.sicugaserver.entity.Quiz;
import com.sicuga.sicugaserver.exception.GlobalException;
import com.sicuga.sicugaserver.repository.GeneralUserAuthRepository;
import com.sicuga.sicugaserver.repository.QuizRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class QuizServiceImpl {

    @Autowired
    GeneralUserAuthServiceImpl generalUserAuthService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<QuizDTOResponse> registerQuiz(QuizDTORequest quizDTO){
        QuizDTOResponse quizDTOResponse;
        try{
            Quiz quiz = quizRepository.save(mapperQuiz(quizDTO));
            quizDTOResponse = modelMapper.map(quiz,QuizDTOResponse.class);
        }catch (Exception e){
            log.info("Error during saving quiz: {}", e.getMessage());
            throw new GlobalException("Error during quiz saved. Contact your administrator for support.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(quizDTOResponse,HttpStatus.OK);
    }

    public ResponseEntity<QuizDTOResponse> getQuiz(String idQuiz){
        Quiz quiz = quizRepository.findById(idQuiz)
                .orElseThrow(()->new GlobalException("Quiz was not found.", HttpStatus.INTERNAL_SERVER_ERROR));
        QuizDTOResponse quizDTOResponse = modelMapper.map(quiz, QuizDTOResponse.class);
        return new ResponseEntity<>(quizDTOResponse,HttpStatus.OK);
    }

    public ResponseEntity<List<QuizDTOResponse>> getAllQuizByUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        GeneralUserAuth userAuth= generalUserAuthService.findUserByEmail(email);
        List<Quiz> quizzes = quizRepository.findByGeneralUserAuth_Id(userAuth.getId()).get();
        List<QuizDTOResponse> quizzesDTO = new ArrayList<QuizDTOResponse>();
        quizzes.forEach(
                quiz -> quizzesDTO.add(modelMapper.map(quiz,QuizDTOResponse.class))
        );
        return new ResponseEntity<>(quizzesDTO,HttpStatus.OK);
    }

    public ResponseEntity deleteQuiz(String idQuiz){
        quizRepository.deleteById(idQuiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Quiz  mapperQuiz(QuizDTORequest quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getId());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setName(quizDTO.getName());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        GeneralUserAuth userAuth= generalUserAuthService.findUserByEmail(email);
        quiz.setGeneralUserAuth(userAuth);
        quiz.setListParam(quizDTO.getListParam());
        return quiz;
    }
}
