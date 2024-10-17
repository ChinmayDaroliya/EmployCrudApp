package com.crud.employeecrudapp.service;

import com.crud.employeecrudapp.entity.DepartmentEntity;
import com.crud.employeecrudapp.entity.EmployEntity;
import com.crud.employeecrudapp.repo.DepartmentRepo;
import com.crud.employeecrudapp.repo.EmployRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployService {
    @Autowired
    private EmployRepo employRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    public List<EmployEntity> getAll(){
        return employRepo.findAll();
    }

//    public EmployEntity save(EmployEntity employ){
//        return employRepo.save(employ);
//    }

    public Optional<EmployEntity> getById(Long id){
        return employRepo.findById(id);
    }

    public EmployEntity update(Long id, EmployEntity newEmploy){
        Optional<EmployEntity> oldEmploy = employRepo.findById(id);
        if(oldEmploy.isPresent()){
            EmployEntity existingEmploy = oldEmploy.get();
            existingEmploy.setFirstName(newEmploy.getFirstName());
            existingEmploy.setLastName(newEmploy.getLastName());
            existingEmploy.setPrimaryNumber(newEmploy.getPrimaryNumber());
            existingEmploy.setAlternateNumber(newEmploy.getAlternateNumber());
            existingEmploy.setEmail(newEmploy.getEmail());
            existingEmploy.setDateOfJoining(newEmploy.getDateOfJoining());
            existingEmploy.setSalary(newEmploy.getSalary());

            String newDepartmentName = newEmploy.getDepartment().getName();
            DepartmentEntity existingDepartment = departmentRepo.findByName(newDepartmentName);

            if(existingDepartment!=null){
                existingEmploy.setDepartment(existingDepartment);
            }else{
                departmentRepo.save(newEmploy.getDepartment());
                existingEmploy.setDepartment(newEmploy.getDepartment());
            }
            return employRepo.save(existingEmploy);
        }
        return  null;
    }

    public boolean delete(Long id){
        if(employRepo.existsById(id)){
            employRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public EmployEntity createEmploy(EmployEntity employ){
//        null exception check
        if(employ.getDepartment()==null||employ.getDepartment().getName()==null){
            throw new IllegalArgumentException("department info is requires");
        }
        String departmentName = employ.getDepartment().getName();
        DepartmentEntity existingDepartment = departmentRepo.findByName(departmentName);

        if(existingDepartment!=null){
            existingDepartment.setName(employ.getDepartment().getName());
            existingDepartment.setLocation(employ.getDepartment().getLocation());
            existingDepartment.setRole(employ.getDepartment().getRole());
            employ.setDepartment(existingDepartment);
        }else{
            existingDepartment = employ.getDepartment();
            departmentRepo.save(existingDepartment);
            employ.setDepartment(existingDepartment);
        }
        return employRepo.save(employ);
    }


}
