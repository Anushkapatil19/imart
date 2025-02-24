package com.API.imart.services;

import org.springframework.stereotype.Service;

import com.API.imart.entities.AuditLog;
import com.API.imart.repository.AuditLogRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    
    private final AuditLogRepository auditLogRepository;
    
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }
    
    @Override
    public void saveAuditLog(String action, String username) {
        AuditLog log = new AuditLog(action, username);
        auditLogRepository.save(log);
    }
    @Override
    public List<AuditLog> getRecentActivities() {
        return auditLogRepository.findByTimestampAfter(LocalDateTime.now().minusDays(7));
    }
    
	@Override
	public void deleteOldActivities() {
		auditLogRepository.deleteByTimestampBefore(LocalDateTime.now().minusDays(30));
	}
	
	

    @Override
    public List<AuditLog> getRecentActivitiesForSeller(Integer sellerId) {
        System.out.println("Fetching activities for seller ID: " + sellerId);  // Debugging
        return auditLogRepository.findBySellerId(sellerId);
    }
}

