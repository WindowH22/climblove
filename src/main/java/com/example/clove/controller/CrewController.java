package com.example.clove.controller;

import com.example.clove.domain.Crew;
import com.example.clove.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crews")
@RequiredArgsConstructor
public class CrewController {
    
    private final CrewService crewService;
    
    @PostMapping
    public ResponseEntity<Crew> createCrew(@RequestBody CreateCrewRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        Crew crew = crewService.createCrew(
            request.getName(),
            request.getDescription(),
            request.getImageUrl(),
            Long.parseLong(userDetails.getUsername()) // 실제로는 User ID를 가져와야 함
        );
        return ResponseEntity.ok(crew);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Crew> getCrewById(@PathVariable Long id) {
        Crew crew = crewService.findById(id);
        return ResponseEntity.ok(crew);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Crew>> getUserCrews(@PathVariable Long userId) {
        List<Crew> crews = crewService.findByUser(userId);
        return ResponseEntity.ok(crews);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Crew>> searchCrews(@RequestParam String keyword, Pageable pageable) {
        Page<Crew> crews = crewService.searchCrews(keyword, pageable);
        return ResponseEntity.ok(crews);
    }
    
    @GetMapping("/top")
    public ResponseEntity<Page<Crew>> getTopCrews(Pageable pageable) {
        Page<Crew> crews = crewService.findTopCrews(pageable);
        return ResponseEntity.ok(crews);
    }
    
    @PostMapping("/{crewId}/members/{userId}")
    public ResponseEntity<Void> addMember(@PathVariable Long crewId, @PathVariable Long userId) {
        crewService.addMember(crewId, userId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{crewId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long crewId, @PathVariable Long userId) {
        crewService.removeMember(crewId, userId);
        return ResponseEntity.ok().build();
    }
    
    public static class CreateCrewRequest {
        private String name;
        private String description;
        private String imageUrl;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }
}
