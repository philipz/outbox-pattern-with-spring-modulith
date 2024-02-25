package example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class OrderMonolithApplicationTests {

	@Test
	void contextLoads() {
	}
	
	ApplicationModules modules = ApplicationModules
       .of(OrderMonolithApplication.class);
 
    @Test
    void writeDocumentationSnippets() {
       new Documenter(modules)
              .writeModuleCanvases()
              .writeModulesAsPlantUml()
              .writeIndividualModulesAsPlantUml();
    }
}
