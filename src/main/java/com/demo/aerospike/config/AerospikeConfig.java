package com.demo.aerospike.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.QueryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.aerospike.convert.AerospikeTypeAliasAccessor;
import org.springframework.data.aerospike.convert.MappingAerospikeConverter;
import org.springframework.data.aerospike.core.AerospikeExceptionTranslator;
import org.springframework.data.aerospike.core.AerospikeTemplate;
import org.springframework.data.aerospike.mapping.AerospikeMappingContext;
import org.springframework.data.aerospike.mapping.AerospikeSimpleTypes;
import org.springframework.data.aerospike.query.QueryEngine;
import org.springframework.data.aerospike.query.StatementBuilder;
import org.springframework.data.aerospike.query.cache.IndexInfoParser;
import org.springframework.data.aerospike.query.cache.IndexRefresher;
import org.springframework.data.aerospike.query.cache.IndexesCacheHolder;
import org.springframework.data.aerospike.query.cache.InternalIndexOperations;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;
import org.springframework.data.convert.CustomConversions;

import java.util.Collections;
import static org.springframework.data.convert.CustomConversions.StoreConversions.of;

@Configuration
@EnableAerospikeRepositories(basePackages = "com.demo.aerospike.repositories")
public class AerospikeConfig {

    public @Bean(destroyMethod = "close") AerospikeClient aerospikeClient() {

        ClientPolicy policy = new ClientPolicy();
        policy.failIfNotConnected = true;
        return new AerospikeClient(policy, "localhost", 3000);
    }

    public @Bean(name = "aerospikeMappingContext") AerospikeMappingContext getAerospikeMappingContext() {
        return  new AerospikeMappingContext();
    }
    public @Bean AerospikeTemplate aerospikeTemplate() {
        CustomConversions conversions = new CustomConversions(of(AerospikeSimpleTypes.HOLDER, Collections.emptyList()), Collections.emptyList());
        AerospikeTypeAliasAccessor accessor = new AerospikeTypeAliasAccessor();
        AerospikeMappingContext  context = getAerospikeMappingContext();
        MappingAerospikeConverter converter = new MappingAerospikeConverter(context, conversions, accessor);
        context.setDefaultNameSpace("test");
        IndexesCacheHolder indexesCacheHolder = new IndexesCacheHolder();
        StatementBuilder statementBuilder = new StatementBuilder(indexesCacheHolder);
        AerospikeClient aerospikeClient = aerospikeClient();
        QueryEngine queryEngine = new QueryEngine(aerospikeClient, statementBuilder, new QueryPolicy());

        IndexRefresher refresher = new IndexRefresher(aerospikeClient, aerospikeClient.getInfoPolicyDefault(), new InternalIndexOperations(new IndexInfoParser()), indexesCacheHolder);
        refresher.refreshIndexes();
        return  new AerospikeTemplate(aerospikeClient, "test", converter, context, ex -> new DataAccessException(ex.getMessage()) {}, queryEngine, refresher);
    }
}