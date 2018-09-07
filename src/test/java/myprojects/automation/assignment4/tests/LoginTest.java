package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {


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
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.className("btn-lg")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("main")));
    }


}
