package com.API.imart.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.API.imart.entities.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
	List<AuditLog> findByTimestampAfter(LocalDateTime timestamp);
    void deleteByTimestampBefore(LocalDateTime timestamp);
    List<AuditLog> findBySellerId(Integer sellerId);
}





