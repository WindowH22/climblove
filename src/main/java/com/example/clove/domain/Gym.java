package com.example.clove.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gyms")
@Getter
@Setter
@NoArgsConstructor
public class Gym {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private String address;
    
    private String phoneNumber;
    
    private String website;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(nullable = false)
    private Integer routeChangeCycle; // 루트 변경 주기 (일)
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GymStatus status = GymStatus.ACTIVE;
    
    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Route> routes;
    
    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<GymSettingSchedule> settingSchedules;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum GymStatus {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}
