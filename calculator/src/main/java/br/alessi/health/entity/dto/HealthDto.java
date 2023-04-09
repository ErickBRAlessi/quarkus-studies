package br.alessi.health.entity.dto;

import br.alessi.health.entity.HealthStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthDto {

    private long id;
    private LocalDateTime time;
    private HealthStatus postgres;
    private HealthStatus mongo;
    private HealthStatus rabbitMQ;

}
