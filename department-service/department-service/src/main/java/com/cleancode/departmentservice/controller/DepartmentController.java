package com.cleancode.departmentservice.controller;

import com.cleancode.departmentservice.client.EmployeeClient;
import com.cleancode.departmentservice.model.Department;
import com.cleancode.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);



    @Autowired
    private DepartmentRepository departmentRepository;


    @Autowired
    private EmployeeClient employeeClient;

    public DepartmentController(DepartmentRepository departmentRepository, EmployeeClient employeeClient) {
        this.departmentRepository = departmentRepository;
        this.employeeClient = employeeClient;
    }

    @PostMapping
    public Department add(@RequestBody Department department){
        LOGGER.info("Department add: {} " , department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping
    public List<Department> findAll(){
        LOGGER.info("Department find " );
        return departmentRepository.findAll();
    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees(){
        LOGGER.info("Department find with employees" );
        List<Department>  departments =
                departmentRepository.findAll();

        departments.forEach(department -> {
            department.setEmployees(employeeClient.findByDepartment(department.getId()));
        });

        return departments;
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id){
        LOGGER.info("Department find: id = {} " , id);
        return departmentRepository.findById(id);
    }




}
