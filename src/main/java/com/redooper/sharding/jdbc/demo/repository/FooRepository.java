package com.redooper.sharding.jdbc.demo.repository;

import com.redooper.sharding.jdbc.demo.model.Foo;
import org.springframework.data.repository.CrudRepository;

/**
 * @Auther: Jackie
 * @Date: 2019-08-02 12:49
 * @Description:
 */
public interface FooRepository extends CrudRepository<Foo, Long> {
}
