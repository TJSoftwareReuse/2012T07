package test;

import cm.CM;
import org.junit.Test;
import java.io.IOException;


/**
 * Created by soap on 15/4/25.
 */
public class CMTest {

    @Test
    public void readSettingTest_getSize() throws IOException {
        CM cm = new CM();
        // relative path
        System.out.println("相对路径读取ini数据");
        cm.ProcessFile("src/test/my.ini");
        cm.getSize();

        // absolute path
        System.out.println("绝对路径读取ini数据");
        cm.ProcessFile("/Users/zhaopeng/git-repo/2012T07/src/test/my.ini");
        cm.getSize();
    }

    @Test
    public void readSettingTest_list() throws IOException {
        CM cm = new CM();
        cm.ProcessFile("src/test/my.ini");
        cm.list();
    }

    @Test
    public void readSettingTest_SearchKey() throws IOException {
        CM cm = new CM();
        cm.ProcessFile("src/test/my.ini");
        cm.SearchKey("[mysqld]back_log");
        cm.SearchKey("[mysqld]sync_relay_log");
    }
}
