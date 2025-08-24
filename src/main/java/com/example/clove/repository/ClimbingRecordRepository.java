package com.example.clove.repository;

import com.example.clove.domain.ClimbingRecord;
import com.example.clove.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClimbingRecordRepository extends JpaRepository<ClimbingRecord, Long> {
    
    Page<ClimbingRecord> findByUserOrderByClimbedAtDesc(User user, Pageable pageable);
    
    @Query("SELECT cr FROM ClimbingRecord cr WHERE cr.visibility = 'PUBLIC' OR (cr.visibility = 'CREW_ONLY' AND cr.user IN (SELECT u FROM User u JOIN u.crews c WHERE c.id = :crewId)) ORDER BY cr.createdAt DESC")
    Page<ClimbingRecord> findFeedRecords(@Param("crewId") Long crewId, Pageable pageable);
    
    @Query("SELECT cr FROM ClimbingRecord cr WHERE cr.user IN (SELECT u FROM User u JOIN u.crews c WHERE c.id = :crewId) ORDER BY cr.createdAt DESC")
    Page<ClimbingRecord> findByCrewId(@Param("crewId") Long crewId, Pageable pageable);
    
    List<ClimbingRecord> findByUserAndClimbedAtBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT COUNT(cr) FROM ClimbingRecord cr WHERE cr.user = :user AND cr.status = 'SUCCESS' AND cr.route.grade = :grade")
    Long countSuccessfulClimbsByGrade(@Param("user") User user, @Param("grade") String grade);
}
