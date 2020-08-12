package sch.project.timework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sch.project.timework.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findDistinctByPhoneAndPassword(String phone, String password);
}
