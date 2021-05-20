import DriverConfig.currentDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class GoToForeignVersion {
    companion object {
        private var driver: WebDriver? = null
        var js: JavascriptExecutor? = null

        @JvmStatic
        @BeforeAll
        fun setUp() {
            driver = currentDriver
            js = driver as JavascriptExecutor?
            driver!!.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            driver!!.quit()
        }
    }

    @Test
    fun `test go to foreign version`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Выбираем версию сайта "Polska"
        driver!!.findElement(By.xpath("//img[@alt='Pogoda w Polsce']")).click()

        // Переходим к открывшейся вкладке
        val tabs = ArrayList<String>(driver!!.windowHandles)
        driver!!.switchTo().window(tabs[1])

        val wait = WebDriverWait(driver, 300)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Wiadomości')]")))
        Assertions.assertTrue(driver!!.findElement(By.xpath("//a[contains(text(), 'Wiadomości')]")).isDisplayed)

        // Закрываем открывшуюся вкладку и возвращаемя к стартовой
        driver!!.close()
        driver!!.switchTo().window(tabs[0])
    }
}
