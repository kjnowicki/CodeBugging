package pl.kjnowicki;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.kjnowicki.mock.MockPage;

import static pl.kjnowicki.FrameworkTests.getElementWithChainedBys;

public class CleanTests {
    ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    public WebDriver getWebDriver() {
        return webDriverThreadLocal.get();
    }

    @BeforeMethod
    public void setupTest() {
        webDriverThreadLocal.set(new ChromeDriver());
        ((JavascriptExecutor) getWebDriver()).executeScript("document.body.appendChild(document.createElement('test-tag'))");
    }

    @AfterMethod
    public void teardownTest() {
        getWebDriver().close();
    }

    @Test(dataProvider = "Stale elements", dataProviderClass = FrameworkTests.class)
    public void StaleElementsNotHandledTest(By... bys) {
        WebElement webElement = getElementWithChainedBys(bys, getWebDriver());
        getWebDriver().navigate().refresh();
        Assert.assertThrows(StaleElementReferenceException.class, webElement::isDisplayed);
    }

    @Test
    public void StaleElementNotThrownWithFindBy() {
        MockPage mockPage = new MockPage(getWebDriver());

        WebElement bodyElement = mockPage.getBodyElement();
        getWebDriver().navigate().refresh();
        bodyElement.isDisplayed();
    }
}
