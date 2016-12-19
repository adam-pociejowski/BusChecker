package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Notification;
import com.valverde.buschecker.repository.NotificationRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@CommonsLog
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void save(Notification notification) {
        notification.setDate(new Date());
        notificationRepository.save(notification);
    }
}