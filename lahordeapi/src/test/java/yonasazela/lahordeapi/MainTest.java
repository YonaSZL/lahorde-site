package yonasazela.lahordeapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("yonasazela.lahordeapi")
class MainTest {

	@Test
	void contextLoads() {
		Assertions.assertTrue(true, "The test suite should be correctly initialized.");
	}

}
