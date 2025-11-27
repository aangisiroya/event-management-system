package com.example.event.repo;
import com.example.event.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
