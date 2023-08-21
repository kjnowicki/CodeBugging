package pl.kjnowicki.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.decorators.Decorated;
import org.openqa.selenium.support.decorators.WebDriverDecorator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CustomWebDriverDecorator extends WebDriverDecorator<WebDriver> {

    @Override
    public Object onError(Decorated<?> target, Method method, Object[] args, InvocationTargetException e) throws Throwable {
        if(WebElement.class.isAssignableFrom(target.getOriginal().getClass()) && e.getTargetException().getClass() == StaleElementReferenceException.class){
            WebElement refreshedElement = findElementAgain((WebElement) target.getOriginal());
            if(refreshedElement != null) {
                try {
                    return method.invoke(refreshedElement, args);
                } catch (Exception ex) {
                    throw e;
                }
            } else {
                throw new NoSuchElementException();
            }
        }
        throw e;
    }

    private WebElement findElementAgain(WebElement element) {
        String webElementString = element.toString();
        List<String> selectorsList = new ArrayList<>(Arrays.stream(webElementString.split("->")).skip(1).toList());
        for(final ListIterator<String> iterator = selectorsList.listIterator(); iterator.hasNext();) {
            int lastCharsCount = iterator.nextIndex() == selectorsList.size() - 1 ? 1 : 2;
            final String selector = iterator.next().trim();
            iterator.set(selector.substring(0,  selector.length() - lastCharsCount));
        }
        List<WebElement> possibleElements = new ArrayList<>();
        for(By selector : selectorsList.stream().map(this::getByFromString).toList()) {
            if(possibleElements.isEmpty()) {
                possibleElements = getDecoratedDriver().getOriginal().findElements(selector);
            } else {
                List<WebElement> tempPossibleElements = new ArrayList<>();
                for(WebElement webElement : possibleElements) {
                    tempPossibleElements.addAll(webElement.findElements(selector));
                }
                possibleElements = tempPossibleElements;
            }
        }
        return possibleElements.size() != 1 ? null : possibleElements.get(0);
    }

    private By getByFromString(String selectorString) {
        String[] selectorSplit = selectorString.split(":");
        String term = selectorSplit[1];
        switch (selectorSplit[0]) {
            case "xpath" -> {
                return By.xpath(term);
            }
            case "css selector" -> {
                return By.cssSelector(term);
            }
            case "tag name" -> {
                return By.tagName(term);
            }
        }
        return null;
    }
}
