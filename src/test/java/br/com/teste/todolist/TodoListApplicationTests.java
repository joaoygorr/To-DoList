package br.com.teste.todolist;

import br.com.teste.todolist.integration.containers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TodoListApplicationTests extends AbstractIntegrationTest {

	@Test
	void contextLoads() {
	}

}
