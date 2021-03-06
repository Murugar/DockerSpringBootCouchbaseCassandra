package com.iqmsoft.cb;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Bean
    public Cluster couchbaseCluster(@Value("${cb.conn}") String connectionString) {
        return CouchbaseCluster.fromConnectionString(connectionString);
    }

    @Bean
    public Bucket bucket(Cluster cluster, @Value("${cb.bucket}") String bucket, @Value("${cb.password}") String password) {
        return cluster.openBucket(bucket, password);
    }
}
