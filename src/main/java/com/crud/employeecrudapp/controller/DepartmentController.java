package com.crud.employeecrudapp.controller;

import com.crud.employeecrudapp.entity.DepartmentEntity;
import com.crud.employeecrudapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping
    public List<DepartmentEntity> getAll(){
        return departmentService.getAll();
    }
    @PostMapping
    public DepartmentEntity post (@RequestBody DepartmentEntity department){
        return departmentService.save(department);
    }

    @GetMapping("/{id}")
    public Optional<DepartmentEntity> getById(@PathVariable Long id){
        return departmentService.getById(id);
    }
    @PutMapping("/{id}")
    public DepartmentEntity put(@PathVariable Long id , @RequestBody DepartmentEntity newDepartment){
        return departmentService.update(id,newDepartment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean deleted = departmentService.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
