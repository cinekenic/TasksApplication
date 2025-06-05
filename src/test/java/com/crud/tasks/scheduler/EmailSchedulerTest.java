package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTest {

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private EmailScheduler emailScheduler;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Test
    void shouldSendDailyEmailWithMultipleTasks() {
        // Given
        when(taskRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        ArgumentCaptor<Mail> captor = ArgumentCaptor.forClass(Mail.class);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        verify(simpleEmailService, times(1)).send(captor.capture());

        Mail sentMail = captor.getValue();
        assertThat(sentMail.getMailTo()).isEqualTo("admin@test.com");
        assertThat(sentMail.getSubject()).isEqualTo(SUBJECT);
        assertThat(sentMail.getMessage()).isEqualTo("Currently in database you got: 5 tasks");
    }

    @Test
    void shouldSendDailyEmailWithOneTask() {
        // Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        ArgumentCaptor<Mail> captor = ArgumentCaptor.forClass(Mail.class);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        verify(simpleEmailService, times(1)).send(captor.capture());

        Mail sentMail = captor.getValue();
        assertThat(sentMail.getMessage()).isEqualTo("Currently in database you got: 1 task");
    }
}
