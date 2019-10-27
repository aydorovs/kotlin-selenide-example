package util

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Screenshots.takeScreenShotAsFile
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebDriverRunner
import com.google.common.io.Files
import io.qameta.allure.Attachment
import io.qameta.allure.Step
import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.LogManager
import java.io.File

class DataHelper {

    private val waitValue: Long = 60000

    @Step("{0}")
    fun step(
        title: String,
        expected: String,
        stepNumber: Int,
        code: () -> Any
    ) {
        val logger = LogManager.getLogger(Thread.currentThread().stackTrace[2].fileName.replace(".kt", "")::class)
        val message = "\n\nTestTitle: $title\nExpextedResult: $expected\nStepNumber: $stepNumber\n\n"
        try {
            code()
            screenshot()
            logger.info("\u001B[36m\n\n$message \u001B[0m")
        } catch (e: Throwable) {
            screenshot()
            logger.error("\u001B[33m \n\nERROR!!!$message \u001B[0m")
            throw e
        }
    }

    @Step("{0}")
    fun log(
        log: String
    ) {
        val logger = LogManager.getLogger(Thread.currentThread().stackTrace[2].fileName.replace(".kt", "")::class)
        logger.info("\n\u001B[36m\nLogMessage\n${log.replace(". ", ". \n")} \u001B[0m")
    }

    @Step("{0}")
    fun preconditions(
        title: String,
        code: () -> Any
    ) {
        val logger = LogManager.getLogger(Thread.currentThread().stackTrace[2].fileName.replace(".kt", "")::class)
        val message = "\nSTART \"$title\"\n"
        try {
            logger.info("\u001B[36m\n$message \u001B[0m")
            code()
        } catch (e: Throwable) {
            logger.error("\u001B[33m \nERROR!!!$message \u001B[0m")
            throw e
        }
    }

    @Step("Ждем появление элемента")
    fun wait(selenideElement: SelenideElement) = selenideElement.waitUntil(
        Condition.visible, waitValue, 500
    )

    @Attachment(value = "Screenshot", type = "image/png")
    fun screenshot(): ByteArray? {
        return try {
            if (WebDriverRunner.getSelenideDriver().hasWebDriverStarted() && WebDriverRunner.currentFrameUrl().isNotEmpty()) {
                val screenshot = takeScreenShotAsFile()
                FileUtils.copyFile(screenshot, File("target\\allure-results\\screenshots\\" + screenshot.name))
                Files.toByteArray(screenshot)
            } else {
                byteArrayOf()
            }
        } catch (e: Throwable) {
            byteArrayOf()
        }
    }
}