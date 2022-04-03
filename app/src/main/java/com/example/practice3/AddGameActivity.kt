package com.example.practice3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.practice3.databinding.ActivityAddGameBinding
import com.example.practice3.model.Game
import com.example.practice3.provider.GameProvider

class AddGameActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityAddGameBinding
    private var category: String = ""
    private var isEdit: Boolean? = null
    private var bundle: Bundle? = null
    private var game: Game? = null
    var id: Int? = 0
    var position: Int? = 0
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.spinnerCategory.onItemSelectedListener = this
        bundle = intent.extras
        id = bundle?.getInt("ID")
        Log.i("ID_EDIT", id.toString())
        isEdit = bundle?.getBoolean("IS_EDIT")
        if(GameProvider.gameList.isNotEmpty()){
            var i = 0
            GameProvider.gameList.forEach {
                if (it.id == id){
                    game = GameProvider.gameList[i]
                    position = i
                }
                i++
            }
            verifyEdit()
        }
//        val categoryArray = resources.getStringArray(R.array.arrayCategory)
//        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item, categoryArray)
//        arrayAdapter.setDropDownViewResource(R.layout.drop_down_item)
//        binding.spinnerCategory.adapter = arrayAdapter
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
        GameProvider.gameList[position!!] = game!!
//        id?.let { GameProvider.gameList.removeAt(it) }
//        game?.let { id?.let { it1 -> GameProvider.gameList.add(it1, it) } }
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
        Log.i("SaveIndexBound", GameProvider.gameList.last().toString())
        GameProvider.gameList.add(game)
        Log.i("Lista después", GameProvider.gameList.toString())
    }
}