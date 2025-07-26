package com.inkognito.service;

import com.inkognito.model.DiaryEntry;
import com.inkognito.model.User;
import com.inkognito.repository.DiaryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiaryEntryService {
    
    @Autowired
    private DiaryEntryRepository diaryEntryRepository;
    
    public DiaryEntry createEntry(User user, DiaryEntry.Category category, String title, String content, Integer rating, Integer durationMinutes) {
        DiaryEntry entry = new DiaryEntry(user, category, title);
        entry.setContent(content);
        entry.setRating(rating);
        entry.setDurationMinutes(durationMinutes);
        return diaryEntryRepository.save(entry);
    }
    
    public Optional<DiaryEntry> findById(Long id) {
        return diaryEntryRepository.findById(id);
    }
    
    public List<DiaryEntry> findByUser(User user) {
        return diaryEntryRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<DiaryEntry> findByUserAndCategory(User user, DiaryEntry.Category category) {
        return diaryEntryRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, category);
    }
    
    public Page<DiaryEntry> findByUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return diaryEntryRepository.findByUserOrderByCreatedAtDesc(user, pageable);
    }
    
    public Page<DiaryEntry> findByUserAndCategory(User user, DiaryEntry.Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return diaryEntryRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, category, pageable);
    }
    
    public List<DiaryEntry> findRecentEntries(User user, int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return diaryEntryRepository.findRecentEntriesByUser(user, startDate);
    }
    
    public long countByUserAndCategory(User user, DiaryEntry.Category category) {
        return diaryEntryRepository.countByUserAndCategory(user, category);
    }
    
    public DiaryEntry updateEntry(Long id, String title, String content, Integer rating, Integer durationMinutes) {
        DiaryEntry entry = diaryEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        
        entry.setTitle(title);
        entry.setContent(content);
        entry.setRating(rating);
        entry.setDurationMinutes(durationMinutes);
        return diaryEntryRepository.save(entry);
    }
    
    public void deleteEntry(Long id) {
        diaryEntryRepository.deleteById(id);
    }
    
    public DiaryEntry save(DiaryEntry entry) {
        return diaryEntryRepository.save(entry);
    }
}