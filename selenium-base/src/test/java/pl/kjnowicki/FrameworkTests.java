package pl.kjnowicki;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.kjnowicki.test.TestGroupBase;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class FrameworkTests extends TestGroupBase {

    @BeforeMethod
    public void setupHtmlTestStructure(){
        ((JavascriptExecutor) getWebDriver()).executeScript("document.body.appendChild(document.createElement('test-tag'))");
    }

    @Test(dataProvider = "Stale elements")
    public void StaleElementHandlingTest(By... bys) {
        System.out.println(Arrays.toString(bys));
        WebElement webElement = null;
        for(By by : bys) {
            webElement = webElement != null ? webElement.findElement(by) : getWebDriver().findElement(by);
        }
        Assert.assertNotNull(webElement);
        getWebDriver().navigate().refresh();
        try {
            webElement.isDisplayed();
        } catch (NoSuchElementException ignored) {
        } catch (StaleElementReferenceException staleElement) {
            Assert.fail("Stale element was not handled");
        }
    }

    @DataProvider(name = "Stale elements", parallel = true)
    public Object[][] staleElementsData() {
        return new Object[][]{
                {By.cssSelector("body")},
                {By.cssSelector("html"), By.xpath(".//body")},
                {By.xpath("//html"), By.cssSelector("body"), By.cssSelector("test-tag")}
        };
    }
}
