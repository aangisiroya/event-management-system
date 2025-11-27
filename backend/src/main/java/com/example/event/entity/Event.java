package com.example.event.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "events")
public class Event {
    @Id private String id;
    private String title;
    private String description;
    private String category; // e.g., Tech, Music, Workshop
    private LocalDateTime dateTime;
    private String location;
    private int seats = 0; // seat limit
    private String createdBy;
    public String getId(){return id;}
    public String getTitle(){return title;}
    public void setTitle(String t){this.title=t;}
    public String getDescription(){return description;}
    public void setDescription(String d){this.description=d;}
    public String getCategory(){return category;}
    public void setCategory(String c){this.category=c;}
    public LocalDateTime getDateTime(){return dateTime;}
    public void setDateTime(LocalDateTime dt){this.dateTime=dt;}
    public String getLocation(){return location;}
    public void setLocation(String l){this.location=l;}
    public int getSeats(){return seats;}
    public void setSeats(int s){this.seats=s;}
    public String getCreatedBy(){return createdBy;}
    public void setCreatedBy(String c){this.createdBy=c;}
}
