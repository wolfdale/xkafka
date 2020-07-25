package com.transaction.consumer;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class EsConfiguration extends AbstractElasticsearchConfiguration {

    @Value(value = "${elastic.bootstrap.server}")
    private String esServer;

    @Value(value= "${elastic.search.enabled}")
    private boolean isElasticSearchEnabled;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esServer)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    public boolean isElasticSearchEnabled() {
        return this.isElasticSearchEnabled;
    }


}
