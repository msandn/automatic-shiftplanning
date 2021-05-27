package edu.ntnu.bsc020backend.models;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    Shift findById(int id);

    List<Shift> findAllByEmployee(Employee employee);

    List<Shift> findByStartTime(LocalDateTime startTime);

    List<Shift> findByDurationMin(int durationMin);

}
