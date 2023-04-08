package br.alessi.health.control;

import br.alessi.health.entity.HealthCheck;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HealthRepository implements PanacheRepository<HealthCheck> {
}
