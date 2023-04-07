package com.example.fintech.repositories;

import com.example.fintech.entities.momo.MoMoPayment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoMoPaymentRepository extends MongoRepository<MoMoPayment, ObjectId> {
}
