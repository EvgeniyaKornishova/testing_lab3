import DriverConfig.currentDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class AppDownloadTest {
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
    fun `test simple informer successful`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем раздел "Информеры"
        driver!!.findElement(By.xpath("//a[contains(text(), 'Приложения')]")).click()

        // Открываем раздел "Виндовс"
        driver!!.findElement(By.xpath("//a[@href='/soft-desktop-windows/']")).click()



        //Assertions.assertTrue(driver!!.findElement(By.xpath("//h2[contains(text(), 'Регистрация завершена')]")).isDisplayed)
    }
}
