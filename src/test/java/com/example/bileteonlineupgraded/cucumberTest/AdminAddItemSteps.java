package com.example.bileteonlineupgraded.cucumberTest;

import com.example.bileteonlineupgraded.CucumberTestRunner;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;


public class AdminAddItemSteps {

    WebDriver driver;

    @Given("browser is open for add item")
    public void browser_is_open_addItem() {
        String projectPath=System.getProperty("user.dir");
        System.out.println("Path = "+projectPath);

        System.setProperty("webdriver.chrome.driver",projectPath+"/src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40,TimeUnit.SECONDS);
    }

    @And("user is on login page admin for add item")
    public void user_is_on_login_page_admin_addItem() {
        driver.navigate().to("http://localhost:8090/admin/login");
    }

    @And("user clicks on sign-in for add item")
    public void user_clicks_on_sign_in_addItem() {
        driver.findElement(By.name("sign-in")).click();
    }

    @Then("user enters email3 and password3 for add item")
    public void admin_enters_email_and_password_addItem() throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys("admin@com");
        driver.findElement(By.name("password")).sendKeys("admin123");

        Thread.sleep(2000);
    }

    @Then("user is navigated to the admin page for addItem")
    public void user_is_navigated_to_the_additemPage() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.id("stadiums")).click();

    }
    @And("user is clicking adding a stadium")
    public void addStadiumClick() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.id("addStadium")).click();

    }
    @And("user is adding a stadium")
    public void addStadium() throws InterruptedException {
        driver.findElement(By.name("name")).sendKeys("Cluj Arena");
        driver.findElement(By.name("address")).sendKeys("Cluj Napoca");
        driver.findElement(By.name("capacity")).sendKeys("58");

        Thread.sleep(2000);

        driver.findElement(By.id("submit")).click();
    }

    @Then("user is navigate to stadiums page")
    public void getPrevPage() throws InterruptedException {
        driver.findElement(By.id("addStadium"));
        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }



}
