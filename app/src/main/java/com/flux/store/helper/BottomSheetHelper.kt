package com.flux.store.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.R
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.ui.theme.LightBlackColorTitle
import com.flux.store.ui.theme.LightWhiteColor
import com.flux.store.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetHelper(
    onDismiss: () -> Unit,
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    iconRes: Int = R.drawable.ic_password_changed
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Hidden
        }
    )

    ModalBottomSheet(
        onDismissRequest     = { /* no-op: disable outside-tap */ },
        sheetState           = sheetState,
        sheetGesturesEnabled = false,
        dragHandle           = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteColor)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 4.dp)
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(5.dp)   // 5 dp corner radius
                        )
                )
            }
        },
        shape           = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor  = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))  // space below the handle

            Box(
                modifier = Modifier
                    .size(84.dp)
                    .background(LightWhiteColor, shape = RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).padding(8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text      = title,
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text      = description,
                style = MaterialTheme.typography.bodySmall,
                color     = LightBlackColorTitle,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    onButtonClick()
                    onDismiss()   // dismiss only via this button
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(buttonText, fontSize = 16.sp)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}





@Preview(showBackground = true)
@Composable
fun BottomSheetHelperPreview() {
    ComposeAppTheme {
        BottomSheetHelper(
            onDismiss = {},
            title = tr(R.string.your_password_has_changed),
            description = tr(R.string.discover_now),
            buttonText = tr(R.string.browse_home),
            onButtonClick = {}
        )
    }
}
