package br.senai.sp.jandira.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicalculator.calcs.bmiBackground
import br.senai.sp.jandira.bmicalculator.calcs.bmiCalculate
import br.senai.sp.jandira.bmicalculator.calcs.getBmiClassification
import br.senai.sp.jandira.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val p = Product()
//        val c = Client(
//            id = 100,
//            name = "Luiz",
//            birthday = LocalDate.of(2006, 2, 15)
//        )
//        p.id = 100
//        p.name = "Mouse"
//        p.price = 230.0
//        var x = p.addName()

        setContent {
            BMICalculatorTheme {
                CalculatorScreen()
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun CalculatorScreen() {

        var weightState = rememberSaveable() {
            mutableStateOf("")
        }

        var heightState = rememberSaveable() {
            mutableStateOf("")
        }

        var bmiState = rememberSaveable() {
            mutableStateOf("")
        }

        var bmiClassificationState = rememberSaveable() {
            mutableStateOf("")
        }

        var context = LocalContext.current

        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.bmi
                        ),
                        contentDescription = "Logo da Aplicação",
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.title
                        ),
                        color = Color.Blue,
                        fontSize = 30.sp,
                        letterSpacing = 8.sp,
                    )
                }
                //FORM
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.weight_label
                        ),
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        fontSize = 15.sp
                    )
                    OutlinedTextField(
                        value = weightState.value,
                        onValueChange = {
                            weightState.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.height_label
                        ),
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp,
                                top = 16.dp
                            ),
                        fontSize = 15.sp
                    )
                    OutlinedTextField(
                        value = heightState.value,
                        onValueChange = {
                            heightState.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(32.dp))



                    Button(
                        onClick = {
                            var bmi = bmiCalculate(
                                weight = weightState.value.toDouble(),
                                height = heightState.value.toDouble()
                            )
                            bmiState.value = bmi.toString()
                            bmiClassificationState.value = getBmiClassification(bmi, context)
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(Color.Green)
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.button_text
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                //FOOTER
                Column(
                    modifier = Modifier
                        .fillMaxSize()//79 54 232
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = if(bmiState.value.isEmpty()){
                            Color(33,110,243)
                        }else{
                            bmiBackground(bmiState.value.toDouble())
                        },
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.your_score),
                                color = Color.White,
                                fontSize = 24.sp,
                            )
                            Text(
                                text = if (bmiState.value.isEmpty()){
                                    bmiState.value
                                }else{
                                    String.format("%.2f",bmiState.value.toDouble())
                                     },
                                color = Color.White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = bmiClassificationState.value,
                                color = Color.White,
                                fontSize = 18.sp,
                            )
                            Row() {
                                Button(onClick = { /*TODO*/ }) {
                                    Text(
                                        text = stringResource(id = R.string.reset),
                                        color = Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.width(32.dp))
                                Button(onClick = { /*TODO*/ }) {
                                    Text(
                                        text = stringResource(id = R.string.share),
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
