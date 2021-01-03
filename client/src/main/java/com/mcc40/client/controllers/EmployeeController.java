/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Employee;
import com.mcc40.client.entities.Employee;
import com.mcc40.client.entities.Location;
import com.mcc40.client.services.EmployeeService;
import com.mcc40.client.services.EmployeeService;
import com.mcc40.client.services.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author aqira
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService service;
    @Autowired
    EmployeeService employeeService;
    LocationService locationService;

    @GetMapping
    public String table() {
        return "/employee/table";
    }

    @GetMapping("get-all")
    public @ResponseBody
    List<Employee> getAll() {
        System.out.println("fetching employee table");
        return service.search(null);
    }

    @PostMapping
    public @ResponseBody
    boolean insert(@RequestBody Employee employee) {
        return service.insert(employee);
    }

    @PutMapping
    public @ResponseBody
    boolean update(@RequestBody Employee employee) {
        System.out.println("update");
        
        return service.update(employee);
    }
    
    @DeleteMapping
    public @ResponseBody
    boolean delete(Integer id) {
        System.out.println("delete " + id);
        
        return service.delete(id);
    }

}
