package com.crud.employeecrudapp.repo;

import com.crud.employeecrudapp.entity.EmployEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployRepo extends JpaRepository<EmployEntity,Long> {

}
