package com.example.qrgenerator.core.presentation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleKBTextField(
    value: String,
    title: String,
    enabled: Boolean,
    icon: Painter?,
    onChange: (String) -> Boolean,
    keyboardOptions: KeyboardOptions,
    hideKeyboard: Boolean = false,
    onFocusLost: () -> Unit = {}
){
    var isError: Boolean by remember{ mutableStateOf(false) }
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val lineColor = if (isError) MaterialTheme.colors.error
    else Color.Gray
    //Keyboard
    val focusManager = LocalFocusManager.current

    BasicTextField(
        enabled = enabled,
        value = value,
        textStyle = TextStyle(color = MaterialTheme.colors.onBackground, textAlign = TextAlign.Center),
        onValueChange = {
            isError = !onChange(it)
        },
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = "",
                innerTextField = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Row(
                            modifier = Modifier.weight(2F),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            if (icon != null)
                                Icon(
                                    painter = icon,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                            Text(text = "$title: ")
                        }
                        Row(
                            modifier = Modifier
                                .weight(3F)
                                .fillMaxWidth()
                                .indicatorLine(
                                    enabled = enabled,
                                    isError = isError,
                                    interactionSource = interactionSource,
                                    TextFieldDefaults.textFieldColors(unfocusedIndicatorColor = lineColor)
                                )
                        ){
                            innerTextField()
                        }
                    }
                },
                enabled = enabled,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                trailingIcon = {
                    //2023/02/27 Alan D.- No se indica nada en este bloque porque no se utiliza ningún trailing icon. La razón de
                    //agregar el argumento es para que el componente agregue el espacio del ícono y quede alineado correctamente
                }
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Next) }
        )
    )
    if (hideKeyboard){
        focusManager.clearFocus()
        onFocusLost()
    }
}


@Composable
fun SimpleTextField(
    value: String,
    title: String,
    enabled: Boolean,
    icon: Painter?,
    onChange: (String) -> Boolean,
    hideKeyboard: Boolean = false,
    onFocusLost: () -> Unit = {}
) {
    SimpleKBTextField(
        value = value,
        title = title,
        enabled = enabled,
        icon = icon,
        onChange = onChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        hideKeyboard = hideKeyboard,
        onFocusLost = onFocusLost
    )
}
