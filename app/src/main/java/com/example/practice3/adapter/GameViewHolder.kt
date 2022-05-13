package com.example.practice3.adapter

import android.graphics.drawable.Drawable
import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.practice3.model.Game
import com.example.practice3.R
import com.example.practice3.databinding.ItemGameBinding
import com.google.android.material.snackbar.Snackbar

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    private val binding = ItemGameBinding.bind(view)
    lateinit var itemEdit: Game
    fun render(
        item: Game,
        onClickListener: (Game) -> Unit
    ) {
        binding.tvName.text = item.name
        binding.tvCompany.text = item.company
        binding.tvCategory.text = item.category
        this.itemEdit = item
        Glide.with(binding.ivImage.context).load(item.image).error(
            R.drawable.error
        ).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                Snackbar.make(itemView, "Error al cargar algunas de las imágenes", Snackbar.LENGTH_SHORT).show()
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
        itemView.setOnClickListener {
            onClickListener(item)
        }
        binding.root.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.setHeaderTitle("Seleccione una opción")
        menu?.add(adapterPosition, 101, 0, "Editar")
        menu?.add(adapterPosition, 102, 1, "Eliminar")
    }
}