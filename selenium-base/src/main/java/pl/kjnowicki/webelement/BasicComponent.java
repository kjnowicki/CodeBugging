package pl.kjnowicki.webelement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasicComponent extends RootOfElements {
    public WebElement rootElement;
    public BasicPage parentPage;
    public static <P extends BasicPage, C extends BasicComponent> C getInstance(P parentPage, Class<C> componentClass, By rootSelector){
        WebDriver webDriver = parentPage.getWebDriver();
        List<WebElement> rootElements = webDriver.findElements(rootSelector);
        if(!rootElements.isEmpty()){
            C component = PageFactory.initElements(webDriver, componentClass);
            component.rootElement = rootElements.get(0);
            component.parentPage = parentPage;
            component.setWebDriver(parentPage.getWebDriver());
            return component;
        }
        return null;
    }

    @Override
    protected WebElement findElement(By selector) {
        FluentWait<WebDriver> fluentlyWait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(2));
        return fluentlyWait.until(wb -> {
           try {
               return rootElement.findElement(selector);
           } catch (Exception e) {
               return null;
           }
        });
    }
}
