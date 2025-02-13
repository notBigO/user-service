package com.userservice.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.user.entities.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
