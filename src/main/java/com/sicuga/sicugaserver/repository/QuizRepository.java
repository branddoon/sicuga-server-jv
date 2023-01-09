package com.sicuga.sicugaserver.repository;

import com.sicuga.sicugaserver.entity.Quiz;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends MongoRepository<Quiz,String> {

    Optional<List<Quiz>>findByGeneralUserAuth(ObjectId id);
}
