package com.trg.tsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trg.tsu.model.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long>{
}
