package com.crud.employeecrudapp.repo;

import com.crud.employeecrudapp.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity,Long> {

    DepartmentEntity findByName(String name);
}
