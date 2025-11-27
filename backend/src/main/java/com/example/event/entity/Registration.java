package com.example.event.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "registrations")
public class Registration {
    @Id private String id;
    private String eventId;
    private String username;
    private LocalDateTime registeredAt = LocalDateTime.now();
    public String getId(){return id;}
    public String getEventId(){return eventId;}
    public void setEventId(String e){this.eventId=e;}
    public String getUsername(){return username;}
    public void setUsername(String u){this.username=u;}
    public LocalDateTime getRegisteredAt(){return registeredAt;}
    public void setRegisteredAt(LocalDateTime t){this.registeredAt=t;}
}
