package eu.unicredit.dummy_java_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public abstract class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    protected static final ObjectMapper mapper = new ObjectMapper();

}

