package br.alessi.health.control;

import br.alessi.health.entity.HealthCheck;
import br.alessi.health.entity.HealthStatus;
import br.alessi.health.entity.dto.HealthDto;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class HealthService {

    @Inject
    HealthRepository healthRepository;

    @Inject
    MongoClient mongoClient;

    @Inject
    @Channel("health-out")
    Emitter<String> emitter;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public HealthDto getHealthStatus() {
        HealthStatus postgresStatus = getPostgresStatus();
        HealthCheck healthCheck = HealthCheck.builder()
                .postgres(postgresStatus)
                .mongo(getMongoDbStatus())
                .rabbitMQ(getActiveMqStatus())
                .time(LocalDateTime.now())
                .build();
        if (postgresStatus == HealthStatus.HEALTHY) {
            healthRepository.persistAndFlush(healthCheck);
        }
        return modelMapper.map(healthCheck, HealthDto.class);
    }

    private HealthStatus getPostgresStatus() {
        try {
            healthRepository.findByIdOptional(0l);
            return HealthStatus.HEALTHY;
        } catch (Exception e) {
            return HealthStatus.UNHEALTHY;
        }
    }

    private HealthStatus getMongoDbStatus() {
        try {
            Document document = new Document()
                    .append("health", "health");
            mongoClient.getDatabase("health").getCollection("health").insertOne(document);
            return HealthStatus.HEALTHY;
        } catch (Exception e) {
            return HealthStatus.UNHEALTHY;
        }
    }


    private HealthStatus getActiveMqStatus() {
        try {
            emitter.send("HEALTH");
            return HealthStatus.HEALTHY;
        } catch (Exception e) {
            return HealthStatus.UNAVAILABLE;
        }
    }

    @Incoming("health")
    public CompletionStage<Void> toUpperCase(Message<String> message) {
        System.out.println(message.getPayload());
        return message.ack();
    }
}
