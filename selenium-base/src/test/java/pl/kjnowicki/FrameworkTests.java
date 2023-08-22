package pl.kjnowicki;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.kjnowicki.test.TestGroupBase;

import java.util.NoSuchElementException;

public class FrameworkTests extends TestGroupBase {

    @BeforeMethod
    public void setupHtmlTestStructure() {
        ((JavascriptExecutor) getWebDriver()).executeScript("document.body.appendChild(document.createElement('test-tag'))");
    }

    @Test(dataProvider = "Stale elements")
    public void StaleElementHandlingTest(By... bys) {
        WebElement webElement = getElementWithChainedBys(bys, getWebDriver());
        getWebDriver().navigate().refresh();
        webElement.isDisplayed();
    }

    @Test(dataProvider = "Disappearing stale elements")
    public void DisappearingStaleElementHandlingTest(By... bys) {
        final WebElement webElement = getElementWithChainedBys(bys, getWebDriver());
        getWebDriver().navigate().refresh();
        Assert.assertThrows(NoSuchElementException.class, webElement::isDisplayed);
    }

    @DataProvider(name = "Stale elements", parallel = true)
    public Object[][] staleElementsData() {
        return new Object[][]{
                {By.cssSelector("body")},
                {By.cssSelector("html"), By.xpath(".//body")}
        };
    }

    @DataProvider(name = "Disappearing stale elements", parallel = true)
    public Object[][] disappearingStaleElementsData() {
        return new Object[][]{
                {By.xpath("//html"), By.cssSelector("body"), By.cssSelector("test-tag")}
        };
    }

    public static WebElement getElementWithChainedBys(By[] bys, WebDriver webDriver) {
        WebElement webElement = null;
        for (By by : bys) {
            webElement = webElement != null ? webElement.findElement(by) : webDriver.findElement(by);
        }
        Assert.assertNotNull(webElement);
        return webElement;
    }
}
