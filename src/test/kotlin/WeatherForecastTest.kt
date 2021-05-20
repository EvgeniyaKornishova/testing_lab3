import DriverConfig.currentDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class WeatherForecastTest {
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
    fun `test weather forecast successful`() {
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        val searchField = driver!!.findElement(By.xpath("//input[@type = 'search']"))
        searchField.click()
        searchField.sendKeys("Таганрог")

        val firstResult = WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='found__list']/a[contains(@class, 'found__item founditem')]/div/div/span[b='Таганрог']")))
        firstResult.click()


        Assertions.assertTrue(driver!!.findElement(By.xpath("//h1[contains(text(), 'Погода в Таганроге')]")).isDisplayed)

        driver!!.findElement(By.xpath("//a[contains(text(), '10 дней')]")).click()
        Assertions.assertTrue(driver!!.findElement(By.xpath("//h1[contains(text(), 'Погода в Таганроге на 10 дней')]")).isDisplayed)

        driver!!.findElement(By.xpath("//a[contains(text(), 'Ветер')]")).click()
        Assertions.assertTrue(driver!!.findElement(By.xpath("//h3[contains(text(), 'Ветер')]")).isDisplayed)
    }

    @Test
    fun `test weather forecast unsuccessful`() {
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        val searchField = driver!!.findElement(By.xpath("//input[@type = 'search']"))
        searchField.click()
        searchField.sendKeys("Абвгд")

        val firstResult = WebDriverWait(driver, 10)
            .until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='found__title found__notfound']")
                )
            )

        Assertions.assertTrue(driver!!.findElement(By.xpath("//div[contains(text(), 'По вашему запросу ничего не найдено')]")).isDisplayed)
    }
}
