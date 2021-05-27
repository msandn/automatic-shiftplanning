package edu.ntnu.bsc020backend;
import edu.ntnu.betweenAlgorithmAndBackend.Context;
import edu.ntnu.bsc020backend.models.*;
import edu.ntnu.sga.SGA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 6000)
@RestController
public class ShiftScheduleController {


    @Autowired
    ShiftScheduleRepository repository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/")
    public String index() {
        return "test";
    }

    @PostMapping("/shiftSchedule")
    public ShiftSchedule postShiftSchedule(@RequestParam(value = "id", defaultValue = "1") int id, @RequestBody ShiftSchedule schedule) {
        Context context = new Context(new GASettings());
        System.out.println(schedule.getEmployees());
        Solution solution = context.getShiftScheduleSolution(schedule);
        schedule.updateSchedule(solution);

        //Save the result
        //repository.save(schedule);
        return schedule;
    }

    @GetMapping("/shiftSchedule")
    public ShiftSchedule getShiftScheduleExample() {
        return getExample();
    }

    @GetMapping("/shiftSchedule/all")
    public List<ShiftSchedule> all(){
        return (List<ShiftSchedule>) repository.findAll();
    }

    @GetMapping("/shiftSchedule/{id}")
    private ShiftSchedule getSchedule(@PathVariable("id") int id){
        return repository.findById(id);
    }

    //Employees need to be saved in the database for this to work
    @PostMapping("/shiftSchedule/generate")
    public ShiftSchedule postShiftScheduleDB(@RequestBody ShiftSchedule schedule) {
        System.out.println(schedule.getEmployees());
        shiftRepository.saveAll(schedule.getShifts());
        Context context = new Context(new GASettings());
        Solution solution = context.getShiftScheduleSolution(schedule);
        schedule.addSolution(solution);
        schedule.updateSchedule(solution);
        //Save the result
        repository.save(schedule);
        return schedule;
    }


    public static ShiftSchedule getExample(){
        List<Employee> employees = new ArrayList<Employee>();
        List<Shift> shifts = new ArrayList<Shift>();

        for(int i = 0; i<6; i++){
            employees.add(new Employee());
        }
        LocalDateTime date = LocalDateTime.of(2021, 2, 28, 16, 20);
        int counter = 0;
        for(int i = 0; i<5; i = i+6){
            shifts.add(new Shift(date.plusHours(16), 480,0));
            shifts.add(new Shift(date.plusHours(16), 480,0));
            shifts.add(new Shift(date.plusHours(16), 480,0));

            shifts.add(new Shift(date.plusHours(24), 480,0));
            shifts.add(new Shift(date.plusHours(24), 480,0));
            shifts.add(new Shift(date.plusHours(24), 480,0));
            date = date.plusDays(1);
        }
        return new ShiftSchedule(LocalDate.of(2021, 3, 1), employees, shifts);

    }
}
