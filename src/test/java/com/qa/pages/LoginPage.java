package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends HeaderBar{
    @AndroidFindBy(accessibility = "Username input field")  private WebElement userNameTxtFld;
    @AndroidFindBy(accessibility = "Password input field")  private WebElement passwordTxtFld;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Username-error-message\"]/android.widget.TextView")  private WebElement userNameErrTxt;
    @AndroidFindBy(accessibility = "Login button")  private WebElement loginBtn;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"generic-error-message\"]/android.widget.TextView") private WebElement invalidCredErrTXt;

    public LoginPage enterUserName (String username) {
        sendKeys(userNameTxtFld, username);
        return this;
    }

    public LoginPage enterPassword (String password) {
        sendKeys(passwordTxtFld, password);
        return this;
    }

    public String getErrTxt () {
        return getAttribute(userNameErrTxt, "text");
    }

    public String getMainErrTxt () {
       return getAttribute(invalidCredErrTXt, "text");
    }

    public HomeScreen pressLoginBtn(){
        click(loginBtn);
        return new HomeScreen();
    }

    public LoginPage clearUsername(){
        clear(userNameTxtFld);
        return this;
    }

    public LoginPage clearPasswordField(){
        clear(passwordTxtFld);
        return this;
    }

    public HomeScreen login(String username, String password){
        sendKeys(userNameTxtFld, username);
        sendKeys(passwordTxtFld, password);
        return pressLoginBtn();
    }

}
