package dev.andrewgao.RunnerTrack.run;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
// import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record Run(
    @Id
    Integer id, 
    @NotEmpty
    String title, 
    LocalDateTime startTime,
    LocalDateTime completeTime,
    @Positive
    Integer miles, 
    Location location
) {

    public Run {
        if(!completeTime.isAfter(startTime)){
            throw new IllegalArgumentException("Start time must be before complete time");
        }
    }

    public Duration getDuration() {
        return Duration.between(startTime,completeTime);
    }

    public Integer getAvgPace() {
        return Math.toIntExact(getDuration().toMinutes() / miles);
    }
}
