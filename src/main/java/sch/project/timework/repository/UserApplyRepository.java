package sch.project.timework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sch.project.timework.domain.UserApplyEntity;
import java.util.List;

@Repository
public interface UserApplyRepository extends JpaRepository<UserApplyEntity, Integer> {
    List<UserApplyEntity> findAllByWorkIdAndUserId(Integer workId, Integer userId);
    Page<UserApplyEntity> findAllByUserId(Pageable pageable, Integer userId);
    Page<UserApplyEntity> findAllByWorkIdIn(Pageable pageable, List<Integer> workIdList);
}
