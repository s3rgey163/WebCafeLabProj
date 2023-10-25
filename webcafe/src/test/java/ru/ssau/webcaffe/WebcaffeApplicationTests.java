package ru.ssau.webcaffe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebcaffeApplicationTests {

	@Test
	void contextLoads() throws ClassNotFoundException {
		Class.forName("ru.ssau.webcaffe.repo.UserRepositoryTest");
	}

}
