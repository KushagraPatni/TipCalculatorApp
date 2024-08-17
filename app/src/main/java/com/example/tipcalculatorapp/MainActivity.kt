package com.example.tipcalculatorapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculatorapp.components.InputField
import com.example.tipcalculatorapp.ui.theme.TipCalculatorAppTheme
import com.example.tipcalculatorapp.utils.calculateTotalPerPerson
import com.example.tipcalculatorapp.utils.calculateTotalTip
import com.example.tipcalculatorapp.widgets.RoundedTconButton


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApp {
                MainContent()
           }
        }
    }
}
@Composable
fun MyApp(content: @Composable () -> Unit ){
    TipCalculatorAppTheme {
        // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colorScheme.background) {
        content()
    }
    }
}




//@Preview
@Composable
fun TopHeader(totalPerPerson :Double =134.0){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total ="%.2f".format(totalPerPerson)
            Text(text = "Per Person Split",
                style = MaterialTheme.typography.titleMedium)
            Text(text = "$$total",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorAppTheme {
        MyApp {
            MainContent()
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier=Modifier,
             onValChange: (String)->Unit
){
    val totalBillState= remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState.value *100).toInt()
    val splitByState = remember {
        mutableStateOf(1)
    }
    val range = IntRange(start = 1, endInclusive = 100)
    val tipAmountState= remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    TopHeader(totalPerPerson = totalPerPersonState.value)
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            InputField(valueState =totalBillState ,
                labelId ="Enter Bill" ,
                enabled = true,
                isSingleLine =true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                    //Todo- on value changed
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )
            // if (validState){
                Row (modifier=Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start){
                        Text(text = "Split Among",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically))
                        Spacer(modifier = Modifier.width(120.dp))
                        Row (modifier=Modifier.padding(horizontal = 3.dp),
                            horizontalArrangement = Arrangement.End){
                            RoundedTconButton(imageVector =Icons.Default.ArrowBack ,
                                onClick = {
                                    splitByState.value=
                                    if(splitByState.value>1)  splitByState.value-1
                                    else 1
                                    totalPerPersonState.value=
                                        calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                            tipPercentage = tipPercentage,
                                            splitBy =splitByState.value )
                                })
                            Text(text = "${splitByState.value}",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 9.dp, end = 9.dp))
                            RoundedTconButton(imageVector =Icons.Default.ArrowForward,
                                onClick = {
                                    if (splitByState.value<range.last){
                                        splitByState.value++
                                    }
                                    totalPerPersonState.value=
                                        calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                            tipPercentage = tipPercentage,
                                            splitBy =splitByState.value )
                                })
                        }
                }
            //Tip Row
            Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                Text(text = "Tip",
                    modifier = Modifier.align(alignment =Alignment.CenterVertically ))
                Spacer(modifier = Modifier.width(200.dp))

                Text(text = "${tipAmountState.value}",
                    modifier = Modifier.align(alignment =Alignment.CenterVertically ))
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$tipPercentage %")

                Spacer(modifier = Modifier.height(14.dp))

                //Slider
                Slider(
                    value = sliderPositionState.value,
                    onValueChange = { newVal ->
                        sliderPositionState.value = newVal
                        val updatedTipPercentage = (sliderPositionState.value * 100).toInt()
                        tipAmountState.value = calculateTotalTip(
                            totalBill = totalBillState.value.toDoubleOrNull() ?: 0.0,
                            tipPercentage = updatedTipPercentage
                        )
                        totalPerPersonState.value=
                            calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                tipPercentage = updatedTipPercentage,
                                splitBy =splitByState.value )
                    },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5
                )

            }

            //}
//            else {
//                Box(){}
//            }
        }
    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent() {
    Column {
        BillForm() { billAmt ->
            Log.d("AMT", "Main Content: $billAmt")
        }
    }
}


