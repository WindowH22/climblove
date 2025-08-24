package com.example.clove.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "climbing_records")
@Getter
@Setter
@NoArgsConstructor
public class ClimbingRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClimbingStatus status;
    
    @Column(nullable = false)
    private Integer attemptCount;
    
    @Column(columnDefinition = "TEXT")
    private String memo;
    
    @ElementCollection
    @CollectionTable(name = "climbing_record_images", joinColumns = @JoinColumn(name = "record_id"))
    @Column(name = "image_url")
    private List<String> images;
    
    @ElementCollection
    @CollectionTable(name = "climbing_record_videos", joinColumns = @JoinColumn(name = "record_id"))
    @Column(name = "video_url")
    private List<String> videos;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.PUBLIC;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime climbedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum ClimbingStatus {
        SUCCESS, FAIL, IN_PROGRESS
    }
    
    public enum Visibility {
        PUBLIC, CREW_ONLY, PRIVATE
    }
}
