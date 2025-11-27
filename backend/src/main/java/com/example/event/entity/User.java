package com.example.event.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
public class User {
    @Id private String id;
    private String username;
    private String password;
    private String role = "USER";
    public String getId(){return id;}
    public String getUsername(){return username;}
    public void setUsername(String u){this.username=u;}
    public String getPassword(){return password;}
    public void setPassword(String p){this.password=p;}
    public String getRole(){return role;}
    public void setRole(String r){this.role=r;}
}
