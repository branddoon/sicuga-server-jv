package com.sicuga.sicugaserver.service;

import com.sicuga.sicugaserver.dto.QuizDTORequest;
import com.sicuga.sicugaserver.dto.QuizDTOResponse;
import com.sicuga.sicugaserver.entity.GeneralUserAuth;
import com.sicuga.sicugaserver.entity.Quiz;
import com.sicuga.sicugaserver.exception.GlobalException;
import com.sicuga.sicugaserver.repository.GeneralUserAuthRepository;
import com.sicuga.sicugaserver.repository.QuizRepository;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Service
public class QuizServiceImpl {

    @Autowired
    GeneralUserAuthServiceImpl generalUserAuthService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<QuizDTOResponse> saveQuiz(QuizDTORequest quizDTO){
        QuizDTOResponse quizDTOResponse;
        Quiz quiz = quizRepository.save(mapperQuiz(quizDTO));
        quizDTOResponse = modelMapper.map(quiz,QuizDTOResponse.class);
        quizDTOResponse.setUpdatedAt(getSimpleDateFormat(quiz));
        return new ResponseEntity<>(quizDTOResponse,HttpStatus.OK);
    }

    public ResponseEntity<QuizDTOResponse> getQuiz(String idQuiz){
        Quiz quiz = quizRepository.findById(idQuiz)
                .orElseThrow(()->new GlobalException("Quiz was not found.", HttpStatus.INTERNAL_SERVER_ERROR));
        QuizDTOResponse quizDTOResponse = modelMapper.map(quiz, QuizDTOResponse.class);
        quizDTOResponse.setUpdatedAt(getSimpleDateFormat(quiz));
        return new ResponseEntity<>(quizDTOResponse,HttpStatus.OK);
    }

    public ResponseEntity<List<QuizDTOResponse>> getAllQuizByUser(String idUser){
        GeneralUserAuth userAuth = generalUserAuthService.findUserById(idUser);
        List<Quiz> quizzes = quizRepository.findByGeneralUserAuth(new ObjectId(userAuth.getId())).get();
        List<QuizDTOResponse> quizzesDTO = new ArrayList<QuizDTOResponse>();
        quizzes.forEach(
                quiz -> {
                    QuizDTOResponse quizDTOResponse = modelMapper.map(quiz, QuizDTOResponse.class);
                    quizDTOResponse.setUpdatedAt(getSimpleDateFormat(quiz));
                    quizzesDTO.add(quizDTOResponse);
                });
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
        quiz.setGeneralUserAuth(new ObjectId(userAuth.getId()));
        quiz.setListParam(quizDTO.getListParam());
        quiz.setCreatedAt(new Date());
        quiz.setUpdatedAt(new Date());
        return quiz;
    }

    private String getSimpleDateFormat(Quiz quiz){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a",new Locale("en-US"));
        return sdf.format(quiz.getUpdatedAt());
    }
}
