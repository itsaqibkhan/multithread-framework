package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductDetail extends BaseTest {
   @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"container header\"]/android.widget.TextView") private WebElement productTitle;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"product price\"]") private WebElement productPrice;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Add To Cart button\"]/android.widget.TextView") private WebElement addToCartBtn;



    public String productTitleDetailPage(){
       return getAttribute(productTitle, "text");
    }

    public ProductDetail pressAddToCartBnt() {
        click(addToCartBtn);
        return this;
    }








}
