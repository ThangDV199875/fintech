package com.example.fintech.repositories;

import com.example.fintech.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);

    User findUserById(ObjectId id);
}
