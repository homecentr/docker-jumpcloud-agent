import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class JumpCloudContainerShould extends ContainerTestBase {
    @After
    public void after() throws Exception {
        JumpCloudApi.removeSystemIfExists(getContainerHostName());
    }

    @Test
    public void registerContainerAsSystem() throws Exception {
        Boolean systemExistsInJumpCloud = JumpCloudApi.systemExistsWithRetry(getContainerHostName(), 20 * 1000);

        assertTrue(systemExistsInJumpCloud);
    }
}
