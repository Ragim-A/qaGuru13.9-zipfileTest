
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
public class JsonTest {
    ClassLoader classLoader = JsonTest.class.getClassLoader();

    @DisplayName("jsonTest with Jackson")
    @Test
    void jsonTest() throws Exception {
        InputStream inputStream = classLoader.getResourceAsStream("auto.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new InputStreamReader(inputStream));
        assertThat(jsonNode.get("Brand").asText()).isEqualTo("Renault");
        assertThat(jsonNode.get("Price").asInt()).isEqualTo(14);
        assertThat(jsonNode.get("LocalAssembled").asBoolean()).isEqualTo(true);
        assertThat(jsonNode.findValue("4wd").asBoolean()).isEqualTo(false);
    }
}
