import DriverConfig.currentDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class GoToInstargamTest {
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
    fun `test instagram`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем пункт меню "Новости" и нажмаем кнопку перехода к Instagram-аккаунту
        driver!!.findElement(By.xpath("//a[contains(text(), 'Новости')]")).click()
        driver!!.findElement(By.xpath("//a[@class='btn btn-instagram btn-with-icon']")).click()

        // Переходим к открывшейся вкладке
        val tabs = ArrayList<String>(driver!!.windowHandles)
        driver!!.switchTo().window(tabs[1])

        Assertions.assertTrue(driver!!.findElement(By.xpath("//h2[text()='gismeteo']")).isDisplayed)

        // Закрываем открывшуюся вкладку и возвращаемя к стартовой
        driver!!.close()
        driver!!.switchTo().window(tabs[0])
    }
}
