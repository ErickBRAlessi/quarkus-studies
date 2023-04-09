package br.alessi.health.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheck extends PanacheEntity {

    private LocalDateTime time;
    private HealthStatus postgres;
    private HealthStatus mongo;
    private HealthStatus rabbitMQ;

}
