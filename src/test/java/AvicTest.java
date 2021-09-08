import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.*;

public class AvicTest {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://avic.ua/");
    }

    @Test(priority = 2)
    public void checkThatLogitechKeyboardsPageShows12Items() {
        WebElement link = driver.findElement(By.xpath("//span[text( )='Компьютеры']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(link).build().perform();
        WebElement link2 = driver.findElement(By.xpath("//li[@class='parent js_sidebar-item']/a[@href='https://avic.ua/klaviaturyi']"));
        actions.moveToElement(link2).build().perform();
        driver.findElement(By.xpath("//img[@src='https://avic.ua/assets/cache/menus/1/logitech-klava-7-prod_xs_menu.jpg']")).click();
        List<WebElement> elementsList = driver.findElements(xpath("//div[@class='prod-cart__descr']"));
        int actualElementsSize = elementsList.size();
        assertEquals(actualElementsSize, 12);
    }

    @Test(priority = 3)
    public void checkThatCheckBoxCallsOnlyHeadphones() {
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("apple watch");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        driver.findElement(By.xpath("//label[@for='fltr-category-662']")).click();
        List<WebElement> elementList = driver.findElements(xpath("//div[@class='prod-cart__descr']"));
        for (WebElement webElement : elementList) {
            assertTrue(webElement.getText().contains("Наушники"));
        }
    }

    @Test(priority = 2)
    public void checkThatUrlContainsWords() {
        driver.findElement(xpath("//a[ text( )='Условия рассрочки' and @class='header-top__item']")).click();
        driver.findElement(xpath("//a[ text( )='Контакты' and @class='header-top__item']")).click();
        assertFalse(driver.getCurrentUrl().contains("usloviya-rassrochki"));
    }

    @Test(priority = 1)
    public void checkThatTotalAmountIsChanged() {
        driver.findElement(By.xpath("//img[@src='https://avic.ua/assets/cache/products/244460/apple-airpods-with-charging-case-mv7n2-1-prod_main.jpg']")).click();
        driver.findElement(By.xpath("//a[@class='main-btn main-btn--green main-btn--large']")).click();
        driver.findElement(By.xpath("//span[@class='js_plus btn-count btn-count--plus ']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='item-total']/span[@class='prise']"), "7398 грн"));
        assertEquals(driver.findElement(By.xpath("//div[@class='item-total']/span[@class='prise']")).getText(), "7398 грн");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
