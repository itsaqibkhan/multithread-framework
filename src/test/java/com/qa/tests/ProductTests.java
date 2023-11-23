package com.qa.tests;

import com.qa.pages.*;
import com.qa.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class ProductTests extends MenuBar {
    LoginPage loginPage;
    ProductDetail productDetail;
    MenuBar menuBar;
    HomeScreen homeScreen;
    HeaderBar headerBar;
    CartScreen cartScreen;
    TestUtils utils = new TestUtils();

    @BeforeMethod
    public void beforeMethod(Method m){
        loginPage = new LoginPage();
        productDetail = new ProductDetail();
        menuBar =  new MenuBar();
        homeScreen = new HomeScreen();
        headerBar = new HeaderBar();
        cartScreen = new CartScreen();

     utils.log().info("***Starting method- "+ m.getName());
   //     terminateApp();
   //     activateApp();

    }

    @Test
    public void validateProductAtProductDetail(){
        homeScreen.pressMenuBtn();
         menuBar.pressLoginBtnMenuPage();
         homeScreen = loginPage.login("bob@example.com","10203040");

         SoftAssert sa = new SoftAssert();
         String expectedString = getStrings().get("title_slb");
         String actualString = homeScreen.getSLBName();
      utils.log().info("Expected error text is -" + expectedString + "\n" + "Actual text is - " + actualString);
         sa.assertEquals(actualString, expectedString);

         expectedString = getStrings().get("title_torch");
         actualString = homeScreen.getSauceLightName();
      utils.log().info("Expected error text is -" + expectedString + "\n" + "Actual text is - " + actualString);
         sa.assertEquals(actualString, expectedString);
         sa.assertAll();
    }

    @Test
    public void addProductsToCart() throws InterruptedException {
        homeScreen.pressProductTitle(1);
        String expectedString = getStrings().get("title_slb");
        Thread.sleep(2000);
        String actualString = productDetail.productTitleDetailPage();
     utils.log().info("Expected text is -" + expectedString + "\n" + "Actual text is - " + actualString);
        Assert.assertEquals(actualString, expectedString);
        productDetail.pressAddToCartBnt();
        pressBackButton();

        homeScreen.pressProductTitle(2);
        String expectedTorchString = getStrings().get("title_torch");
        Thread.sleep(2000);         //To prevent stale element exception
        String actualTorchString = productDetail.productTitleDetailPage();
     utils.log().info("Expected text is -" + expectedTorchString + "\n" + "Actual text is - " + actualTorchString);
        Assert.assertEquals(actualString, expectedString);
        productDetail.pressAddToCartBnt();
        pressBackButton();
        headerBar.pressCartBtn();

        Thread.sleep(2000);
     utils.log().info(cartScreen.getProductPriceCart(1));
     utils.log().info(cartScreen.getProductPriceCart(2));
     utils.log().info(cartScreen.getTotalPrice());


        float calculatedSum = Float.parseFloat(cartScreen.getProductPriceCart(1).substring(1))
                + Float.parseFloat(cartScreen.getProductPriceCart(2).substring(1)); //Get a substring and convert it into integer
        float shownSum = Float.parseFloat(cartScreen.getTotalPrice().substring(1));
     utils.log().info("The calculated sum is $" + calculatedSum + "\n" + "The shown sum is $" + shownSum);
        Assert.assertEquals(calculatedSum, shownSum);

    }


}
