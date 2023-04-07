package com.example.fintech.repositories;

import com.example.fintech.entities.ApiManagement;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiManagementRepository extends MongoRepository<ApiManagement, ObjectId> {
    ApiManagement findByName(String name);

    Page<ApiManagement> getByName(String name, Pageable pageable);
}
