package com.example.clove.repository;

import com.example.clove.domain.Crew;
import com.example.clove.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
    
    Optional<Crew> findByName(String name);
    
    @Query("SELECT c FROM Crew c WHERE c.leader = :user OR :user MEMBER OF c.members")
    List<Crew> findByUser(@Param("user") User user);
    
    @Query("SELECT c FROM Crew c WHERE c.status = 'ACTIVE' AND (c.name LIKE %:keyword% OR c.description LIKE %:keyword%)")
    Page<Crew> searchCrews(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT c FROM Crew c WHERE c.status = 'ACTIVE' ORDER BY SIZE(c.members) DESC")
    Page<Crew> findTopCrews(Pageable pageable);
    
    boolean existsByName(String name);
}
