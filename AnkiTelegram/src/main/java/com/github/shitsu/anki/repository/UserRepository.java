package com.github.shitsu.anki.repository;

import com.github.shitsu.anki.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity , Long> {

}
