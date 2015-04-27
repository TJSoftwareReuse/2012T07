package test;

import pm.PM;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by soap on 15/4/25.
 */
public class PMTest {
    @Test
    public void pmUnit_test() throws IOException, InterruptedException {
        PM pm = new PM();
        System.out.println(new Date());
        pm.setOutputPath("src/test/");
        pm.start();
        for (int i=0;i<13;i++)
        {
            Thread.sleep(10000);
            pm.addItem("test1", 5);
            pm.addItem("test2", 10);
            pm.addItem("test3", 100);
            System.out.println(new Date());
        }
        pm.stop();

        System.out.println(new Date());

    }
}
