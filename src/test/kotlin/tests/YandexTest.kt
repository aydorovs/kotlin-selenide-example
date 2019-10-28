package tests

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.*
import pages.YandexPage
import util.BaseFunc
import util.Helper

@DisplayName("Проверка поиска yandex")
@Tag("test")
class YandexTest {

    @BeforeEach
    fun startTest() {
        BaseFunc.setUpRemote()
    }

    @AfterEach
    fun endTest() {
        BaseFunc.tearDown()
    }

    @Test
    fun `Validate yandex search!`() {
        Helper.step("Открываем браузер", "Браузер открылся", 1) {
            open("https://ya.ru")
        }
        Helper.step(
            "Вводим текст в строку поиска и нажимаем Enter",
            "Произошел переход на следующую страницу с результатами поиска",
            2
        ) {
            YandexPage.inputSearch.run {
                `val`("selenide")
                pressEnter()
            }
        }
        Helper.step(
            "Проверяем, что среди результатов, есть заголовок с официального сайта Selenide",
            "Заголовок найдее",
            3
        ) {
            YandexPage.searchResults
                .findBy(text("Selenide: лаконичные и стабильные UI тесты на Java"))
                .shouldBe(visible)
        }
    }
}
