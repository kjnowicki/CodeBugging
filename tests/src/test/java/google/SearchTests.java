package google;

import pl.kjnowicki.test.TestGroupBase;
import google.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static google.constants.Locale.EN;

public class SearchTests extends TestGroupBase {

    @Test
    public void UrlSearchTest() {
        HomePage homePage = navigateTo("https://google.com", HomePage.class);
        Assert.assertNotNull(homePage.agreementsDialog);
        homePage.agreementsDialog.changeLanguageTo(EN);
        homePage.agreementsDialog.clickAccept();
    }

    @Test
    public void TermSearchTest() {
        navigateTo("https://google.com", HomePage.class);
    }
}
