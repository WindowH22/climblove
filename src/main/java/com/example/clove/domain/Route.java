package com.example.clove.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
public class Route {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String grade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;
    
    @Column(nullable = false)
    private LocalDateTime settingDate;
    
    @Column(nullable = false)
    private LocalDateTime removalDate;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RouteType type;
    
    @Column(nullable = false)
    private Integer holdCount;
    
    @ElementCollection
    @CollectionTable(name = "route_images", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "image_url")
    private List<String> images;
    
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<ClimbingRecord> climbingRecords;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum RouteType {
        BOULDER, LEAD, TOP_ROPE
    }
}
