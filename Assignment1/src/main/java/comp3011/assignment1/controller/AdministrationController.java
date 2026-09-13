package comp3011.assignment1.controller;

import comp3011.assignment1.dto.ErrorResponse;
import comp3011.assignment1.dto.ShutdownResponse;
import comp3011.assignment1.dto.UptimeResponse;
import comp3011.assignment1.service.ServerStartTime;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class AdministrationController {

    private final ServerStartTime serverStartTime;
    private final ConfigurableApplicationContext applicationContext;
    private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);

    public AdministrationController(ServerStartTime serverStartTime,
                                     ConfigurableApplicationContext applicationContext) {
        this.serverStartTime = serverStartTime;
        this.applicationContext = applicationContext;
    }

    @GetMapping("/api/v1/admin/uptime")
    public UptimeResponse getUptime() {
        Instant start = serverStartTime.get();
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        double uptimeSeconds = Duration.between(start, now).toMillis() / 1000.0;
        return new UptimeResponse(start.toString(), now.toString(), uptimeSeconds);
    }

    @PostMapping("/api/v1/admin/shutdown")
    public ResponseEntity<?> shutdown() {
        if (!shutdownRequested.compareAndSet(false, true)) {
            ErrorResponse error = new ErrorResponse(
                    Instant.now().truncatedTo(ChronoUnit.MILLIS).toString(),
                    409,
                    "Conflict",
                    "Graceful shutdown is already in progress.",
                    "/api/v1/admin/shutdown"
            );
            return ResponseEntity.status(409).body(error);
        }

        // Run the actual shutdown on a separate thread, after a short delay,
        // so this response has time to actually reach the client first.
        Thread shutdownThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            applicationContext.close();
        });
        shutdownThread.start();

        return ResponseEntity.status(202).body(new ShutdownResponse("Graceful shutdown requested."));
    }
}