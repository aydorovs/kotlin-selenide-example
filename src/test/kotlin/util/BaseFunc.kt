package util

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.openqa.selenium.remote.DesiredCapabilities
import java.util.*

object BaseFunc {

    private val rb: ResourceBundle = ResourceBundle.getBundle("config")
    private val capabilities = DesiredCapabilities()

    fun setUpLocal() {
        SelenideLogger.addListener("AllureSelenide", AllureSelenide().screenshots(true))
        System.setProperty("webdriver.chrome.driver", rb.getString("webdriver"))
        Configuration.startMaximized = true
        Configuration.browserSize = rb.getString("browser_size")
        Configuration.reportsFolder = "target/reports"
        Configuration.pageLoadStrategy = "none"
        Configuration.timeout = 6000
        Configuration.browserCapabilities = capabilities
    }

    fun setUpRemote() {
        Configuration.remote = "http://${rb.getString("host")}:4444/wd/hub"
        capabilities.browserName = rb.getString("browser")
        capabilities.setCapability("enableVNC", true)
        capabilities.setCapability("enableVideo", false)
    }

    fun tearDown() {
        Selenide.close()
    }
}