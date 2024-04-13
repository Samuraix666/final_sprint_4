package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerPage {

    private final WebDriver driver;

    private final By firstNameLocator = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameLocator = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By subwayStationLocator = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneNumberLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By proceedButtonLocator = By.xpath(("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']"));

    public CustomerPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstName(String firstName) {
        WebElement firstNameElement = driver.findElement(firstNameLocator);

        firstNameElement.clear();
        firstNameElement.sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        WebElement lastNameElement = driver.findElement(lastNameLocator);

        lastNameElement.clear();
        lastNameElement.sendKeys(lastName);
    }

    public void fillAddress(String address) {
        WebElement addressElement = driver.findElement(addressLocator);

        addressElement.clear();
        addressElement.sendKeys(address);
    }

    public void fillSubwayStation(String subwayStation) {
        WebElement addressElement = driver.findElement(subwayStationLocator);

        addressElement.clear();
        addressElement.sendKeys(subwayStation, Keys.ARROW_DOWN, Keys.RETURN);
    }

    public void fillPhoneNumber(String phoneNumber) {
        WebElement phoneNumberElement = driver.findElement(phoneNumberLocator);

        phoneNumberElement.clear();
        phoneNumberElement.sendKeys(phoneNumber);
    }

    public void proceed() {
        driver.findElement(proceedButtonLocator).click();
    }

}
