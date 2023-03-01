package com.example.bileteonlineupgraded.cucumberTest;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ClientBuyTicketSteps {

    WebDriver driver;

    @Given("browser is open for buy ticket")
    public void browser_is_open_buyTicket() {
        String projectPath=System.getProperty("user.dir");
        System.out.println("Path = "+projectPath);

        System.setProperty("webdriver.chrome.driver",projectPath+"/src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40,TimeUnit.SECONDS);
    }

    @And("user is on login page client for buy ticket")
    public void user_is_on_login_page_admin_addItem() {
        driver.navigate().to("http://localhost:8090/client/login");
    }


    @When("user clicks on guest for buy ticket")
    public void user_connect_as_guest_buyTicket() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id("guest")).click();

    }

    @Then("user is navigated to the client page for buy ticket")
    public void user_is_navigated_to_the_buyTicket() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.id("meciuri")).click();

    }
    @And("user clicks on match with id equal to 1")
    public void buyFirstMatchTicket() throws InterruptedException {

        Thread.sleep(2000);
        driver.navigate().to("http://localhost:8090/client/cumpara/bilet/1");
    }
    @Then("user clicks view seats")
    public void seeSeats() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id("veziLocuri")).click();
    }
    @And("user select seat numero 1")
    public void selectSeat() throws InterruptedException {
        driver.findElement(By.name("attribute1")).sendKeys("1");
        Thread.sleep(2000);
    }
    @Then("user clicks on buy")
    public void buyTicketView(){
        driver.findElement(By.id("cumpara")).click();
    }
    @And("user introduces personal data")
    public void personalData1() throws InterruptedException {

        driver.findElement(By.name("CVV")).sendKeys("000");
        driver.findElement(By.name("adresa")).sendKeys("Cluj Napoca,strada NuConteaza");
        driver.findElement(By.name("dataExpirare")).sendKeys("2025-09");
        driver.findElement(By.name("numarCard")).sendKeys("0000000000000000");
        driver.findElement(By.name("numeDetinator")).sendKeys("Nici asta nu conteaza");
        driver.findElement(By.name("numarTelefon")).sendKeys("0101010101");
        Thread.sleep(5000);
    }
    @And("user clicks on cumpara")
    public void cumpara1(){
        driver.findElement(By.id("cumpara")).click();
    }
    @Then("user see his ticket")
    public void seeTicket1() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.className("ticket__barcode"));
        driver.close();
        driver.quit();
    }


}
