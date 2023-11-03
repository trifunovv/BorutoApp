package com.example.borutoapp.data.remote

import com.example.borutoapp.domain.model.ApiResponse
import com.example.borutoapp.domain.model.Hero
import java.io.IOException

class FakeBorutoApi2: BorutoApi {

    private val heroes: Map<Int, List<Hero>> by lazy {
        mapOf(
            1 to page1,
            2 to page2,
            3 to page3,
            4 to page4,
            5 to page5
        )
    }

    private var page1 = listOf(Hero(
        id = 1,
        name = "Sasuke",
        image = "",
        about = "",
        rating = 5.0,
        power = 0,
        month = "",
        day = "",
        family = listOf(),
        abilities = listOf(),
        natureTypes = listOf()
    ),
        Hero(
            id = 2,
            name = "Naruto",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        Hero(
            id = 3,
            name = "Sakura",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ))
    private var page2 = listOf(Hero(
        id = 4,
        name = "Boruto",
        image = "",
        about = "",
        rating = 5.0,
        power = 0,
        month = "",
        day = "",
        family = listOf(),
        abilities = listOf(),
        natureTypes = listOf()
    ),
        Hero(
            id = 5,
            name = "Sarada",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        Hero(
            id = 6,
            name = "Mitsuki",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ))
    private var page3 = listOf(Hero(
        id = 7,
        name = "Kawaki",
        image = "",
        about = "",
        rating = 5.0,
        power = 0,
        month = "",
        day = "",
        family = listOf(),
        abilities = listOf(),
        natureTypes = listOf()
    ),
        Hero(
            id = 8,
            name = "Orochimaru",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        Hero(
            id = 9,
            name = "Kakashi",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ))
    private var page4 = listOf(Hero(
        id = 10,
        name = "Isshiki",
        image = "",
        about = "",
        rating = 5.0,
        power = 0,
        month = "",
        day = "",
        family = listOf(),
        abilities = listOf(),
        natureTypes = listOf()
    ),
        Hero(
            id = 11,
            name = "Momoshiki",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        Hero(
            id = 12,
            name = "Urashiki",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ))
    private var page5 = listOf(Hero(
        id = 13,
        name = "Code",
        image = "",
        about = "",
        rating = 5.0,
        power = 0,
        month = "",
        day = "",
        family = listOf(),
        abilities = listOf(),
        natureTypes = listOf()
    ),
        Hero(
            id = 14,
            name = "Amado",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        Hero(
            id = 15,
            name = "Koji",
            image = "",
            about = "",
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ))

    fun clearData(){
        page1 = emptyList()
    }

    private var exception = false

    fun addException(){
        exception = true
    }

    override suspend fun getAllHeroes(page: Int): ApiResponse {
        if(exception){
            throw IOException()
        }
        require(page in 1..5)
        return ApiResponse(
            success = true,
            message = "ok",
            prevPage = calculate(page = page)["prevPage"],
            nextPage = calculate(page = page)["nextPage"],
            heroes = heroes[page]!!
        )
    }

    override suspend fun searchHeroes(name: String): ApiResponse {
        return ApiResponse(
            success = false
        )
    }

    private fun calculate(page: Int): Map<String, Int?> {
        if (page1.isEmpty()) {
            return mapOf("prevPage" to null, "nextPage" to null)
        }
        var prevPage: Int? = page
        var nextPage: Int? = page
        if (page in 1..4){
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..5){
            prevPage = prevPage?.minus(1)
        }
        if (page == 1){
            prevPage = null
        }
        if (page == 5){
            nextPage = null
        }
        return mapOf("prevPage" to prevPage, "nextPage" to nextPage)
    }
}