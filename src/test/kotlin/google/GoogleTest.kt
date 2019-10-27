package google

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import util.DataHelper
import java.util.*


class GoogleTest {

    private val capabilities = DesiredCapabilities()
    private val chrome_options = ChromeOptions()
    private val rb: ResourceBundle = ResourceBundle.getBundle("config")
    private val data = DataHelper()


    @BeforeEach
    fun setUp() {
        Configuration.remote = "http://${rb.getString("host")}:4444/wd/hub"
        capabilities.browserName = rb.getString("browser")
        capabilities.setCapability("enableVNC", true)
        capabilities.setCapability("enableVideo", false)
        //proxy.httpProxy = "#####"
        //proxy.sslProxy = "#####"
        //capabilities.setCapability(CapabilityType.PROXY, proxy)

        SelenideLogger.addListener("AllureSelenide", AllureSelenide().screenshots(true))
//        System.setProperty("webdriver.chrome.driver", rb.getString("webdriver"))
        Configuration.startMaximized = true
        Configuration.browserSize = rb.getString("browser_size")
        Configuration.baseUrl = rb.getString("url")
        Configuration.reportsFolder = "target/reports"
        Configuration.pageLoadStrategy = "none"
        Configuration.timeout = 6000
        Configuration.browserCapabilities = capabilities
        chrome_options.addArguments("--disable-extensions")
    }

    @Test
    @Tag("test")
    fun userCanSearchAnyKeyword() {
        data.step("Открываем браузер", "Браузер открылся", 1) {
            open("https://ya.ru")
        }
        data.step(
            "Вводим текст в строку поиска и нажимаем Enter",
            "Произошел переход на следующую страницу с результатами поиска",
            2
        ) {
            `$x`("//input[@class='input__control input__input']").`val`("selenide").pressEnter()
        }
        data.step(
            "Проверяем, что среди результатов, есть заголовок с официального сайта Selenide",
            "Заголовок найдее",
            3
        ) {
            `$$x`("//div[@class='organic__url-text']").findBy(text("Selenide: лаконичные и стабильные UI тесты на Java"))
                .shouldBe(visible)
        }
    }

    @AfterEach
    fun tearDown() {
        close()
    }
}
