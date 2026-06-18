package com.example.demo.repository;

import com.example.demo.entity.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthLogRepository extends JpaRepository<HealthLog, Integer> {
    // 繼承 JpaRepository 即可自動獲得全增刪查改功能
}