package comp3011.assignment1.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class ServerStartTime {

    // Captured when Spring constructs this singleton bean during startup
    private final Instant startTime = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    public Instant get() {
        return startTime;
    }
}