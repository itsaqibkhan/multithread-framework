package com.qa.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomeScreen extends HeaderBar {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"sort button\"]/android.widget.ImageView") private WebElement sortBtn;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"container header\"]/android.widget.TextView") private WebElement titleText;
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='store item text'])[1]") private WebElement SLBTitle;
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='store item text'])[2]") private WebElement sauceLightTitle;
    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"review star 5\"]/android.widget.TextView)[1]") private WebElement fiveRatingBtn;


    public HomeScreen pressSortBtn(){
        click(sortBtn);
        return this;
    }

    public String getTitle(){
        return getAttribute(titleText, "text");
    }
    public String getSLBName(){
        return getAttribute(SLBTitle, "text");
    }
    public String getSauceLightName(){
        return getAttribute(sauceLightTitle, "text");
    }
    public ProductDetail pressProductTitle(int item){
        By element = By.xpath("(//android.widget.TextView[@content-desc='store item text'])" + "[" + item + "]");
        WebElement productTitle = getDriver().findElement(element);
        click(productTitle);
        return new ProductDetail();
    }



}
