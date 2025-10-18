package com.example.starter.starterdemo.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class TimeExecutorProperties {

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration inSecondsTime;

}
