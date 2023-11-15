package com.qa.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.stream.Stream;

public class CartScreen extends HeaderBar{
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"total price\"]") private WebElement totalPrice;


    public String getProductTitleCart(int item){
        By element = By.xpath("//android.widget.TextView[@content-desc=\"product label\"]" + "[" + item + "]");
        WebElement productTitle = getDriver().findElement(element);
        return getAttribute(productTitle, "text");
    }
    public String getProductPriceCart(int item){
        By element = By.xpath("(//android.widget.TextView[@content-desc=\"product price\"])" + "[" + item + "]");
        WebElement productPrice = getDriver().findElement(element);
        return getAttribute(productPrice, "text");
    }
    public String getTotalPrice(){
        return getAttribute(totalPrice,"text");
    }
}
