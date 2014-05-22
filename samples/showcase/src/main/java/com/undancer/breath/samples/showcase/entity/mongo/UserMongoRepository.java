package com.undancer.breath.samples.showcase.entity.mongo;

import com.undancer.breath.samples.showcase.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by undancer on 14-5-22.
 */
public interface UserMongoRepository extends MongoRepository<User, Long> {
}
