package pages

import com.codeborne.selenide.Selenide.`$$x`
import com.codeborne.selenide.Selenide.`$x`

object YandexPage {

    val inputSearch = `$x`("//input[@class='input__control input__input']")
    val searchResults = `$$x`("//div[@class='organic__url-text']")
}