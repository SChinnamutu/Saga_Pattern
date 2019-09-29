package info.novatec.axon.config;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregateConfig {
	/*
	 * @Bean public EventStorageEngine eventStore(MongoClient client) { return new
	 * MongoEventStorageEngine(new DefaultMongoTemplate(client)); }
	 */
    
    @Bean
    public EventStorageEngine eventStore() {
        return new InMemoryEventStorageEngine();
    }

}
