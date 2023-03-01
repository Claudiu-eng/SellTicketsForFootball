package com.example.bileteonlineupgraded.cucumberTest;

import com.example.bileteonlineupgraded.CucumberTestRunner;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@CucumberContextConfiguration
@SpringBootTest(classes = CucumberTestRunner.class)
public class LogInSteps {

    WebDriver driver;

    @Given("browser is open")
    public void browser_is_open() {
        String projectPath=System.getProperty("user.dir");
        System.out.println("Path = "+projectPath);

        System.setProperty("webdriver.chrome.driver",projectPath+"/src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40,TimeUnit.SECONDS);
    }

    @And("user is on login page")
    public void user_is_on_login_page() {
        driver.navigate().to("http://localhost:8090/client/login");
    }

    @And("user is on login page admin")
    public void user_is_on_login_page_admin() {
        driver.navigate().to("http://localhost:8090/admin/login");
    }

    @When("user enters email3 and password3")
    public void admin_enters_email_and_password() throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys("admin@com");
        driver.findElement(By.name("password")).sendKeys("admin123");

        Thread.sleep(2000);
    }

    @When("user enters email4 and password4")
    public void admin_enters_email_and_password_fail() throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys("adm@com");
        driver.findElement(By.name("password")).sendKeys("admin123");

        Thread.sleep(2000);
    }

    @When("user enters email2 and password2")
    public void user_enters_email_and_password() throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys("client@gmail.com");
        driver.findElement(By.name("password")).sendKeys("12345");

        Thread.sleep(2000);
    }

    @When("user enters email1 and password1")
    public void user_enters_email_and_password_fail() throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys("client@dsaf.com");
        driver.findElement(By.name("password")).sendKeys("123d456");

        Thread.sleep(2000);
    }

    @When("user clicks on guest")
    public void user_connect_as_guest() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id("guest")).click();

    }

    @And("user clicks on sign-in")
    public void user_clicks_on_sign_in() {
        driver.findElement(By.name("sign-in")).click();
    }

    @And("user clicks on login")
    public void user_clicks_on_login() {
        driver.findElement(By.name("action")).click();
    }

    @Then("user is navigated to the admin page")
    public void user_is_navigated_to_the_admin_page() throws InterruptedException {

        driver.findElement(By.name("action"));
        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }

    @Then("user is navigated to the client page")
    public void user_is_navigated_to_the_client_page() throws InterruptedException {

        driver.findElement(By.className("content"));
        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }


}
