package com.example.clove.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "crews")
@Getter
@Setter
@NoArgsConstructor
public class Crew {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private String imageUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;
    
    @ManyToMany
    @JoinTable(
        name = "crew_members",
        joinColumns = @JoinColumn(name = "crew_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members;
    
    @OneToMany(mappedBy = "crew", cascade = CascadeType.ALL)
    private List<CrewPost> posts;
    
    @OneToMany(mappedBy = "crew", cascade = CascadeType.ALL)
    private List<CrewMeeting> meetings;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CrewStatus status = CrewStatus.ACTIVE;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum CrewStatus {
        ACTIVE, INACTIVE, PRIVATE
    }
}
