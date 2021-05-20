import DriverConfig.currentDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class WeatherDiaryTest {
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
    fun `test weather diary successful`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем дневник погоды
        driver!!.findElement(By.xpath("//a[contains(text(), 'Ещё')]")).click()
        driver!!.findElement(By.xpath("//a[contains(text(), 'Дневник')]")).click()

        //Выбираем страну
        driver!!.findElement(By.xpath("//select[@name='sd_country']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Россия')]")).click()

        //Выбираем область
        driver!!.findElement(By.xpath("//select[@name='sd_distr']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Вологодская область')]")).click()

        //Выбираем город
        driver!!.findElement(By.xpath("//select[@name='sd_city']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Бабаево')]")).click()

        //Выбираем месяц
        driver!!.findElement(By.xpath("//select[@name='Month']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Январь')]")).click()

        //Выбираем год
        driver!!.findElement(By.xpath("//select[@name='Year']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), '2008')]")).click()

        //Нажимаем на кнопку "Получить дневник"
        driver!!.findElement(By.xpath("//button[contains(text(), 'Получить дневник')]")).click()

        Assertions.assertTrue(driver!!.findElement(By.xpath("//h1[contains(text(), 'Дневник погоды в Бабаево за Январь 2008 г.')]")).isDisplayed)
    }

    @Test
    fun `test weather diary unsuccessful`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем дневник погоды
        driver!!.findElement(By.xpath("//a[contains(text(), 'Ещё')]")).click()
        driver!!.findElement(By.xpath("//a[contains(text(), 'Дневник')]")).click()

        //Выбираем страну
        driver!!.findElement(By.xpath("//select[@name='sd_country']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Россия')]")).click()

        //Выбираем область
        driver!!.findElement(By.xpath("//select[@name='sd_distr']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Вологодская область')]")).click()

        //Выбираем город
        driver!!.findElement(By.xpath("//select[@name='sd_city']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Бабаево')]")).click()

        //Выбираем месяц
        driver!!.findElement(By.xpath("//select[@name='Month']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), 'Январь')]")).click()

        //Выбираем год
        driver!!.findElement(By.xpath("//select[@name='Year']")).click()
        driver!!.findElement(By.xpath("//option[contains(text(), '1997')]")).click()

        //Нажимаем на кнопку "Получить дневник"
        driver!!.findElement(By.xpath("//button[contains(text(), 'Получить дневник')]")).click()

        Assertions.assertTrue(driver!!.findElement(By.xpath("//label[contains(text(), 'Наблюдения метео-данных в данный период не велись.')]")).isDisplayed)
    }
}
