package com.flux.store.ui.screens.dynamicHelperView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.fakeData.fakeNetwork.FakePreview
import com.flux.store.fakeData.fakeNetwork.dataForBannerInSideTitle
import com.flux.store.model.response.HomeBanner

@Composable
fun SingleBannerViewInsideTitle(banner: HomeBanner) {

    val inPreview = LocalInspectionMode.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary)
                .clip(shape = RoundedCornerShape(16.dp))

        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.6f)

            ) {
                Text(
                    banner.bannerTitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(Modifier.height(4.dp))
                banner.bannerDescription?.let {
                    Text(
                        it,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            if (inPreview || banner.bannerImage is Int) {
                // PREVIEW or a local drawable resource → use Image + painterResource
                val resId = banner.bannerImage as? Int ?: R.drawable.img_transparent
                Image(
                    painter = painterResource(resId),
                    contentDescription = banner.bannerTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .height(170.dp)
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                // RUNTIME & it's a URL → use Coil's AsyncImage directly
                AsyncImage(
                    model = banner.bannerImage /* String URL */,
                    contentDescription = banner.bannerTitle,
                    placeholder = painterResource(R.drawable.img_transparent),
                    error = painterResource(R.drawable.img_transparent),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .height(170.dp)
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Banner – Light")
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    name = "Banner – Dark"
)
@Composable
private fun BannerInsideTitlePreview() {
    FakePreview(
        fakeData = dataForBannerInSideTitle,
        useUiState = false,          // direct data, no ViewModel
        onSuccess = { banner ->
            SingleBannerViewInsideTitle(banner)
        }
    )
}
