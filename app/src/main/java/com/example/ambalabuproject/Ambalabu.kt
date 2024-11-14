package com.example.ambalabuproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ambalabuproject.R

val righteousFontFamily = FontFamily(Font(R.font.righteous))

class Ambalabu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyApplicationScreen()
            }
        }
    }
}

//VIEW MODEL MARKET
class MarketViewModel : ViewModel() {
    private val allItems = listOf(
        Pair(R.drawable.mr_ironi_labubu3, "Mr Ironi Labubu"),
        Pair(R.drawable.ambalabu_hijau2, "Ambalabu Ijo"),
        Pair(R.drawable.laburus2, "Laburuzz"),
        Pair(R.drawable.ambalabu_coklat_ver2, "Coklat ver"),
        Pair(R.drawable.mr_ironi_labubu3, "Mr Ironi Labubu"),
        Pair(R.drawable.ambalabu_hijau2, "Ambalabu Ijo"),
        Pair(R.drawable.laburus2, "Laburuzz")
    )

    var searchQuery by mutableStateOf("")
    var filteredItems by mutableStateOf(allItems)

    fun updateSearchQuery(query: String) {
        searchQuery = query
        filteredItems = if (query.isEmpty()) {
            allItems
        } else {
            allItems.filter { it.second.contains(query, ignoreCase = true) }
        }
    }
}

data class FighterItem(
    val imageRes: Int,
    val title: String,
    val subtitle: String,
    val description: String
)

@Composable
fun MyApplicationScreen() {
    var selectedScreen by remember { mutableStateOf("Beranda") }
    var selectedFighter by remember { mutableStateOf<FighterItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            when (selectedScreen) {
                "Beranda" -> AmbalabuScreen(onFighterSelected = { fighter ->
                    selectedFighter = fighter
                    selectedScreen = "DetailFighter"
                })
                "Market" -> MarketScreen()
                "DetailFighter" -> if (selectedFighter != null) {
                    DetailScreen(
                        fighter = selectedFighter!!,
                        onBack = {
                            selectedScreen = "Beranda"
                            selectedFighter = null
                        }
                    )
                }
                "About" -> MyselfScreen()
            }
        }
        BottomNavigationBar(
            selectedItem = selectedScreen,
            onItemSelected = { screen ->
                selectedScreen = screen
                if (screen != "DetailFighter") selectedFighter = null
            }
        )
    }
}

@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8ACDD7))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SULFIA'S APP",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = righteousFontFamily,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )
    }
}

@Composable
fun AmbalabuScreen(onFighterSelected: (FighterItem) -> Unit) {
    TopHeader()
    Spacer(modifier = Modifier.height(4.dp))
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Daftar Labubu Normal \nSebelum Terinfeksi :",
            fontSize = 18.sp,
            color = Color(0xFFCC3030),
            fontFamily = righteousFontFamily,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC0EEE4))
                .padding(vertical = 16.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) { index ->
                    val imageRes = if (index % 2 == 0) R.drawable.coklat else R.drawable.pink
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .background(Color(0xFFF9F9E0), shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(imageRes),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Choose Your Fighter!",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFCC3030),
            textAlign = TextAlign.Start,
            fontFamily = righteousFontFamily,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        //BAGIAN CHOOSE YOUR FIGHTER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF9F9E0))
                .padding(vertical = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        FighterItem(R.drawable.mr_ironi_labubu3, "Mr Ironi Labubu", "Grey ver", "tipe yang akan menyerang jika menyerang"),
                        FighterItem(R.drawable.ambalabu_coklat_ver2, "Ambalabu", "Coklat ver", "merupakan versi sedikit agresif"),
                        FighterItem(R.drawable.ambalabu_hijau2, "Ambalabu", "Hijau ver", "entitas dengan normal dan tenang"),
                        FighterItem(R.drawable.laburus2, "Laburus", "Ungu ver", "versi yang kalem dari laburus")
                    )
                ) { item ->
                    FighterCard(
                        imageRes = item.imageRes,
                        title = item.title,
                        subtitle = item.subtitle,
                        description = item.description,
                        onClick = { onFighterSelected(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun FighterCard(imageRes: Int, title: String, subtitle: String, description: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFC0EEE4), shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .offset(x = (-10).dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 24.dp),
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = righteousFontFamily
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color(0xFFBE883F),
                fontFamily = righteousFontFamily
            )
            Text(
                text = description,
                fontSize = 12.sp,
                fontFamily = righteousFontFamily
            )
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(Color(0xFFF9F9E0), shape = CircleShape)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF3D3131)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8ACDD7))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomNavItem(
            label = "Beranda",
            iconRes = R.drawable.ic_home3,
            isSelected = selectedItem == "Beranda",
            onClick = { onItemSelected("Beranda") }
        )
        BottomNavItem(
            label = "Market",
            iconRes = R.drawable.ic_pets,
            isSelected = selectedItem == "Market",
            onClick = { onItemSelected("Market") }
        )
        BottomNavItem(
            label = "About",
            iconRes = R.drawable.ic_coffee,
            isSelected = selectedItem == "About",
            onClick = { onItemSelected("About") }
        )
    }
}



