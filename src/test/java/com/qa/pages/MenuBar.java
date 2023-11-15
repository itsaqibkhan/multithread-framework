package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class MenuBar extends BaseTest {
    @AndroidFindBy(accessibility = "menu item log in") private WebElement LoginBtnMenuPage;


    public LoginPage pressLoginBtnMenuPage(){
        click(LoginBtnMenuPage);
        return new LoginPage();

    }

}
