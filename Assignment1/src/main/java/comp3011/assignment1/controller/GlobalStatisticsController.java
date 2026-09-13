package comp3011.assignment1.controller;

import comp3011.assignment1.dto.GlobalStatsResponse;
import comp3011.assignment1.service.GlobalStatsTracker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalStatisticsController {

    private final GlobalStatsTracker statsTracker;

    public GlobalStatisticsController(GlobalStatsTracker statsTracker) {
        this.statsTracker = statsTracker;
    }

    @GetMapping("/api/v1/global/stats")
    public GlobalStatsResponse getGlobalStats() {
        return new GlobalStatsResponse(statsTracker.getInputTokens(), statsTracker.getOutputTokens());
    }
}