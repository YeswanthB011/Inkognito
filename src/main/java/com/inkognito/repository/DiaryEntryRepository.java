package com.inkognito.repository;

import com.inkognito.model.DiaryEntry;
import com.inkognito.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
    
    List<DiaryEntry> findByUserOrderByCreatedAtDesc(User user);
    
    List<DiaryEntry> findByUserAndCategoryOrderByCreatedAtDesc(User user, DiaryEntry.Category category);
    
    Page<DiaryEntry> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    Page<DiaryEntry> findByUserAndCategoryOrderByCreatedAtDesc(User user, DiaryEntry.Category category, Pageable pageable);
    
    @Query("SELECT e FROM DiaryEntry e WHERE e.user = :user AND e.createdAt >= :startDate ORDER BY e.createdAt DESC")
    List<DiaryEntry> findRecentEntriesByUser(@Param("user") User user, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(e) FROM DiaryEntry e WHERE e.user = :user AND e.category = :category")
    long countByUserAndCategory(@Param("user") User user, @Param("category") DiaryEntry.Category category);
}