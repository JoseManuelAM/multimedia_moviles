package com.pmym.practica2

import SampleData
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmym.practica2.ui.theme.MyApplicationTheme

class Practica2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body:String)

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages){
            message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation(){
    MyApplicationTheme{
        Conversation(SampleData.conversationSample)
    }
}
@Composable
fun MessageCard(msg: Message) {
    Column {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                //.background(color= Color.Yellow)
        ) {
            Image(
                painter = painterResource(R.drawable.mirage),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false)}
            val surfaceColor by animateColorAsState(
                if(isExpanded) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )

            Column(
                modifier = Modifier.clickable{ isExpanded = !isExpanded}
            ) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier.animateContentSize().padding(1.dp)){
                    Text(
                        text = msg.body,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = if(isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .align(Alignment.End)
            ) {
                Column {
                    Text(
                        text = msg.body,
                        modifier = Modifier
                            .background(color = Color.Green)
                    )
                }
            }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Preview(showBackground = true)
@Composable
fun MessageCardPreview(){
    MyApplicationTheme {
        Surface{
            MessageCard(msg = Message("Basim", "¿Cuándo sale Assassin's Creed Mirage?"))
        }
        }
}
