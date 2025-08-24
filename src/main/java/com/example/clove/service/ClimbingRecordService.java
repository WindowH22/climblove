package com.example.clove.service;

import com.example.clove.domain.ClimbingRecord;
import com.example.clove.domain.Route;
import com.example.clove.domain.User;
import com.example.clove.repository.ClimbingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClimbingRecordService {
    
    private final ClimbingRecordRepository climbingRecordRepository;
    private final UserService userService;
    private final RouteService routeService;
    
    @Transactional
    public ClimbingRecord createRecord(Long userId, Long routeId, ClimbingRecord.ClimbingStatus status,
                                     Integer attemptCount, String memo, List<String> images,
                                     List<String> videos, ClimbingRecord.Visibility visibility,
                                     LocalDateTime climbedAt) {
        
        User user = userService.findById(userId);
        Route route = routeService.findById(routeId);
        
        ClimbingRecord record = new ClimbingRecord();
        record.setUser(user);
        record.setRoute(route);
        record.setStatus(status);
        record.setAttemptCount(attemptCount);
        record.setMemo(memo);
        record.setImages(images);
        record.setVideos(videos);
        record.setVisibility(visibility);
        record.setClimbedAt(climbedAt);
        
        return climbingRecordRepository.save(record);
    }
    
    public Page<ClimbingRecord> getUserRecords(Long userId, Pageable pageable) {
        User user = userService.findById(userId);
        return climbingRecordRepository.findByUserOrderByClimbedAtDesc(user, pageable);
    }
    
    public Page<ClimbingRecord> getFeedRecords(Long crewId, Pageable pageable) {
        return climbingRecordRepository.findFeedRecords(crewId, pageable);
    }
    
    public Page<ClimbingRecord> getCrewRecords(Long crewId, Pageable pageable) {
        return climbingRecordRepository.findByCrewId(crewId, pageable);
    }
    
    public List<ClimbingRecord> getUserRecordsByPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userService.findById(userId);
        return climbingRecordRepository.findByUserAndClimbedAtBetween(user, startDate, endDate);
    }
    
    public Long countSuccessfulClimbsByGrade(Long userId, String grade) {
        User user = userService.findById(userId);
        return climbingRecordRepository.countSuccessfulClimbsByGrade(user, grade);
    }
}
