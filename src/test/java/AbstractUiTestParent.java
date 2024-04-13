import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class AbstractUiTestParent {

    private static final String DRIVER = "DRIVER";

    protected WebDriver driver;

    @Before
    public void init() {
        driver = getDriver();
        driver.manage().window().maximize();
    }

    private WebDriver getDriver() {
        Driver driver;

        try {
            driver = Driver.valueOf(System.getenv(DRIVER));
        } catch (Exception e) {
            driver = Driver.CHROME;
        }

        switch (driver) {
            case CHROME:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

                return new ChromeDriver(options);

            case FIREFOX:
                return new FirefoxDriver();
        }

        throw new RuntimeException("Unreachable code");
    }

    @After
    public void teardown() {
        driver.quit();
    }

}
