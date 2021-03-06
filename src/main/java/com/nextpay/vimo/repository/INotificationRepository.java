package com.nextpay.vimo.repository;

import com.nextpay.vimo.model.Notification;
import com.nextpay.vimo.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByStatusIsFalseAndUser(User user);

    @Query(value = "select * from notification " +
            "order by create_date desc ", nativeQuery = true)
    Iterable<Notification> findAllDateDesc();
}
