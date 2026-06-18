package com.example.demo.controller;

import com.example.demo.entity.HealthLog;
import com.example.demo.repository.HealthLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-logs")
@CrossOrigin(origins = "*") // 支援前端跨域連線
public class HealthLogController {

    @Autowired
    private HealthLogRepository healthLogRepository;

    // 1. 取得所有健康日誌紀錄 [cite: 30]
    @GetMapping
    public List<HealthLog> getAllLogs() {
        return healthLogRepository.findAll();
    }

    // 2. 新增一筆健康日誌（送出時自動執行多層決策樹分類） [cite: 30, 97]
    @HttpPost
    public HealthLog createLog(@RequestBody HealthLog log) {
        // 呼叫核心決策樹方法，判定風險後才寫入資料庫
        String computedRisk = calculateDecisionTree(log.getSleepHours(), log.getSteps(), log.getMoodScore());
        log.setRiskLevel(computedRisk);
        return healthLogRepository.save(log);
    }

    /**
     * 核心多層分支決策樹演算法 (非單一 if 判斷) [cite: 14]
     * 嚴格符合：睡眠時數 -> 當日步數 -> 心情分數 的層次切分 [cite: 14, 97]
     */
    private String calculateDecisionTree(double sleep, int steps, int mood) {
        // 第一層分支：判斷睡眠時數是否嚴重不足 [cite: 14, 25]
        if (sleep < 5.5) {
            // 第二層分支：在睡眠不足的情況下，判斷活動量（步數）是否也偏低 [cite: 14, 25]
            if (steps < 3500) {
                // 第三層分支：在睡眠、步數皆差的情況下，判斷心情是否也低落 [cite: 14, 25]
                if (mood <= 4) {
                    return "HIGH"; // 🔴 三者皆差 -> 高風險 [cite: 25]
                } else {
                    return "MEDIUM"; // 🟡 雖然睡眠步數差，但心情尚可 -> 中風險混合組 [cite: 25, 97]
                }
            } else {
                // 睡眠不足但有在運動走動
                return mood <= 4 ? "HIGH" : "MEDIUM";
            }
        } else if (sleep >= 7.0) { // 睡眠充足組 [cite: 25]
            // 第二層分支：睡眠充足下看步數與心情
            if (steps >= 6000 && mood >= 6) {
                return "LOW"; // 🟢 三項指標皆好 -> 低風險 [cite: 25]
            } else {
                return "MEDIUM"; // 有指標下滑 -> 中風險 [cite: 25, 97]
            }
        } else {
            // 中間普通組 (睡眠 5.5 ~ 7.0 小時之間)
            return "MEDIUM"; // 🟡 中風險 [cite: 25]
        }
    }
}