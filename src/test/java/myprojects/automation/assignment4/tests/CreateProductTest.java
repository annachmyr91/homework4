package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class CreateProductTest extends BaseTest {

    private String productName;
    private String productQty;
    private String productPrice;

    @DataProvider
    public Object[][] getLoginCredentials() {
        return new Object[][]
                {
                        {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"},

                };
    }

    @Test(dataProvider = "getLoginCredentials")
    public void login(String login, String password) {
        driver.navigate().to("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.className("btn-lg")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("main")));
    }

    @Test(dependsOnMethods = "login")
    public void createProduct() {
        Actions actions = new Actions(driver);
        Actions catalogMenu = actions.moveToElement(driver.findElement(By.id("subtab-AdminCatalog")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("subtab-AdminProducts")));
        catalogMenu.click(driver.findElement(By.id("subtab-AdminProducts"))).build().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("page-header-desc-configuration-add")));
        driver.findElement(By.id("page-header-desc-configuration-add")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form_step1_name_1")));

        productName = ProductData.generate().getName();
        driver.findElement(By.id("form_step1_name_1")).sendKeys(productName);

        productQty = ProductData.generate().getQty().toString();
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(productQty);

        productPrice = ProductData.generate().getPrice();
        driver.findElement(By.id("form_step1_price_shortcut")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.id("form_step1_price_shortcut")).sendKeys(productPrice);

        driver.findElement(By.className("switch-input")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class = 'growl growl-notice growl-medium']")));


        driver.findElement(By.xpath("//button[@class =\"btn btn-primary js-btn-save\"]")).click();

    }


    // TODO implement logic to check product visibility on website
    @Test(dependsOnMethods = "createProduct")
    public void visibilityOfProduct() {
        driver.get(Properties.getBaseUrl());
        JavascriptExecutor je = driver;
        WebElement allItems = driver.findElement(By.className("all-product-link"));
        je.executeScript("arguments[0].scrollIntoView(true);", allItems);
        allItems.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("products")));

        WebElement lastPage = driver.findElement(By.cssSelector(".page-list li:nth-last-child(2)"));
        je.executeScript("arguments[0].scrollIntoView(true);", lastPage);
        lastPage.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"next disabled js-search-link\"]")));
        WebElement addedElement = driver.findElement(By.cssSelector(".products > *:last-child .product-description a"));
        String addedElementName = addedElement.getText();
        System.out.println(productName);
        System.out.println(addedElementName);
        Assert.assertTrue(productName.equalsIgnoreCase(addedElementName));

        addedElement.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-prices")));

        String addedElementName2 = driver.findElement(By.xpath("//h1[@itemprop = \"name\"]")).getText();
        String addedElementQty = driver.findElement(By.className("product-quantities")).getText();
        String addedElementPrice = driver.findElement(By.xpath("//span[@itemprop = \"price\"]")).getText();

        System.out.println(addedElementName2);
        Assert.assertTrue(addedElementName2.equalsIgnoreCase(productName), "Product name does not match original");

        System.out.println(addedElementQty);
        Assert.assertTrue(addedElementQty.contains(productQty), "Product quantity does not match original");

        System.out.println(addedElementPrice);
        System.out.println(productPrice);
        Assert.assertTrue(addedElementPrice.contains(productPrice), "Product price does not match original");

    }
}

