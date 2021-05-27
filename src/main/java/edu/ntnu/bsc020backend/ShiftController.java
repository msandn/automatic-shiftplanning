package edu.ntnu.bsc020backend;

import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.EmployeeRepository;
import edu.ntnu.bsc020backend.models.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 6000)
@RequestMapping("/shift")
public class ShiftController {

    @Autowired
    private ShiftRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    private List<Shift> all(){
        return (List<Shift>) repository.findAll();
    }

    @PostMapping("")
    private void add(@RequestBody Shift shift){
        repository.save(shift);
    }

    @PutMapping("/{id}/{empid}")
    private void updateShift(@PathVariable("id") int id, @PathVariable("empid") int empid){
        Shift updateShift = repository.findById(id);
        updateShift.setEmployee(employeeRepository.findById(empid));
        repository.save(updateShift);
    }

    @GetMapping("/{id}")
    private Shift getShift(@PathVariable("id") int id){
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    private void deleteShift(@PathVariable("id") int id){
        repository.delete(repository.findById(id));
    }
}
