package com.hakim.bill.model.dong.dao;

import com.hakim.bill.model.dong.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {

}