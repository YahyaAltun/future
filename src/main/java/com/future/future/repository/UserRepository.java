package com.future.future.repository;

import com.future.future.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName,u.lastName=:lastName, u.phoneNumber=:phoneNumber"
            + ", u.email=:email, u.address=:address WHERE u.id=:id")
    void update(@Param("id") Long id, @Param("firstName") String firstName , @Param("lastName") String lastName,
                @Param("phoneNumber") String phoneNumber, @Param("email") String email,
                @Param("address") String address);
}
