package com.crud.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TasksApplicationTests {

	@Test
	void contextLoads() {
		assertThat(true).isTrue();
	}

	@Test
	void shouldRunMainMethodWithoutExceptions() {
		TasksApplication.main(new String[] {});
		assertThat(true).isTrue();
	}
}
