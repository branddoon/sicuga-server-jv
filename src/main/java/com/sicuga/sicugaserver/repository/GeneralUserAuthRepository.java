package com.sicuga.sicugaserver.repository;

import com.sicuga.sicugaserver.entity.GeneralUserAuth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralUserAuthRepository extends MongoRepository<GeneralUserAuth,String> {

    Boolean existsByEmail(String email);

    Optional<GeneralUserAuth> findByEmail(String email);

}
