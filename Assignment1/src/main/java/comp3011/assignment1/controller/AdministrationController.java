package comp3011.assignment1.controller;

import comp3011.assignment1.dto.UptimeResponse;
import comp3011.assignment1.service.ServerStartTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
public class AdministrationController {

    private final ServerStartTime serverStartTime;

    public AdministrationController(ServerStartTime serverStartTime) {
        this.serverStartTime = serverStartTime;
    }

    @GetMapping("/api/v1/admin/uptime")
    public UptimeResponse getUptime() {
        Instant start = serverStartTime.get();
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        double uptimeSeconds = Duration.between(start, now).toMillis() / 1000.0;

        return new UptimeResponse(start.toString(), now.toString(), uptimeSeconds);
    }
}