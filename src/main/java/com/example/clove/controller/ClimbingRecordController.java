package com.example.clove.controller;

import com.example.clove.domain.ClimbingRecord;
import com.example.clove.service.ClimbingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/climbing-records")
@RequiredArgsConstructor
public class ClimbingRecordController {
    
    private final ClimbingRecordService climbingRecordService;
    
    @PostMapping
    public ResponseEntity<ClimbingRecord> createRecord(@RequestBody CreateRecordRequest request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        ClimbingRecord record = climbingRecordService.createRecord(
            Long.parseLong(userDetails.getUsername()), // 실제로는 User ID를 가져와야 함
            request.getRouteId(),
            request.getStatus(),
            request.getAttemptCount(),
            request.getMemo(),
            request.getImages(),
            request.getVideos(),
            request.getVisibility(),
            request.getClimbedAt()
        );
        return ResponseEntity.ok(record);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ClimbingRecord>> getUserRecords(@PathVariable Long userId, Pageable pageable) {
        Page<ClimbingRecord> records = climbingRecordService.getUserRecords(userId, pageable);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/feed")
    public ResponseEntity<Page<ClimbingRecord>> getFeedRecords(@RequestParam(required = false) Long crewId,
                                                             Pageable pageable) {
        Page<ClimbingRecord> records = climbingRecordService.getFeedRecords(crewId, pageable);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/crew/{crewId}")
    public ResponseEntity<Page<ClimbingRecord>> getCrewRecords(@PathVariable Long crewId, Pageable pageable) {
        Page<ClimbingRecord> records = climbingRecordService.getCrewRecords(crewId, pageable);
        return ResponseEntity.ok(records);
    }
    
    public static class CreateRecordRequest {
        private Long routeId;
        private ClimbingRecord.ClimbingStatus status;
        private Integer attemptCount;
        private String memo;
        private List<String> images;
        private List<String> videos;
        private ClimbingRecord.Visibility visibility;
        private LocalDateTime climbedAt;
        
        // Getters and Setters
        public Long getRouteId() { return routeId; }
        public void setRouteId(Long routeId) { this.routeId = routeId; }
        public ClimbingRecord.ClimbingStatus getStatus() { return status; }
        public void setStatus(ClimbingRecord.ClimbingStatus status) { this.status = status; }
        public Integer getAttemptCount() { return attemptCount; }
        public void setAttemptCount(Integer attemptCount) { this.attemptCount = attemptCount; }
        public String getMemo() { return memo; }
        public void setMemo(String memo) { this.memo = memo; }
        public List<String> getImages() { return images; }
        public void setImages(List<String> images) { this.images = images; }
        public List<String> getVideos() { return videos; }
        public void setVideos(List<String> videos) { this.videos = videos; }
        public ClimbingRecord.Visibility getVisibility() { return visibility; }
        public void setVisibility(ClimbingRecord.Visibility visibility) { this.visibility = visibility; }
        public LocalDateTime getClimbedAt() { return climbedAt; }
        public void setClimbedAt(LocalDateTime climbedAt) { this.climbedAt = climbedAt; }
    }
}
