package pl.kjnowicki.mock;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.kjnowicki.webelement.BasicPage;

@Getter
public class MockPage extends BasicPage {

    @FindBy(tagName = "html")
    private WebElement htmlElement;

    @FindBy(tagName = "body")
    private WebElement bodyElement;

    @FindBy(tagName = "test-tag")
    private WebElement testTagElement;

    public MockPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }
}
