package com.example.borutoapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
// import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
// import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.components.RatingWidget
import com.example.borutoapp.presentation.components.ShimmerEffect
import com.example.borutoapp.ui.theme.HERO_ITEM_HEIGHT
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.topAppBarContentColor
import com.example.borutoapp.util.Constants.BASE_URL

@ExperimentalCoilApi
@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController,
    modifier: Modifier
) {
    val result = handlePagingResult(heroes = heroes, modifier = modifier)
    if(result){
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ){
            items(
        count = heroes.itemCount,
        key = heroes.itemKey(key = { hero -> hero.id }
        ),
        contentType = heroes.itemContentType(
            )
    ) { index ->
        val item = heroes[index]
        item?.let {
            HeroItem(hero = it, navController = navController)
        }
            }
//            ova ako ne raboti nesto da go vratam po staro ama deprecated e
//            zaedno so gornite dva komentirani importi
//            items(
//                items = heroes,
//                key = { hero -> hero.id }
//            ){ hero -> hero?.let{
//                HeroItem(hero = it, navController = navController)
//            }}
        }
    }
}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>,
    modifier: Modifier
): Boolean {
    heroes.apply { 
        val error = when{
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect(modifier = modifier)
                false
            }
            error != null -> {
                EmptyScreen(error = error, heroes = heroes)
                false
            }
            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HeroItem(
    hero: Hero,
    navController: NavHostController
) {
    val painter = rememberImagePainter(data = "$BASE_URL${hero.image}"){
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }
    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ){
        Surface(
            shape = RoundedCornerShape(LARGE_PADDING)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colorScheme.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    maxLines = 3,
                    lineHeight = 1.em,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier
                            .padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )

                }
            }

        }
    }
}

@Preview
@Composable
fun HeroItemPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Some random text about having a nothing. Also the text should contain something that can be displayed in multiple rows. Just like this so if that is all needed then that is it. Otherwise find out more by clicking on the item right here.",
            rating = 4.5,
            power = 100,
            month = "June",
            day = "23rd",
            family = listOf("dds","sdasd"),
            abilities = listOf("Sharringan","Rinnegan"),
            natureTypes = listOf("fire","water","earth")
        ),
        navController = rememberNavController()
    )
}
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemDarkPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Some random text about having a nothing. Also the text should contain something that can be displayed in multiple rows. Just like this so if that is all needed then that is it. Otherwise find out more by clicking on the item right here.",
            rating = 4.5,
            power = 100,
            month = "June",
            day = "23rd",
            family = listOf("dds","sdasd"),
            abilities = listOf("Sharringan","Rinnegan"),
            natureTypes = listOf("fire","water","earth")
        ),
        navController = rememberNavController()
    )
}