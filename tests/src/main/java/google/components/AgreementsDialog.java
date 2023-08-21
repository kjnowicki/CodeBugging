package google.components;

import pl.kjnowicki.webelement.BasicComponent;
import pl.kjnowicki.webelement.BasicPage;
import google.constants.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AgreementsDialog extends BasicComponent {
    public static final By ROOT_SELECTOR = By.cssSelector("div[role=dialog]");

    @FindBy(css = "button[aria-label]")
    public WebElement languageButton;

    @FindBy(xpath = "//div[count(button)=2]//button")
    public List<WebElement> decisionButtons;


    public static <K extends BasicPage> AgreementsDialog getInstance(K parentPage) {
        return BasicComponent.getInstance(parentPage, AgreementsDialog.class, ROOT_SELECTOR);
    }

    public AgreementsDialog changeLanguageTo(Locale locale) {
        clickElement(languageButton);
        clickElement(findElement(By.cssSelector("*[data-hl=%s]".formatted(locale.value))));
        return this;
    }

    public BasicPage clickAccept() {
        clickElement(decisionButtons.get(1));
        return parentPage;
    }
}
