package com.example.fintech.repositories;

import com.example.fintech.entities.internal_payment.InternalCoin;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalCoinRepository extends MongoRepository<InternalCoin, ObjectId> {
    InternalCoin findByUserId(ObjectId userId);

    Page<InternalCoin> findByUserIdOrPartnerId(ObjectId userId, ObjectId partnerId, Pageable pageable);
}
