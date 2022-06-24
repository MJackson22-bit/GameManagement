package com.example.practice3.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.practice3.R
import com.example.practice3.databinding.ActivityDetailBinding
import com.example.practice3.db.SQLiteHelperGame
import com.example.practice3.model.Game
import com.example.practice3.provider.GameProvider
import com.google.android.material.snackbar.Snackbar


class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private var bundle: Bundle? = null
    private var id: Int? = 0
    private var game: Game? = null
    lateinit var sqLiteHelperGame: SQLiteHelperGame
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelperGame = SQLiteHelperGame(this)
        bundle = intent.extras
        id = bundle?.getInt("GAME_DETAIL")
        getGame()
        showDetail()
    }

    private fun getGame() {
        game = sqLiteHelperGame.getGame(id)
    }

    private fun showDetail() {
        binding.tvName.text = game?.name.toString()
        binding.tvCategory.text = game?.category
        binding.tvCompany.text = game?.company
        binding.tvDescription.text = game?.description
        getImage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        showDetail()
    }

    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuDetailEdit -> {
                val intent = Intent(this, AddGameActivity::class.java)
                Log.i("Detail", game?.id.toString())
                intent.putExtra("ID_DETAIL", game?.id)
                intent.putExtra("IS_EDIT", true)
                intent.putExtra("detail", true)
                startActivity(intent)
                return true
            }
            R.id.menuDetailDelete -> {
                val alertDialog: AlertDialog = this.let {
                    val builder = AlertDialog.Builder(it)
                    builder.setMessage("¿Desea eliminar este elemento?")
                        .setPositiveButton(
                            R.string.eliminar,
                            DialogInterface.OnClickListener { _, _ ->
                                sqLiteHelperGame.deleteGame(game?.id)
                                it.finish()
                            })
                        .setNegativeButton(
                            R.string.cancelar,
                            DialogInterface.OnClickListener { dialog, _ ->
                                dialog.cancel()
                            })
                    builder.create()
                }
                alertDialog.show()
                val window: Window? = alertDialog.window
                val windowManager: WindowManager.LayoutParams? = window?.attributes
                windowManager?.gravity = Gravity.BOTTOM
                window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                window!!.attributes = windowManager
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun getImage() {
        Glide.with(binding.ivImage.context).load(game?.image).error(
            R.drawable.error
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                Snackbar.make(
                    binding.root,
                    "Error al cargar algunas de las imágenes",
                    Snackbar.LENGTH_SHORT
                ).show()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }).into(binding.ivImage)
    }
}