package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.TaskReport;
import com.valverde.buschecker.enums.Task;
import com.valverde.buschecker.repository.TaskReportRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@CommonsLog
@Transactional
public class TaskReportService {

    private final TaskReportRepository taskReportRepository;

    @Autowired
    public TaskReportService(TaskReportRepository taskReportRepository) {
        this.taskReportRepository = taskReportRepository;
    }

    public void save(Task task, Integer successed, Integer failed) {
        TaskReport taskReport = new TaskReport();
        taskReport.setDate(new Date());
        taskReport.setAmountSuccess(successed);
        taskReport.setAmountFailed(failed);
        taskReport.setTask(task);
        taskReportRepository.save(taskReport);
    }
}