package com.qa.tests;

import com.google.gson.JsonObject;
import com.qa.BaseTest;
import com.qa.pages.HomeScreen;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuBar;
import com.qa.utils.TestUtils;
import com.sun.jdi.request.ExceptionRequest;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    HomeScreen homeScreen;
    MenuBar menuBar;
    InputStream dataIs;
    JSONObject loginusers;
    TestUtils utils = new TestUtils();

    @BeforeClass
   public void beforeClass() throws IOException {
        InputStream dataIs = null;

        try {
            String jsonFilePath = "data/loginUsers.json";
            dataIs = getClass().getClassLoader().getResourceAsStream(jsonFilePath);
            JSONTokener tokener = new JSONTokener(dataIs);
            loginusers = new JSONObject(tokener);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(dataIs != null){
                dataIs.close();
            }
        }


    }

    @BeforeMethod
    public void beforeMethod(Method m){
     utils.log().info("Child BeforeMethod called");

        loginPage = new LoginPage();
        homeScreen = new HomeScreen();
        menuBar = new MenuBar();
     utils.log().info("***Strarting test with method -" + m.getName()+ "***");
        terminateApp();                                     //Make all the test case independent and then terminate and activate app in beforemethod
        activateApp();
    }

    @Test
    public void emptyUserName() throws InterruptedException {
        homeScreen.pressMenuBtn();
        menuBar.pressLoginBtnMenuPage();
        loginPage.pressLoginBtn();
        String actualErrText = loginPage.getErrTxt();
        String expectedErrText = getStrings().get("err_empty_username");
     utils.log().info("Actual error text is -" + actualErrText + "\n" + "Expected error text is - " + expectedErrText);
        Assert.assertEquals(actualErrText, expectedErrText);
    }

    @Test
    public void invalidUserName() throws InterruptedException {

            homeScreen.pressMenuBtn();
            menuBar.pressLoginBtnMenuPage();
            loginPage.enterUserName(loginusers.getJSONObject("invalidUserName").getString("username")).enterPassword(loginusers.getJSONObject("invalidUserName").getString("password")).pressLoginBtn();  // We can do this chaining only because first to method return login page also a goof practice for page object model
            String actualMainErrTxt = loginPage.getMainErrTxt();
            String expectedMainErrTxt = getStrings().get("err_invalid_credentials");
         utils.log().info("Actual main error text is -" + actualMainErrTxt + "\n" + "Expected error text is - " + expectedMainErrTxt);
            Assert.assertEquals(actualMainErrTxt, expectedMainErrTxt);

       /*
        catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.toString());


          StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
         utils.log().info(sw.toString());
            Assert.fail(sw.toString());
        */
    }

    @Test
    public void lockedUserSoftAssert() throws InterruptedException {
        homeScreen.pressMenuBtn();
        menuBar.pressLoginBtnMenuPage();
        loginPage.clearUsername();
        loginPage.enterUserName(loginusers.getJSONObject("lockedUserName").getString("username"));
        loginPage.clearPasswordField();
        loginPage.enterPassword(loginusers.getJSONObject("lockedUserName").getString("password"));
        loginPage.pressLoginBtn();

        SoftAssert sa =new SoftAssert();
        String actualMainErrTxt = loginPage.getMainErrTxt();
        String expectedMainErrTxt = getStrings().get("err_invalid_credentials");
     utils.log().info("Actual main error text is -" + actualMainErrTxt + "\n" + "Expected error text is - " + expectedMainErrTxt);
        sa.assertEquals(actualMainErrTxt, expectedMainErrTxt);
        sa.assertAll();
    }

    @Test
    public void validUserName() throws InterruptedException {
        homeScreen.pressMenuBtn();
        menuBar.pressLoginBtnMenuPage();
        loginPage.clearUsername();
        loginPage.enterUserName(loginusers.getJSONObject("validUserName").getString("username"));
        loginPage.clearPasswordField();
        loginPage.enterPassword(loginusers.getJSONObject("validUserName").getString("password"));
        homeScreen = loginPage.pressLoginBtn();
    }
}
