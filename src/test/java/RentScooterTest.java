import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pageobject.CustomerPage;
import pageobject.ScooterPage;

import java.time.LocalDate;

@RunWith(Parameterized.class)
public class RentScooterTest extends AbstractUiTestParent {

    private final By orderButtonLocator;

    public RentScooterTest(By orderButtonLocator) {
        this.orderButtonLocator = orderButtonLocator;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                { By.xpath("//div[@class='Header_Nav__AGCXC']/button[contains(@class, 'Button_Button__ra12g')]") },
                { By.xpath("//div[@class='Home_FinishButton__1_cWm']/button[contains(@class, 'Button_Button__ra12g')]") }
        };
    }

    @Test
    public void rentScooterTest() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        WebElement orderButtonElement = driver.findElement(orderButtonLocator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({ behaviour: \"instant\", block: \"center\" });",
                orderButtonElement
        );

        orderButtonElement.click();

        CustomerPage customerPage = new CustomerPage(driver);

        customerPage.fillFirstName("Иван");
        customerPage.fillLastName("Иванов");
        customerPage.fillAddress("ул. Пушкина, д. 72");
        customerPage.fillSubwayStation("Орехова");
        customerPage.fillPhoneNumber("77711225601");
        customerPage.proceed();

        ScooterPage scooterPage = new ScooterPage(driver);
        scooterPage.fillDeliveryDate(LocalDate.of(2024, 4, 15));
        scooterPage.chooseRentalPeriod("сутки");
        scooterPage.chooseScooterColor("серая безысходност");
        scooterPage.fillCommentForCourier("можете не торопиться");
        scooterPage.clickOrderButton();
        scooterPage.confirmOrder();
        scooterPage.checkOrderConfirmation();
    }

}
