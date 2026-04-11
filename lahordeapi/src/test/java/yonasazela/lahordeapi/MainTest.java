package yonasazela.lahordeapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Suite
@SelectPackages("yonasazela.lahordeapi")
class MainTest {

	@Test
	void contextLoads() {
		Assertions.assertTrue(true, "The test suite should be correctly initialized.");
	}

	@Test
	@DisplayName("newLahordeApiApplication should create a new instance")
	void newLahordeApiApplication_shouldCreateNewInstance() {
		assertThat(LahordeApiApplication.newLahordeApiApplication()).isNotNull()
				.isInstanceOf(LahordeApiApplication.class);
	}

	@Test
	@DisplayName("main method should not throw exception")
	void main_shouldNotThrowException() {
		assertDoesNotThrow(() -> LahordeApiApplication.main(new String[]{"--server.port=0"}));
	}
}
