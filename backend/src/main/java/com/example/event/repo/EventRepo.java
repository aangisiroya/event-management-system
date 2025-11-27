package com.example.event.repo;
import com.example.event.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface EventRepo extends MongoRepository<Event, String> {
    List<Event> findByCategory(String category);
}
