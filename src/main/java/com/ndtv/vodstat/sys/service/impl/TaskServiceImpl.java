package com.ndtv.vodstat.sys.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.sys.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

	@Override
	@Scheduled(cron="0 * * * * ?")
	synchronized public void init() {
		System.out.println("init===============");
	}

}
