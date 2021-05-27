package edu.ntnu.bsc020backend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 6000)
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @GetMapping("")
    private List<Employee> all(){
        return (List<Employee>) repository.findAll();
    }

    @PostMapping("")
    private Employee add(@RequestBody Employee employee){
        repository.save(employee);
        return employee;
    }

    @GetMapping("/{id}")
    private Employee getEmployee(@PathVariable("id") int id){
        return repository.findById(id);
    }
}
