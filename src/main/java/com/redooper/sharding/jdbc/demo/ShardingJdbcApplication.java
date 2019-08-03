package com.redooper.sharding.jdbc.demo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.redooper.sharding.jdbc.demo.model.Foo;
import com.redooper.sharding.jdbc.demo.model.User;
import com.redooper.sharding.jdbc.demo.repository.FooRepository;
import com.redooper.sharding.jdbc.demo.repository.UserRepository;
import groovy.lang.Closure;
import groovy.util.Expando;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.util.InlineExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Jackie
 * @Date: 2019-08-01 16:52
 * @Description:
 */
@Slf4j
@PropertySource("sharding-jdbc.properties")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,})
public class ShardingJdbcApplication implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FooRepository fooRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        invokeGroovyShell();

//        invokeSharding();

//        invokeNoSharding();

        invokeSave();
    }

    /**
     * 测试行表达式
     */
    private void invokeGroovyShell() {
        String algorithmExpression = "t_user_$->{String.format(\"%02d\", id % 64)}";
        algorithmExpression = InlineExpressionParser.handlePlaceHolder(algorithmExpression.trim());
        Closure<?> closure = new InlineExpressionParser(algorithmExpression).evaluateClosure();

        Closure<?> result = closure.rehydrate(new Expando(), null, null);
        result.setResolveStrategy(Closure.DELEGATE_ONLY);
        result.setProperty("id", RandomUtil.randomLong(1000000000000000000L));
        log.info("Result = {}", result.call().toString());
    }

    /**
     * 测试数据分片&读写分离
     */
    private void invokeSharding() {
        // 1.增删改操作自动走主库
        User user = User.builder()
                .name(IdUtil.simpleUUID())
                .age(RandomUtil.randomInt(100))
                .build();
        userRepository.save(user);
        log.info("User: {}", JSON.toJSONString(user));

        // 2.默认查询操作走从库
        Iterable<User> users = userRepository.findAll();
        users.forEach(e -> log.info("User: {}", JSON.toJSONString(e)));

        // 3.设置强制主库路由，使查询操作走主库
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            users = userRepository.findAll();
            users.forEach(e -> log.info("User: {}", JSON.toJSONString(e)));
        }

        // 4.强制主库路由关闭后，查询操作依然走从库
        users = userRepository.findAll();
        users.forEach(e -> log.info("User: {}", JSON.toJSONString(e)));
    }

    /**
     * 测试仅读写分离
     */
    private void invokeNoSharding() {
        Foo foo = Foo.builder()
                .name(IdUtil.simpleUUID())
                .age(RandomUtil.randomInt(100))
                .build();
        fooRepository.save(foo);
        log.info("Foo: {}", JSON.toJSONString(foo));

        Iterable<Foo> foos = fooRepository.findAll();
        foos.forEach(e -> log.info("Foo: {}", JSON.toJSONString(e)));

        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            foos = fooRepository.findAll();
            foos.forEach(e -> log.info("Foo: {}", JSON.toJSONString(e)));
        }

        foos = fooRepository.findAll();
        foos.forEach(e -> log.info("Foo: {}", JSON.toJSONString(e)));
    }

    /**
     * 测试分布式主键
     */
    private void invokeSave() throws InterruptedException {
        for (int i = 0; i < 500; i++) {
            User user = User.builder()
                    .name(IdUtil.simpleUUID())
                    .age(RandomUtil.randomInt(100))
                    .build();
            userRepository.save(user);
            log.info("User: {}", JSON.toJSONString(user));
            TimeUnit.MILLISECONDS.sleep(RandomUtil.randomLong(500));
        }
    }
}