@Composable
fun BottomNavItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = label,
            colorFilter = if (isSelected) ColorFilter.tint(Color.White) else ColorFilter.tint(Color(0xFF3D3131)),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color(0xFF3D3131),
            textAlign = TextAlign.Center
        )
    }
}

//TOP HEADER DETAIL FIGHTER
@Composable
fun TopHeaderWithBack(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8ACDD7))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBack) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = righteousFontFamily,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )
    }
}


//HALAAMAN DETAIL FIGHTER
@Composable
fun DetailScreen(fighter: FighterItem, onBack: () -> Unit) {
    TopHeaderWithBack(title = "Detail Fighter", onBack = onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC0EEE4), shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(fighter.imageRes),
                contentDescription = null,
                modifier = Modifier.size(200.dp)  .padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = fighter.description,
            fontSize = 16.sp,
            fontFamily = righteousFontFamily,
            color = Color.Gray
        )
    }
}

//HALAMAN MARKET
@Composable
fun MarketScreen(viewModel: MarketViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        TopHeader(title = "AMBALABU MARKET")

        TextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            placeholder = { Text("Search...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Image(
                    painter = painterResource(id = R.drawable.banner_amba),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF9F9E0))
                        .shadow(4.dp, ambientColor = Color.Black.copy(alpha = 0.1f), spotColor = Color.Black.copy(alpha = 0.1f))
                        .padding(16.dp)
                )
            }

            items(viewModel.filteredItems) { (imageRes, title) ->
                Box(
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp), ambientColor = Color.Black.copy(alpha = 0.1f), spotColor = Color.Black.copy(alpha = 0.1f))
                        .background(Color(0xFFC0EEE4), shape = RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFCAC8), shape = RoundedCornerShape(8.dp))
                            .padding(top = 8.dp, bottom = 8.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        ItemCard(imageRes = imageRes, title = title)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(imageRes: Int, title: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF9F9E0), shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFFBE883F),
                fontFamily = righteousFontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TopHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8ACDD7))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = righteousFontFamily,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )
    }
}

//HALAMAN ABOUT
@Composable
fun MyselfScreen() {
    TopHeader(title = "MYSELF")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.me),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .border(10.dp, Color(0xFFC0EEE4), CircleShape),
            contentScale = ContentScale.Crop

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nama : Sulfia", fontFamily = righteousFontFamily, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFD8A8A))
        Text(text = "Email : sulfiaaaa@gmail.com", fontFamily = righteousFontFamily, fontSize = 16.sp, color = Color(0xFF5BA0AA))
        Text(text = "Universitas : Universitas Mercu Buana", fontFamily = righteousFontFamily, fontSize = 16.sp, color = Color(0xFF5BA0AA))
        Text(text = "Jurusan : Teknik Informatika", fontFamily = righteousFontFamily, fontSize = 16.sp, color = Color(0xFF5BA0AA))
        Text(text = "Kelas : Flagship Siang", fontFamily = righteousFontFamily, fontSize = 16.sp, color = Color(0xFF5BA0AA))
        Text(text = "Mobile Development", fontFamily = righteousFontFamily, fontSize = 16.sp, color = Color(0xFF5BA0AA))

    }
}




@Preview(showBackground = true)
@Composable
fun MyApplicationScreenPreview() {
    MyApplicationScreen()
}
