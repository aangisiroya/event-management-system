package com.example.event.controller;

import com.example.event.entity.Event;
import com.example.event.entity.Registration;
import com.example.event.repo.EventRepo;
import com.example.event.repo.RegistrationRepo;
import com.example.event.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private RegistrationRepo regRepo;

    @Autowired
    private JwtUtil jwtUtil;

    // extract username
    private String usernameFromHeader(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) throw new RuntimeException("No token");
        return jwtUtil.extractUsername(auth.substring(7));
    }

    // extract role
    private String roleFromHeader(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        return jwtUtil.extractRole(auth.substring(7));
    }

    // ------------------- PUBLIC -------------------
    @GetMapping("/events")
    public List<Event> all(@RequestParam(required = false) String category) {
        if (category == null) return eventRepo.findAll();
        return eventRepo.findByCategory(category);
    }

    // ------------------- ADMIN CREATE -------------------
    @PostMapping("/admin/events")
    public Map<String, String> create(@RequestHeader("Authorization") String auth,
                                      @RequestBody Map<String, String> body) {

        String role = roleFromHeader(auth);
        if (!"ADMIN".equals(role)) return Map.of("error", "only admin can create");

        Event e = new Event();
        e.setTitle(body.get("title"));
        e.setDescription(body.get("description"));
        e.setCategory(body.get("category"));
        e.setLocation(body.get("location"));

        try {
            e.setSeats(Integer.parseInt(body.getOrDefault("seats", "0")));
        } catch (Exception ex) {
            e.setSeats(0);
        }

        // Accept date in YYYY-MM-DD OR YYYY-MM-DDTHH:mm
        String dateStr = body.get("dateTime");
        if (dateStr != null) {
            try {
                LocalDate d = LocalDate.parse(dateStr);
                e.setDateTime(d.atStartOfDay());
            } catch (Exception ex1) {
                try {
                    e.setDateTime(LocalDateTime.parse(dateStr));
                } catch (Exception ex2) {
                    e.setDateTime(null);
                }
            }
        }

        e.setCreatedBy(usernameFromHeader(auth));
        eventRepo.save(e);

        return Map.of("msg", "created");
    }

    // ------------------- ADMIN DELETE -------------------
    @DeleteMapping("/admin/events/{id}")
    public Map<String, String> delete(@RequestHeader("Authorization") String auth,
                                      @PathVariable String id) {

        String role = roleFromHeader(auth);
        if (!"ADMIN".equals(role)) return Map.of("error", "only admin");

        eventRepo.deleteById(id);
        return Map.of("msg", "deleted");
    }

    // ------------------- USER REGISTER -------------------
    @PostMapping("/events/{id}/register")
    public Map<String, String> register(@RequestHeader("Authorization") String auth,
                                        @PathVariable String id) {

        String user = usernameFromHeader(auth);
        Event e = eventRepo.findById(id).orElseThrow();
        long count = regRepo.countByEventId(id);

        if (e.getSeats() > 0 && count >= e.getSeats())
            return Map.of("error", "full");

        Registration r = new Registration();
        r.setEventId(id);
        r.setUsername(user);
        regRepo.save(r);

        return Map.of("msg", "registered");
    }

    // ------------------- ADMIN: VIEW REGISTRATIONS -------------------
    @GetMapping("/admin/events/{id}/registrations")
    public List<Registration> regs(@RequestHeader("Authorization") String auth,
                                   @PathVariable String id) {

        String role = roleFromHeader(auth);
        if (!"ADMIN".equals(role)) throw new RuntimeException("only admin");

        return regRepo.findByEventId(id);
    }

    // ------------------- USER: MY REGISTRATIONS -------------------
    @GetMapping("/events/my")
    public List<Registration> my(@RequestHeader("Authorization") String auth) {
        String u = usernameFromHeader(auth);
        return regRepo.findByUsername(u);
    }
}
