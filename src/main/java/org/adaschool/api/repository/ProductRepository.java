package org.adaschool.api.repository;

import org.adaschool.api.repository.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<User, Long> {
}