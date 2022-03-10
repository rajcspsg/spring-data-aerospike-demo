package com.demo.aerospike.repositories;

import com.demo.aerospike.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
