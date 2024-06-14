package org.examp.emailnotification.persistence.entity.repository;

import org.examp.emailnotification.persistence.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository <ProcessedEventEntity,Long> {
ProcessedEventEntity findByMessageId(String messageId);
}
