package com.qa;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import io.appium.java_client.service.local.AppiumServiceBuilder;


public class BaseTest {
    protected static ThreadLocal <Properties>  props= new ThreadLocal<Properties>();
    protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal <HashMap<String, String>>();

    protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
    public static ThreadLocal <AppiumDriverLocalService> server = new ThreadLocal<AppiumDriverLocalService>();
    protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
    protected static ThreadLocal <String> platformName = new ThreadLocal<String>();
    TestUtils utils = new TestUtils();
    static Logger log = LogManager.getLogger(BaseTest.class.getName());


    public AppiumDriver getDriver(){
        return driver.get();
    }
    public void setDriver(AppiumDriver driver2){
      driver.set(driver2);
    }
    public Properties getProperties(){
        return props.get();
    }
    public void setProprties(Properties props2){
        props.set(props2);
    }
    public HashMap<String,String> getStrings(){
        return strings.get();
    }
    public void setStrings(HashMap<String,String> strings2){
        strings.set(strings2);
    }
    public String getDateAndTime() {
        return dateTime.get();
    }
    public void setDateAndTime(String dateTime2){
        dateTime.set(dateTime2);
    }
    public AppiumDriverLocalService getAppiumDriverLocalService(){
        return server.get();
    }
    public void setAppiumDriverLocalService(AppiumDriverLocalService server2){
        server.set(server2);
    }
    public String getDeviceName() {
        return deviceName.get();
    }
    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }
    public String getPlaformName() {
        return platformName.get();
    }

    public void setPlatform(String platform2) {
        platformName.set(platform2);
    }

    @BeforeMethod
    public void beforeMethod(){
    utils.log("Super BeforeMethod called");
        ((CanRecordScreen) getDriver()).startRecordingScreen();
    }

    @AfterMethod
    public synchronized void afterMethod(ITestResult result) throws IOException {
        String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

        if (result.getStatus() == 2) {
            Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

            String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
                    + File.separator + getDateAndTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();

            File videoDir = new File(dirPath);

            synchronized (videoDir){
                if (!videoDir.exists()) {
                    videoDir.mkdirs();
                }
            }

            try {
                FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
                stream.write(Base64.decodeBase64(media));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

      @BeforeSuite
      public void beforeSuite(){
        setAppiumDriverLocalService(getAppiumService());
        getAppiumDriverLocalService().start();

      }

      @AfterSuite
      public void afterSuite(){
        getAppiumDriverLocalService().stop();
      }

    public AppiumDriverLocalService getDriverServerDefault(){
       return AppiumDriverLocalService.buildDefaultService();
    }

    // To start appium server programatically
    public AppiumDriverLocalService getAppiumService() {
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH", "/home/user/.npm-global/bin:/usr/local/lib/nodejs/node-v18.13.0-linux-x64/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/bin:/usr/X11/bin:$PATH:/snap/bin:/usr/lib/jvm/jdk-15.0.2/bin:/home/user/Android/Sdk/tools:/home/user/Android/Sdk/platform-tools:/home/user/Android/Sdk/tools/bin" + System.getenv("PATH"));   //echo $PATH  to get this path
        environment.put("ANDROID_HOME", "/home/user/Android/Sdk");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/lib/nodejs/node-v18.13.0-linux-x64/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/nodejs/node-v18.13.0-linux-x64/lib/node_modules/appium/build/lib/main.js"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)                            //To override the existing session
                .withEnvironment(environment));                             // Because the system environment variable is not exposed to java
         //       .withLogFile(new File("ServersLogs/server.log")) );                           // To output the log in this file
    }



   public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }


    @Parameters({"platformName", "deviceName", "udid", "avd", "systemPort"})
    @BeforeTest
    public void beforeTest(String platformName, String deviceName, String udid, @Optional String avd, int systemPort) throws Exception {
        setPlatform(platformName);
        setDeviceName(deviceName);
        setDateAndTime(utils.dateAndTime());
        InputStream inputStream = null;
        InputStream stringIs  = null;
        AppiumDriver driver;


        log.trace("This is a trace");
        log.debug("This is a debug");
        log.info("This is a info");
        log.warn("This is a warn");
        log.error("This is a error");
        log.fatal("This is a fatal");

        try {
    //   String appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator +  "Android-MyDemoAppRN.1.3.0.build-244(1).apk";

      Properties props = new Properties();
//   utils = new TestUtils();


    String propFileName = "config.properties";
    String stringXmlFileName = "strings/strings.xml";

    inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    props.load(inputStream);
    setProprties(props);

    stringIs = getClass().getClassLoader().getResourceAsStream(stringXmlFileName);
    setStrings(utils.parseStringXml(stringIs));

    UiAutomator2Options options = new UiAutomator2Options()
            .setPlatformName(platformName)
            .setAutomationName(getProperties().getProperty("automationName"))
            .setUdid(udid)
            .setAvd(avd)
            .setSystemPort(systemPort)
            .setAppPackage(getProperties().getProperty("appPackage"))
            .setAppActivity(getProperties().getProperty("appActivity"));
         //   .setApp(props.getProperty("appLocation"));

    URL url = new URL(getProperties().getProperty("appiumUrl"));
    driver = new AndroidDriver(url, options);
    utils.log(String.valueOf(driver.getSessionId()));
    setDriver(driver);
} catch (Exception e) {
    e.printStackTrace();
    throw e;
} finally {
    if(inputStream!= null)
        inputStream.close();
} if(stringIs!= null)
            stringIs.close();
    }

    public void waitForVisibility(WebElement e)
    {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(WebElement e)
    {
        waitForVisibility(e);
        e.click();
    }
    public void sendKeys(WebElement e, String txt){
        waitForVisibility(e);
        e.sendKeys(txt);
    }
    public void clear(WebElement e){
        waitForVisibility(e);
        e.clear();
    }
    public String getAttribute(WebElement e, String attribute){
       waitForVisibility(e);
      return e.getAttribute(attribute);
    }

    public void terminateApp(){
        ((InteractsWithApps) getDriver()).terminateApp(getProperties().getProperty("appPackage"));

    }
    public void activateApp(){
        ((InteractsWithApps) getDriver()).activateApp(getProperties().getProperty("appPackage"));

    }

    public void pressBackButton(){
       // ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
       // ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
        getDriver().navigate().back();
   }


    @AfterTest
    public void afterTest(){
        getDriver().quit();
    }
}
