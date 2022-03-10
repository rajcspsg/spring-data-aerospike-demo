package com.demo.aerospike.app;

import com.demo.aerospike.config.AerospikeConfig;
import com.demo.aerospike.entity.User;
import com.demo.aerospike.repositories.UserRepository;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Arrays;

public class MySpringDataAerospikeDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AerospikeConfig.class);
        UserRepository repository = ctx.getBean(UserRepository.class);
        User expireIn5Mins = new User("5minsUser","5minsUser","5minsUser", DateTime.now().plusMinutes(5).getMillis()/1000);
        User expireIn5Hrs = new User("5hrsUsers","5hrsUsers","5hrsUsers", DateTime.now().plusMinutes(5).getMillis()/1000);
        User expireIn5days = new User("5daysUsers","5daysUsers","5daysUsers", DateTime.now().plusMinutes(5).getMillis()/1000);
        User expireIn5mns = new User("expireIn5mns","expireIn5mns","expireIn5mns", DateTime.now().plusMinutes(5).getMillis()/1000);
        repository.saveAll(Arrays.asList(expireIn5Mins, expireIn5Hrs, expireIn5days, expireIn5mns));

    }
}
