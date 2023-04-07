package com.example.fintech.repositories;

import com.example.fintech.entities.AccessPermission;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessPermissionRepository extends MongoRepository<AccessPermission, ObjectId> {
    AccessPermission findByName(String name);

    Page<AccessPermission> getByName(String name, Pageable pageable);
}
