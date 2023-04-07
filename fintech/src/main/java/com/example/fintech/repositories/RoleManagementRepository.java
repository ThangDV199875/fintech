package com.example.fintech.repositories;

import com.example.fintech.entities.RoleManagement;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleManagementRepository extends MongoRepository<RoleManagement, ObjectId> {
    RoleManagement findByName(String name);

    Page<RoleManagement> getByName(String name, Pageable pageable);
}
