package com.example.practice3.view

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.practice3.R
import com.example.practice3.databinding.ActivityAddGameBinding
import com.example.practice3.db.helper.SQLiteHelperGame
import com.example.practice3.db.helper.TableGameHelper
import com.example.practice3.model.Game
import com.example.practice3.provider.GameProvider

class AddGameActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityAddGameBinding
    private var category: String = ""
    private var isEdit: Boolean? = null
    private var bundle: Bundle? = null
    private var game: Game? = null
    private lateinit var sqLiteHelperGame: SQLiteHelperGame
    lateinit var db: SQLiteDatabase
    var id: Int? = 0
    var detail: Boolean? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelperGame = SQLiteHelperGame(this)
        db = sqLiteHelperGame.writableDatabase
        binding.spinnerCategory.onItemSelectedListener = this
        bundle = intent.extras
        id = bundle?.getInt("ID")
        detail = bundle?.getBoolean("detail")
        Log.i("ID_EDIT", id.toString())
        isEdit = bundle?.getBoolean("IS_EDIT")
        verifyEdit()
    }
    
    @RequiresApi(Build.VERSION_CODES.N)
    private fun verifyEdit() {
        if (bundle != null) {
            if (isEdit == true) {
                chargeItemEdit()
                binding.btnSaveGame.setOnClickListener {
                    updateGame()
                    onBackPressed()
                }
            } else {
                binding.btnSaveGame.setOnClickListener {
                    saveGame()
                    Toast.makeText(this, "Se ha guardado exitosamente", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateGame() {
        game?.name = binding.etName.text.toString()
        game?.company = binding.etCompany.text.toString()
        game?.description = binding.etDescription.text.toString()
        game?.image = binding.etImage.text.toString()
        game?.category = category
        TableGameHelper.updateGame(game, id, db)
    }

    private fun getCategory(): Int {
        var i = 0
        val array = resources.getStringArray(R.array.arrayCategory).iterator()
        array.forEach {
            if (it.equals(game?.category)) {
                return i
            }
            i++
        }
        return 0
    }

    private fun chargeItemEdit() {
        game = TableGameHelper.getGame(id, db)
        Log.i("Contain", game.toString())
        binding.etName.setText(game?.name)
        binding.etCompany.setText(game?.company)
        binding.etImage.setText(game?.image)
        binding.etDescription.setText(game?.description)
        binding.spinnerCategory.setSelection(getCategory())
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun saveGame() {
        val game: Game = Game(
            GameProvider.gameList.last().id + 1,
            binding.etName.text.toString(),
            binding.etCompany.text.toString(),
            category,
            binding.etImage.text.toString(),
            binding.etDescription.text.toString()
        )
        TableGameHelper.insertGame(game, db)
    }
}