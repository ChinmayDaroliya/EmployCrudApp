package com.crud.employeecrudapp.controller;

import com.crud.employeecrudapp.entity.EmployEntity;
import com.crud.employeecrudapp.repo.EmployRepo;
import com.crud.employeecrudapp.service.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employ")
@CrossOrigin(origins = "*")
public class EmployController {
    @Autowired
    private EmployService employService;
    @GetMapping
    public List<EmployEntity> getAll(){
        return employService.getAll();
    }
    @PostMapping
    public EmployEntity post(@RequestBody EmployEntity employ){
        return employService.createEmploy(employ);
    }
    @GetMapping("/{id}")
    public Optional<EmployEntity> getById(@PathVariable Long id){
        return employService.getById(id);
    }

    @PutMapping("/{id}")
    public EmployEntity put(@PathVariable Long id, @RequestBody EmployEntity newEmploy){
        return employService.update(id,newEmploy);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id ){ // we have to use response entity here so that we can config error
         boolean deleted = employService.delete(id);
         if(deleted){
             return ResponseEntity.noContent().build(); //204 no content
         }
         else {
             return ResponseEntity.notFound().build(); // 404 not found
         }
    }
}
