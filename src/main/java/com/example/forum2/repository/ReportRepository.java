package com.example.forum2.repository;

import com.example.forum2.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    public List<Report> findAllByOrderByIdDesc();
}
