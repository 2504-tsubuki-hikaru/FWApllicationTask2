package com.example.forum2.repository;

import com.example.forum2.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Coment, Integer> {
}
