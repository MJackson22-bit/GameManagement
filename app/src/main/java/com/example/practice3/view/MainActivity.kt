package com.example.practice3.view

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice3.R
import com.example.practice3.adapter.GameAdapter
import com.example.practice3.databinding.ActivityMainBinding
import com.example.practice3.db.helper.SQLiteHelperGame
import com.example.practice3.db.helper.TableGameHelper
import com.example.practice3.model.Game
import com.example.practice3.provider.ProviderIdContextMenu

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sqLiteHelperGame: SQLiteHelperGame
    lateinit var db: SQLiteDatabase
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelperGame = SQLiteHelperGame(this)
        db = sqLiteHelperGame.writableDatabase
        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY + 12 && binding.fabAddGame.isExtended) {
                binding.fabAddGame.shrink();
            }
            if (scrollY < oldScrollY - 12 && !binding.fabAddGame.isExtended) {
                binding.fabAddGame.extend();
            }
            if (scrollY == 0) {
                binding.fabAddGame.extend();
            }
        }
        binding.fabAddGame.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            intent.putExtra("IS_EDIT", false)
            startActivity(intent)
        }
        iniRecyclerView()
    }

    private fun iniRecyclerView() {
        registerForContextMenu(binding.recyclerGame)
        val recyclerView = binding.recyclerGame
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            GameAdapter(TableGameHelper.getAllGame(db)) { game ->
                onItemSelected(game)
            }
    }

    override fun onResume() {
        super.onResume()
        db = sqLiteHelperGame.writableDatabase
        TableGameHelper.getAllGame(db)
        iniRecyclerView()
    }

    private fun onItemSelected(game: Game) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("GAME_DETAIL", game.id)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            101 -> {
                val intent = Intent(this, AddGameActivity::class.java)
                intent.putExtra("ID", ProviderIdContextMenu.id)
                intent.putExtra("IS_EDIT", true)
                startActivity(intent)
                true
            }
            102 -> {
                TableGameHelper.deleteGame(ProviderIdContextMenu.id, db)
                this.onResume()
                true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate: MenuInflater = menuInflater
        inflate.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.newGame -> {
                val intent = Intent(this, AddGameActivity::class.java)
                intent.putExtra("IS_EDIT", false)
                startActivity(intent)
                true
            }
            R.id.exit -> {
                this.finish()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}