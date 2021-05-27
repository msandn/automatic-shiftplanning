package edu.ntnu.bsc020backend.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftScheduleRepository extends CrudRepository<ShiftSchedule, Integer> {
    ShiftSchedule findById(int id);
}
