package util

import com.codeborne.selenide.Screenshots.takeScreenShotAsFile
import com.codeborne.selenide.WebDriverRunner
import com.google.common.io.Files
import io.qameta.allure.Attachment
import io.qameta.allure.Step
import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.LogManager
import java.io.File

object Helper {

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