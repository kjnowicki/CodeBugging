package google.pages;

import pl.kjnowicki.webelement.BasicPage;
import google.components.AgreementsDialog;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasicPage {
    public AgreementsDialog agreementsDialog;

    @SuppressWarnings("unused")
    public HomePage(WebDriver webDriver) {
        this.setWebDriver(webDriver);
        agreementsDialog = AgreementsDialog.getInstance(this);
    }
}
