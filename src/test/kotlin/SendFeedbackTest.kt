import DriverConfig.currentDriver
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

class SendFeedbackTest {
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
    fun `test send feedback`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем раздел "Информеры"
        driver!!.findElement(By.xpath("//div[@class='footer_item feedback']")).click()

        val dialogFrame = driver!!.findElement(By.xpath("//div[@id='ue-dlg-content']/iframe"))
        driver!!.switchTo().frame(dialogFrame)

        driver!!.findElement(By.xpath("//input[@name='header']")).click()
        driver!!.findElement(By.xpath("//input[@name='header']")).sendKeys("Заголовок обращения")

        driver!!.findElement(By.xpath("//div[contains(@class,'ue-content ue-editor redactor-in')]")).click()
        driver!!.findElement(By.xpath("//div[contains(@class,'ue-content ue-editor redactor-in')]")).sendKeys("Описание обращения")

        val charPool : List<Char> = ('a'..'z') + ('A'..'Z')
        var email = (1..15)
            .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        email += "@example.com"

        driver!!.findElement(By.xpath("//input[@id='id_email']")).click()
        driver!!.findElement(By.xpath("//input[@id='id_email']")).sendKeys(email)

        // Wait for captcha
        Thread.sleep(30000)

        driver!!.findElement(By.xpath("//button[@id='id_submit_btn']")).click()


        val wait = WebDriverWait(driver, 300)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='forum-posts']")))

        Assertions.assertTrue(driver!!.findElement(By.xpath("//h4[contains(text(), 'Спасибо за ваше обращение')]")).isDisplayed)

        driver!!.switchTo().defaultContent();

    }

}