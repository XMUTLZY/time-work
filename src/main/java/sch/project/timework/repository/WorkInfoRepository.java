package sch.project.timework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sch.project.timework.domain.WorkInfoEntity;
import java.util.List;

@Repository
public interface WorkInfoRepository extends JpaRepository<WorkInfoEntity, Integer> {
    Page<WorkInfoEntity> findAllByUserId(Pageable pageable, Integer userId);
    int countAllByUserId(Integer userId);
    List<WorkInfoEntity> findAllByUserId(Integer userId);
}
