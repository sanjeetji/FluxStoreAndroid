package com.flux.store.ui.screens.dynamicHelperView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.model.response.DataHeader
import com.flux.store.model.response.HomeBanner

@Composable
fun BannerOutsideTitle(
    banner: HomeBanner,
    itemWidth: Dp,            // ⬅ add this
    imageHeight: Dp = 170.dp
) {
    val inPreview = LocalInspectionMode.current

    Column(
        Modifier
            .width(itemWidth)                    // ⬅ constrain width for LazyRow items
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val imageModifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .clip(RoundedCornerShape(12.dp))

        if (inPreview || banner.bannerImage is Int) {
            val resId = banner.bannerImage as? Int ?: R.drawable.img_home_banner
            Image(
                painter = painterResource(resId),
                contentDescription = banner.bannerTitle,
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
        } else {
            AsyncImage(
                model = banner.bannerImage,
                contentDescription = banner.bannerTitle,
                placeholder = painterResource(R.drawable.img_home_banner),
                error = painterResource(R.drawable.img_home_banner),
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
        }

        Spacer(Modifier.height(1.dp))

        Text(
            text = banner.bannerTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
        )

        banner.bannerDescription?.let {
            Text(
                text = it,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.outlineVariant,
                style = MaterialTheme.typography.labelSmall.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                softWrap = true
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BannerOutsideTitlePreview() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 100f  // tweak 0.80–0.90 to taste
    BannerOutsideTitle(
        HomeBanner(
            bannerId = 1,
            bannerTitle = "Banner Title",
            bannerDescription = "This text is for banner description,This text is for banner description,This text is for banner description,This text is for banner description,This text is for banner description,This text is for banner description",
            bannerImage = "https://images.unsplash.com/photo-1583316174775-bd6dc0e9f298?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            DataHeader("New Collection", "View All")
        ),
        itemWidth,
        170.dp
    )
}