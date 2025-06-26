package com.github.shitsu.AnkiTelegram.repository;

import com.github.shitsu.AnkiTelegram.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity , Long> {

}
