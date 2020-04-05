package by.epam.nikitaSergei.entity;

import by.epam.nikitaSergei.Main;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class CallCenterTest {

    @Test
    public void callCenterTestMethod() {
        final Logger logger = Logger.getLogger(Main.class);

        CallCenter callCenter = new CallCenter(2, 5);
        for (int index = 0; index < 7; index++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                logger.error(exception);
            }
            callCenter.createClient();
        }
        Assert.assertNotNull(callCenter);
    }
}