package pageobject;

import config.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScooterPage {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final WebDriver driver;

    private final By deliveryDateLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    private final By rentalPeriodContainerLocator = By.className("Dropdown-root");
    private final By rentalPeriodFieldLocator = By.className("Dropdown-placeholder");
    private final By rentalPeriodFieldArrowLocator = By.className("Dropdown-arrow");
    private final By rentalPeriodOptionsLocator = By.className("Dropdown-option");

    private final By scooterColorContainerLocator = By.className("Order_Checkboxes__3lWSI");
    private final By scooterBlackColorLocator = By.xpath(".//input[@id='black']");
    private final By scooterGreyColorLocator = By.xpath(".//input[@id='grey']");

    private final By commentForCourierLocator = By.xpath("//input[@placeholder='Комментарий для курьера']");

    private final By orderButtonLocator = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    private final By orderModalWindowLocator = By.className("Order_Modal__YZ-d3");
    private final By confirmOrderButtonLocator = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");

    private final By orderConfirmedModalWindowLocator = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Заказ оформлен')]");

    public ScooterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillDeliveryDate(LocalDate date) {
        driver.findElement(deliveryDateLocator).sendKeys(date.format(DATE_TIME_FORMATTER), Keys.RETURN);
    }

    public void chooseRentalPeriod(String rentalPeriod) {
        WebElement rentalPeriodFieldContainerElement = driver.findElement(rentalPeriodContainerLocator);
        WebElement rentalPeriodFieldElement = rentalPeriodFieldContainerElement.findElement(rentalPeriodFieldLocator);

        rentalPeriodFieldContainerElement.findElement(rentalPeriodFieldArrowLocator).click();

        new WebDriverWait(driver, Duration.ofSeconds(Constants.MAX_WEB_DRIVER_WAIT_SECONDS))
                .until(ExpectedConditions.numberOfElementsToBe(rentalPeriodOptionsLocator, 7));

        List<WebElement> optionElements = rentalPeriodFieldContainerElement.findElements(rentalPeriodOptionsLocator);

        for (WebElement optionElement : optionElements) {
            if (optionElement.getText().equals(rentalPeriod)) {
                optionElement.click();

                new WebDriverWait(driver, Duration.ofSeconds(Constants.MAX_WEB_DRIVER_WAIT_SECONDS))
                        .until(ExpectedConditions.textToBePresentInElement(rentalPeriodFieldElement, rentalPeriod));

                return;
            }
        }

        throw new IllegalArgumentException("Нет возможности аренды самоката на " + rentalPeriod + "!");
    }

    public void chooseScooterColor(String color) {
        WebElement scooterColorContainerElement = driver.findElement(scooterColorContainerLocator);

        switch (color) {
            case "чёрный жемчуг":
                scooterColorContainerElement.findElement(scooterBlackColorLocator).click();
                break;
            case "серая безысходность":
                scooterColorContainerElement.findElement(scooterGreyColorLocator).click();
                break;
            default:
                throw new IllegalArgumentException("Нет такого цвета самоката: " + color + "!");
        }
    }

    public void fillCommentForCourier(String comment) {
        driver.findElement(commentForCourierLocator).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButtonLocator).click();

        new WebDriverWait(driver, Duration.ofSeconds(Constants.MAX_WEB_DRIVER_WAIT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(orderModalWindowLocator));
    }

    public void confirmOrder() {
        driver.findElement(confirmOrderButtonLocator).click();
    }

    public void checkOrderConfirmation() {
        new WebDriverWait(driver, Duration.ofSeconds(Constants.MAX_WEB_DRIVER_WAIT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(orderConfirmedModalWindowLocator));
    }

}
