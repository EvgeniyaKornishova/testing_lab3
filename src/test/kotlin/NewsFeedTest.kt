import DriverConfig.currentDriver
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit


class NewsFeedTest {
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
    fun `test the news feed`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем раздел "Новости"
        driver!!.findElement(By.xpath("//a[contains(text(), 'Новости')]")).click()
        Assertions.assertTrue(driver!!.findElement(By.xpath("//div[contains(text(), 'Новости')]")).isDisplayed)
    }

    @Test
    fun `test news category`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем раздел "Новости"
        driver!!.findElement(By.xpath("//a[contains(text(), 'Новости')]")).click()

        // Открываем категорию "Животные"
        driver!!.findElement(By.xpath("//nav/a[contains(text(), 'Животные')]")).click()

        Assertions.assertTrue(driver!!.findElement(By.xpath("//nav/a[contains(text(), 'Животные') and @class='menu-item is-active']")).isDisplayed)
    }

    @Test
    fun `test YandexDzen subscription`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем раздел "Новости"
        driver!!.findElement(By.xpath("//a[contains(text(), 'Новости')]")).click()

        // Открываем первую новость
        driver!!.findElement(By.xpath("//div[contains(@class, 'card-wrap')]")).click()

        // Нажимаем на кнопку "Подписаться на новости Яндекс.Дзен"
        driver!!.findElement(By.xpath("//a[contains(text(), 'Подпишитесь на наши новости в Яндекс.Дзен')]")).click()

        // Переходим к открывшейся вкладке
        val tabs = ArrayList<String>(driver!!.windowHandles)
        driver!!.switchTo().window(tabs[1])

        Assertions.assertTrue(driver!!.findElement(By.xpath("//div[contains(text(), 'Новости Gismeteo')]")).isDisplayed)

        // Закрываем открывшуюся вкладку и возвращаемя к стартовой
        driver!!.close()
        driver!!.switchTo().window(tabs[0])
    }

    @Test
    fun `test share news`() {
        // Открываем сайт
        driver!!["https://www.gismeteo.ru/"]
        driver!!.manage().window().maximize()

        // Открываем раздел "Новости"
        driver!!.findElement(By.xpath("//a[contains(text(), 'Новости')]")).click()

        // Открываем первую новость
        driver!!.findElement(By.xpath("//div[contains(@class, 'card-wrap')]")).click()

        // Сохраняем название новости
        val newsTitle = driver!!.findElement(By.xpath("//h1[@itemprop='headline']")).text

        // Выбираем опцию "Поделиться в Telegram"
        driver!!.findElement(By.xpath("//span[contains(text(), 'Telegram')]")).click()

        // Переходим к открывшейся вкладке
        val tabs = ArrayList<String>(driver!!.windowHandles)
        driver!!.switchTo().window(tabs[1])

        Assertions.assertTrue(driver!!.findElement(By.xpath("//div[@class='tgme_page_description' and contains(text(), newsTitle)]")).isDisplayed)
        Assertions.assertTrue(driver!!.findElement(By.xpath("//a[contains(text(), 'Share')]")).isDisplayed)

        // Закрываем открывшуюся вкладку и возвращаемя к стартовой
        driver!!.close()
        driver!!.switchTo().window(tabs[0])
    }
}
