package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     *

     */

    @DataProvider
    public Object[][] getLoginCredentials() {
        return new Object[][]
                {
                        {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"},

                };
    }

    @Test(dataProvider = "getLoginCredentials")
    public void login(String login, String password) {

        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.className("btn-lg")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("main")));
    }


    public void createProduct(ProductData newProduct) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.id("subtab-AdminCatalog"))).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("subtab-AdminProducts")));
        driver.findElement(By.id("subtab-AdminProducts")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("page-header-desc-configuration-add")));
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded

        // wait.until(...);
        // ...
    }


}

