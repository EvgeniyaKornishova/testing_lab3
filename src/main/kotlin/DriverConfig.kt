import PropertyNames.CHROME_DRIVER
import PropertyNames.CURRENT_DRIVER
import PropertyNames.FIREFOX_DRIVER
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

object DriverConfig {
    val currentDriver: WebDriver
        get() {
            return when (ConfProperties.getProperty(CURRENT_DRIVER)) {
                "firefox" -> FirefoxDriver()
                "chrome" -> ChromeDriver()
                else -> ChromeDriver()
            }
        }

    init {
        val chrome = ConfProperties.getProperty(CHROME_DRIVER)
        val firefox = ConfProperties.getProperty(FIREFOX_DRIVER)
        when (ConfProperties.getProperty(CURRENT_DRIVER)) {
            "firefox" -> System.setProperty("webdriver.gecko.driver", firefox)
            "chrome" -> System.setProperty("webdriver.chrome.driver", chrome)
            else -> System.setProperty("webdriver.chrome.driver", chrome)
        }
    }
}
