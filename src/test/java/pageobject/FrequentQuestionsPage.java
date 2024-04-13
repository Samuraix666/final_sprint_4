package pageobject;

import config.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FrequentQuestionsPage {

    private final WebDriver driver;

    // Локатор для вопросов на странице
    private final By frequentQuestionsLocator = By.className("accordion__button");

    // Локатор контейнера вопроса и ответа
    private final By questionElementContainerLocator = By.xpath("./../..");

    // Локатор ответа на вопрос внутри контейнера
    private final By answerElementLocator = By.xpath(".//div[contains(@class, 'accordion__panel')]");

    public FrequentQuestionsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAnswerFor(String question) throws InterruptedException {
        List<WebElement> questionElements = driver.findElements(frequentQuestionsLocator);

        for (WebElement questionElement : questionElements) {

            if (questionElement.getText().contains(question)) {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({ behaviour: \"instant\", block: \"center\" });",
                        questionElement
                );

                Thread.sleep(300);

                questionElement.click();
                WebElement questionElementContainer = questionElement.findElement(questionElementContainerLocator);
                WebElement answerElement = questionElementContainer.findElement(answerElementLocator);

                new WebDriverWait(driver, Duration.ofSeconds(Constants.MAX_WEB_DRIVER_WAIT_SECONDS))
                        .until(ExpectedConditions.visibilityOf(answerElement));

                return answerElement.getText();
            }

        }

        throw new IllegalArgumentException("Не найден вопрос на странице! Вопрос: " + question);
    }

}
