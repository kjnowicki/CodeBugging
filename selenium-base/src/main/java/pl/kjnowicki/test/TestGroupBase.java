package pl.kjnowicki.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pl.kjnowicki.webdriver.CustomWebDriverDecorator;
import pl.kjnowicki.webelement.BasicPage;

public class TestGroupBase {
    ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    public WebDriver getWebDriver() {
        return webDriverThreadLocal.get();
    }

    public <T extends BasicPage> T getPage(Class<T> tClass) {
        T pageObject = PageFactory.initElements(getWebDriver(), tClass);
        pageObject.setWebDriver(getWebDriver());
        return pageObject;
    }

    public <T extends BasicPage> T navigateTo(String url, Class<T> tClass) {
        getWebDriver().get(url);
        return getPage(tClass);
    }

    @BeforeMethod
    public void setupTest() {
        webDriverThreadLocal.set(new CustomWebDriverDecorator().decorate(new ChromeDriver()));
    }

    @AfterMethod
    public void teardownTest() {
        getWebDriver().close();
    }
}
