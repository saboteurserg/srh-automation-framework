package samples;

import com.krynytskyyserhiy.automation.framework.keywords.DB;
import com.krynytskyyserhiy.automation.framework.keywords.Log;
import com.krynytskyyserhiy.automation.framework.pageobject.SRHPageObject;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.krynytskyyserhiy.automation.framework.keywords.DB.DRIVER_TYPE.ORACLE;

/**
 * Created by serhiy.krynytskyy on 20.12.2016.
 */
public class DBTest extends SRHPageObject {


    @Test(enabled = false)
    public void testDB1(){

        DB db = new DB(ORACLE, "testhost.com", 1521, "SID", "username", "password");
        ResultSet rs;

        try {
            rs = db.executeQuery("SELECT * FROM TEST_NAME WHERE STATE_ID = 3 ORDER BY END_DATE DESC");
            rs.first();
            Log.info(rs.getString("END_DATE"));
            int i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    @Test(enabled = true)
    public void testDB2(){

        DB db = new DB(ORACLE, "testhost.com", 1521, "SID", "username", "password");
        ResultSet rs;

        try {
            rs = db.executeQuery("SELECT END_DATE FROM TEST_NAME WHERE STATE_ID = 3", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs.first();
            Log.info(rs.getString("END_DATE"));
            int i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }



}
