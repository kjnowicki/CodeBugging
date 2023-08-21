package pl.kjnowicki.webelement;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Getter
public abstract class RootOfElements {
    @Setter
    protected WebDriver webDriver;

    protected void clickElement(WebElement element) {
        FluentWait<WebDriver> fluentlyWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        fluentlyWait.until(wb -> {
            try {
                element.click();
            } catch (Exception e) {
                return false;
            }
            return true;
        });
    }

    protected WebElement findElement(By selector) {
        FluentWait<WebDriver> fluentlyWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        return fluentlyWait.until(wb -> {
            try {
                return wb.findElement(selector);
            } catch (Exception e) {
                return null;
            }
        });
    }
}
