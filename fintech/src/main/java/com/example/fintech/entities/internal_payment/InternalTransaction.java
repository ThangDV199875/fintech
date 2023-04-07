package com.example.fintech.entities.internal_payment;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "internal_transaction")
@Data
public class InternalTransaction {
    @Id
    ObjectId id;

    String name;

    String transactionCode;

    String orderInfo;

    String returnUrl;

    String price;

    String accessKey;


}
