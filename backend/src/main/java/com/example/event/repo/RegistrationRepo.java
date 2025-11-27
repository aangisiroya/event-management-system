package com.example.event.repo;
import com.example.event.entity.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface RegistrationRepo extends MongoRepository<Registration, String> {
    List<Registration> findByEventId(String eventId);
    List<Registration> findByUsername(String username);
    long countByEventId(String eventId);
}
