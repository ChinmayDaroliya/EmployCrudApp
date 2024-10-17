package com.crud.employeecrudapp.service;

import com.crud.employeecrudapp.entity.DepartmentEntity;
import com.crud.employeecrudapp.entity.EmployEntity;
import com.crud.employeecrudapp.repo.DepartmentRepo;
import com.crud.employeecrudapp.repo.EmployRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public List<DepartmentEntity> getAll(){
        return departmentRepo.findAll();
    }

    public DepartmentEntity save(DepartmentEntity department){
        return departmentRepo.save(department);
    }

    public Optional<DepartmentEntity> getById(Long id){
        return departmentRepo.findById(id);
    }

    public DepartmentEntity update(Long id, DepartmentEntity newDepartment){
        Optional<DepartmentEntity> oldDepartment = departmentRepo.findById(id);
        if(oldDepartment.isPresent()){
            DepartmentEntity existingDepartment = oldDepartment.get();
            existingDepartment.setName(newDepartment.getName());
            existingDepartment.setLocation(newDepartment.getLocation());
            existingDepartment.setRole(newDepartment.getRole());
            return  departmentRepo.save(existingDepartment);
        }
        return  null;
    }

    public boolean delete(Long id){
        if(departmentRepo.existsById(id)){
            departmentRepo.deleteById(id);
            return true;
        }
        return false;
    }


}
