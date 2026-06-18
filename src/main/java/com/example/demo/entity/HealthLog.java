package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "health_logs")
public class HealthLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 唯一識別碼 [cite: 21]
    
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate; // 紀錄日期 [cite: 21]
    
    @Column(name = "sleep_hours", nullable = false)
    private Double sleepHours; // 睡眠時數 [cite: 21]
    
    @Column(name = "steps", nullable = false)
    private Integer steps; // 當日步數 [cite: 21]
    
    @Column(name = "mood_score", nullable = false)
    private Integer moodScore; // 心情分數 [cite: 22]
    
    @Column(name = "risk_level")
    private String riskLevel; // 風險等級 [cite: 22]

    // Getter and Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public Integer getSteps() { return steps; }
    public void setSteps(Integer steps) { this.steps = steps; }

    public Integer getMoodScore() { return moodScore; }
    public void setMoodScore(Integer moodScore) { this.moodScore = moodScore; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
}