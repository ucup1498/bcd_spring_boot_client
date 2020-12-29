/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Department;
import com.mcc40.client.services.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author aqira
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }
    
    @GetMapping("")
    public String search(String keyword, Model model) {
        List<Department> departments = service.search(keyword);
        model.addAttribute("departments", departments);
        
        model.addAttribute("htmlTitle", "Department Table");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority : auth.getAuthorities()) {
            System.out.println(authority.getAuthority());
            System.out.println(authority);
        }
        model.addAttribute("profile", auth);
        
        return "department/department_table";
    }

    @GetMapping("edit")
    public String openEditPage(Model model) {
        return "department/department_edit";
    }
    
    @PostMapping("modify")
    public String modifyDepartment(@RequestParam("id") Integer id, Model model) {
        System.out.println("modify param: "  + id);
        Department department = service.getById(id).get(0);
        model.addAttribute("department", department);
        return "department/department_modify";
    }
    
    @GetMapping("modify")
    public String openSavePage(Model model) {
        return "department/department_modify";
    }

    @PostMapping("post")
    public String savePost(String id, String name, String manager, String location, Model model) {
        System.out.println("[POST] department: " + id + " | " + name + " | " + manager + " | " + location);
        Department department = new Department();
        department.setId(Integer.parseInt(id));
        department.setName(name);
        department.setManagerId(Integer.parseInt(manager));
        department.setLocationId(Integer.parseInt(location));

        System.out.println(department);
        service.savePost(department);
        return "redirect:localhost:3000/department";
    }

    @PostMapping("update")
    public String savePut(String id, String name, String manager, String location, Model model) {
        System.out.println("[PUT] department: " + id + " | " + name + " | " + manager + " | " + location);
        Department department = new Department();
        department.setId(Integer.parseInt(id));
        if (!name.equals("")) {
            department.setName(name);
        }
        if (!manager.equals("")) {
            department.setManagerId(Integer.parseInt(manager));
        }
        if (!location.equals("")) {
            department.setLocationId(Integer.parseInt(location));
        }
        
        System.out.println(department);
        service.savePut(department);
        return "redirect:localhost:3000/department";
    }
    
    @PostMapping("delete")
    public String delete(String id, Model model) {
        System.out.println("[DELETE] department: " +  id);
        
        service.deleteById(Integer.parseInt(id));
        
        return "redirect:localhost:8082/department/modify";
    }

}
