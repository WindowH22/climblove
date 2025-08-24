package com.example.clove.service;

import com.example.clove.domain.Crew;
import com.example.clove.domain.User;
import com.example.clove.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewService {
    
    private final CrewRepository crewRepository;
    private final UserService userService;
    
    @Transactional
    public Crew createCrew(String name, String description, String imageUrl, Long leaderId) {
        if (crewRepository.existsByName(name)) {
            throw new IllegalArgumentException("Crew name already exists");
        }
        
        User leader = userService.findById(leaderId);
        
        Crew crew = new Crew();
        crew.setName(name);
        crew.setDescription(description);
        crew.setImageUrl(imageUrl);
        crew.setLeader(leader);
        
        return crewRepository.save(crew);
    }
    
    public Crew findById(Long id) {
        return crewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Crew not found with id: " + id));
    }
    
    public List<Crew> findByUser(Long userId) {
        User user = userService.findById(userId);
        return crewRepository.findByUser(user);
    }
    
    public Page<Crew> searchCrews(String keyword, Pageable pageable) {
        return crewRepository.searchCrews(keyword, pageable);
    }
    
    public Page<Crew> findTopCrews(Pageable pageable) {
        return crewRepository.findTopCrews(pageable);
    }
    
    @Transactional
    public void addMember(Long crewId, Long userId) {
        Crew crew = findById(crewId);
        User user = userService.findById(userId);
        
        Set<User> members = crew.getMembers();
        members.add(user);
        crew.setMembers(members);
        
        crewRepository.save(crew);
    }
    
    @Transactional
    public void removeMember(Long crewId, Long userId) {
        Crew crew = findById(crewId);
        User user = userService.findById(userId);
        
        Set<User> members = crew.getMembers();
        members.remove(user);
        crew.setMembers(members);
        
        crewRepository.save(crew);
    }
}
