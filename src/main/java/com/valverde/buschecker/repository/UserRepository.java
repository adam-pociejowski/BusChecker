package com.valverde.buschecker.repository;

import com.valverde.buschecker.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}