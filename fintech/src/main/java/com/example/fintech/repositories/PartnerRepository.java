package com.example.fintech.repositories;

import com.example.fintech.entities.Partner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends MongoRepository<Partner, ObjectId> {
    Partner findPartnerById(ObjectId id);

    Partner findByNameInIgnoreCase(String name);
}
