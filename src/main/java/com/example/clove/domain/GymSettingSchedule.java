package com.example.clove.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "gym_setting_schedules")
@Getter
@Setter
@NoArgsConstructor
public class GymSettingSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
    private SettingType type;
    
    @Column(nullable = false)
    private Integer routeCount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.SCHEDULED;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum SettingType {
        NEW_SETTING, PARTIAL_CHANGE, MAINTENANCE
    }
    
    public enum ScheduleStatus {
        SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    }
}
