import DriverConfig.currentDriver
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit


class ReadPolicyTest {
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
    fun `test user policy`() {
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()
        driver!!.findElement(By.xpath("//a[span='Соглашение']")).click()
        Assertions.assertTrue(driver!!.findElement(By.xpath("//h1[contains(text(), 'Пользовательское соглашение')]")).isDisplayed)
    }
}
