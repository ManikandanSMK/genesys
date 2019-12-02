package com.genesys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genesys.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public long countByEmail(String email);

	public long deleteByEmail(String email);

}
