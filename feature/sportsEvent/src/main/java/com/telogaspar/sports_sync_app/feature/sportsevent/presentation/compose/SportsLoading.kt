package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SportsLoading() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.align(Alignment.CenterVertically)) {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        RangeSelectorLoading()
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun RangeSelectorLoading() {
    Row(modifier = Modifier.fillMaxWidth()) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}