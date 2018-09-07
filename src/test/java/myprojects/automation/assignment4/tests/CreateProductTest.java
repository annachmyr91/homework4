package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

@Test
public class CreateProductTest extends BaseTest {


    @Test(dependsOnMethods = "LoginTest")
    public void createProduct(ProductData newProduct) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.id("subtab-AdminCatalog"))).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("subtab-AdminProducts")));
        driver.findElement(By.id("subtab-AdminProducts")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("page-header-desc-configuration-add")));
    }


    // TODO implement logic to check product visibility on website
}

