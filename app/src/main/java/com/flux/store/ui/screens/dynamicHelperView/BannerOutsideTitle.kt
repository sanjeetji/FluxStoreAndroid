package com.flux.store.ui.screens.dynamicHelperView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.flux.store.fakeData.fakeNetwork.FakePreview
import com.flux.store.fakeData.fakeNetwork.dataForBannerInSideTitle
import com.flux.store.model.response.HomeBanner

@Composable
fun BannerOutsideTitle(
    banner: HomeBanner,
    itemWidth: Dp,
    imageHeight: Dp = 170.dp,
    isTallBanner: Boolean = false
) {
    val inPreview = LocalInspectionMode.current
    val bannerHeight = if (isTallBanner) 220.dp else imageHeight

    Column(
        Modifier
            .width(itemWidth)                    // ⬅ constrain width for LazyRow items
            .padding(horizontal = if (isTallBanner) 8.dp else 12.dp, vertical = if (isTallBanner) 6.dp else 8.dp)
    ) {
        val imageModifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight)
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
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

@Preview(showBackground = true, showSystemUi = true, name = "Banner – Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Banner – Dark")
@Composable
private fun BannerOutsideTitlePreview() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 100f
    FakePreview(
        fakeData = dataForBannerInSideTitle,
        useUiState = false,          // direct data, no ViewModel
        onSuccess = { banner ->
            BannerOutsideTitle(banner,
                itemWidth = itemWidth,
                imageHeight = 170.dp,
             false
            )
        }
    )
}