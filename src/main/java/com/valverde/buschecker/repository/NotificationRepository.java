package com.valverde.buschecker.repository;

import com.valverde.buschecker.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {}