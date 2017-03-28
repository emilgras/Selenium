/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author emilgras
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTest {

    final int WAIT = 5;
    static WebDriver driver;

    public SeleniumTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "/Users/emilgras/Documents/Software/test/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:3000");
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test1() {
        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));
                return trows.size() == 5;
            }
        });
    }

    @Test
    public void test2() {
        WebElement input = driver.findElement(By.id("filter"));
        input.sendKeys("2002");
        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));
                return trows.size() == 2;
            }
        });
    }

    @Test
    public void test3() {
        WebElement input = driver.findElement(By.id("filter"));
        input.sendKeys("\b\b\b\b");
        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));
                return trows.size() == 5;
            }
        });
    }

    @Test
    public void test4() {
        WebElement sortLink = driver.findElement(By.id("h_year"));
        sortLink.click();
        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));

                // WebElement row1 = trows.get(0).findElement(By.xpath("//*[@id=\"tbodycars\"]/tr[1]/td[1]"));
                // WebElement row5 = trows.get(4).findElement(By.xpath("//*[@id=\"tbodycars\"]/tr[5]/td[1]"));
                WebElement row1 = trows.get(0).findElements(By.tagName("td")).get(0);
                WebElement row5 = trows.get(4).findElements(By.tagName("td")).get(0);
                return row1.getText().equals("938") && row5.getText().equals("940");
            }
        });
    }

    @Test
    public void test5() {

        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                WebElement descriptionInput = driver.findElement(By.id("description"));
                WebElement saveButton = driver.findElement(By.id("save"));
                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));

                descriptionInput.sendKeys("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\bCool car");

                saveButton.click();

                for (WebElement trow : trows) {
                    List<WebElement> tds = trow.findElements(By.tagName("td"));
                    if (tds.get(0).getText().equals("938")) {

                        // Click edit
                        List<WebElement> ankers = trow.findElements(By.tagName("a"));
                        WebElement edit = ankers.get(0);
                        edit.click();

                        // Check if description is updated
                        String description = tds.get(5).getText();
                        return description.equals("Cool car");

                    }
                }
                System.out.println("Failed");
                return false;
            }
        });
    }

    @Test
    public void test6() {

        WebElement newButton = driver.findElement(By.id("new"));
        WebElement saveButton = driver.findElement(By.id("save"));
        newButton.click();
        saveButton.click();

        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement message = driver.findElement(By.id("submiterr"));
                return message.getText().equals("All fields are required");
            }
        });
    }

    @Test
    public void test7() {

        WebElement newButton = driver.findElement(By.id("new"));
        WebElement saveButton = driver.findElement(By.id("save"));

        WebElement yearInput = driver.findElement(By.id("year"));
        WebElement registeredInput = driver.findElement(By.id("registered"));
        WebElement makeInput = driver.findElement(By.id("make"));
        WebElement modelInput = driver.findElement(By.id("model"));
        WebElement descriptionInput = driver.findElement(By.id("description"));
        WebElement priceInput = driver.findElement(By.id("price"));

        newButton.click();

        yearInput.sendKeys("2008");
        registeredInput.sendKeys("2002-5-5");
        makeInput.sendKeys("Kia");
        modelInput.sendKeys("Rio");
        descriptionInput.sendKeys("As new");
        priceInput.sendKeys("31000");
        saveButton.click();

        (new WebDriverWait(driver, WAIT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement tbody = driver.findElement(By.id("tbodycars"));
                List<WebElement> trows = tbody.findElements(By.tagName("tr"));
                return trows.size() == 6;
            }
        });
    }

}
