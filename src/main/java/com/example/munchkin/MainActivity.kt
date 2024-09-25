package com.example.munchkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaPrincipal()
        }
    }
}

data class Jogador(
    var nome: String,
    var level: Int,
    var equipamento: Int,
    var modificador: Int,
) {
    companion object {
        private val listaJogadores = mutableListOf(
            Jogador("Player 1", 1, 0, 0),
            Jogador("Player 2", 1, 0, 0),
            Jogador("Player 3", 1, 0, 0),
            Jogador("Player 4", 1, 0, 0),
            Jogador("Player 5", 1, 0, 0),
            Jogador("Player 6", 1, 0, 0)
        )

        fun buscarJogador(nome: String): Jogador? {
            return listaJogadores.find { it.nome.equals(nome, ignoreCase = true) }
        }

        fun editarJogador(nome: String, level: Int, equipamento: Int, modificador: Int) {
            val jogador = buscarJogador(nome)
            if (jogador != null) {
                jogador.level = level
                jogador.equipamento = equipamento
                jogador.modificador = modificador
            }
        }
    }
}

@Composable
fun TelaPrincipal() {
    var jogadorNome by remember { mutableStateOf("") }
    var jogadorEncontrado by remember { mutableStateOf<Jogador?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = jogadorNome,
            onValueChange = {
                jogadorNome = it
                jogadorEncontrado = Jogador.buscarJogador(jogadorNome)
            },
            label = { Text(text = "Jogador:") },
            modifier = Modifier.fillMaxWidth()
        )

        jogadorEncontrado?.let { jogador ->
            var level by remember { mutableStateOf(jogador.level) }
            var equipamento by remember { mutableStateOf(jogador.equipamento) }
            var modificador by remember { mutableStateOf(jogador.modificador) }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { if (level > 1) level-- }) {
                    Text(text = "-")
                }
                Text(text = "Level: $level", modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = { if (level < 10) level++ }) {
                    Text(text = "+")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { if (equipamento > 0) equipamento-- }) {
                    Text(text = "-")
                }
                Text(text = "Equipamento: $equipamento", modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = { if (equipamento < 99) equipamento++ }) {
                    Text(text = "+")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { if (modificador > -10) modificador-- }) {
                    Text(text = "-")
                }
                Text(text = "Modificadores: $modificador", modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = { if (modificador < 10) modificador++ }) {
                    Text(text = "+")
                }
            }

            Button(onClick = {
                Jogador.editarJogador(jogador.nome, level, equipamento, modificador)
                jogadorEncontrado = Jogador.buscarJogador(jogador.nome)
            }) {
                Text(text = "Salvar Alterações")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Poder: ${modificador + level + equipamento}")
        } ?: Text(text = "Jogador não encontrado", modifier = Modifier.padding(16.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    TelaPrincipal()
}
