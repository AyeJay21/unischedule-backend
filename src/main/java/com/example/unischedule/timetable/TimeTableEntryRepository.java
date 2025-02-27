package com.example.unischedule.timetable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTableEntryRepository extends JpaRepository<TimeTableEntry, Long> {
    List<TimeTableEntry> findByUserId(Long userId);
}
