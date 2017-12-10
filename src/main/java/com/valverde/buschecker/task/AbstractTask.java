package com.valverde.buschecker.task;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public abstract class AbstractTask {

    private Long startTime;

    abstract void startTask();

    String getTaskName() {
        return "["+getClass().getSimpleName()+"]:";
    }

    void logStartTask() {
        startTime = System.currentTimeMillis();
        log.info(getTaskName()+" started.");
    }

    void logEndTask() {
        log.info(getTaskName()+" finished in "+(System.currentTimeMillis() - startTime)+" millis.");
    }
}