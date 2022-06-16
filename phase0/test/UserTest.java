import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() {
        try {
            UserManager.addBasicUser("Basic1", "Basic1");
            UserManager.addAdminUser("Admin1", "Admin1");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUsernameChange() {
        try {
            UserUseClass uuc = new UserUseClass();
            assertFalse(uuc.setUsername("Admin1", "Basic1"));
            assertTrue(uuc.setUsername("admin1", "Admin1"));
            List<String> lst = new ArrayList<>();
            lst.add("admin1");
            lst.add("Basic1");
            assertEquals("UserManager's usernames is not correctly updated after username change",
                    lst, UserManager.getUsernames());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin_LogOut() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));
            assertTrue(u1.getIsLoggedIn());
            assertTrue(u2.getIsLoggedIn());

            assertTrue(uuc.logOut("Admin1"));
            assertTrue(uuc.logOut("Basic1"));
            assertFalse(u1.getIsLoggedIn());
            assertFalse(u2.getIsLoggedIn());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBan() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));

            assertTrue(u1.banUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertTrue(u2.getIsBanned());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnBan() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));

            assertTrue(u1.banUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertTrue(u2.getIsBanned());

            assertTrue(u1.unBanUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertFalse(u2.getIsBanned());
            assertTrue(uuc.login("Basic1", "Basic1"));
            assertTrue(u2.getIsLoggedIn());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTempBan() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));

            assertTrue(u1.tempBanUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertTrue(u2.getIsTempBan());

            assertFalse(uuc.login("Basic1", "Basic1"));

            // Change BasicUser Time to 1 Min
            TimeUnit.MINUTES.sleep(1);

            assertTrue(uuc.login("Basic1", "Basic1"));
            assertTrue(u2.getIsLoggedIn());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin_After_Username_Change() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));
            assertTrue(u1.getIsLoggedIn());
            assertTrue(u2.getIsLoggedIn());

            assertTrue(uuc.logOut("Basic1"));
            assertTrue(uuc.setUsername("basic1", "Basic1"));
            assertTrue(u2.setPassword("basic1"));

            assertTrue("UserManager usernames not updated correctly",
                    uuc.login( "basic1", "basic1"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
