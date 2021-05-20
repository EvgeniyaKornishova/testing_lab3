import DriverConfig.currentDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class SimpleInformerTest {
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
        driver!!.findElement(By.xpath("//a[contains(text(), 'Информеры')]")).click()

        // Открываем раздел "Простой информер"
        driver!!.findElement(By.xpath("//a[contains(span, 'Простой информер')]")).click()

        // Выбираем шаблон информера
        driver!!.findElement(By.xpath("//u[contains(text(), '100 × 100')]")).click()

        // Изменяем размер
        driver!!.findElement(By.xpath("//select[@name='size']")).click()
        driver!!.findElement(By.xpath("//option[@value='120x240']")).click()

        // Выбираем тип
        driver!!.findElement(By.xpath("//input[@type='radio' and @name='type' and @value='120x240-2']")).click()

        // Выбираем автоматическое определение местоположения
        driver!!.findElement(By.xpath("//input[@class='checkbox' and @name='cityauto']")).click()

        // Вводим email
        driver!!.findElement(By.xpath("//input[@name='email']")).click()
        driver!!.findElement(By.xpath("//input[@name='email']")).sendKeys("example@example.com")

        // Вводим url сайта
        driver!!.findElement(By.xpath("//input[@id='tuner_field_domain']")).click()
        driver!!.findElement(By.xpath("//input[@id='tuner_field_domain']")).sendKeys("example.com")

        // Проходим капчу
        val captchaFrame = driver!!.findElement(By.xpath("//iframe[@title='reCAPTCHA']"))
        driver!!.switchTo().frame(captchaFrame)

        val wait = WebDriverWait(driver, 300)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='recaptcha-accessible-status' and contains(text(), 'Вы прошли проверку')]")))

        driver!!.switchTo().defaultContent();

        // Нажимаем на кнопку подтверждения формы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='offer__hat' and contains(text(), 'Регистрация стандартного информера')]")))

        Assertions.assertTrue(driver!!.findElement(By.xpath("//h2[contains(text(), 'Регистрация завершена')]")).isDisplayed)
    }
}
