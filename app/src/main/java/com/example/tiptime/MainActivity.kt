/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { TipTimeTheme { Surface(Modifier.fillMaxSize()) { TipTimeLayout() } } }
    }
}

@Composable
fun TipTimeLayout() {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var tipInput by rememberSaveable { mutableStateOf("") }
    var roundUp by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val tip = calculateTip(parseNumber(amountInput), parseNumber(tipInput), roundUp)

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().imePadding()
            .verticalScroll(rememberScrollState()).padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(stringResource(R.string.calculate_tip), style = MaterialTheme.typography.headlineMedium)
        EditNumberField(
            label = R.string.bill_amount, leadingIcon = R.drawable.money,
            value = amountInput, onValueChanged = { amountInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth()
        )
        EditNumberField(
            label = R.string.how_was_the_service, leadingIcon = R.drawable.percent,
            value = tipInput, onValueChanged = { tipInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.fillMaxWidth()
        )
        RoundTheTipRow(roundUp, { roundUp = it })
        Text(stringResource(R.string.tip_amount, tip), style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    TextField(
        value = value, onValueChange = onValueChanged, modifier = modifier,
        singleLine = true, label = { Text(stringResource(label)) },
        leadingIcon = { Icon(painterResource(leadingIcon), contentDescription = null) },
        keyboardOptions = keyboardOptions, keyboardActions = keyboardActions
    )
}

@Composable
fun RoundTheTipRow(roundUp: Boolean, onRoundUpChanged: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    val label = stringResource(R.string.round_up_tip)
    Row(modifier.fillMaxWidth().heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = roundUp, onCheckedChange = onRoundUpChanged,
            modifier = Modifier.semantics { contentDescription = label })
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() { TipTimeTheme { TipTimeLayout() } }
