package com.API.imart.services;

import java.util.List;


import com.API.imart.entities.AuditLog;

public interface AuditLogService {
	
	
	
    List<AuditLog> getRecentActivities();
    void saveAuditLog(String action, String username);
    void deleteOldActivities();
    List<AuditLog> getRecentActivitiesForSeller(Integer sellerId);
   

}

