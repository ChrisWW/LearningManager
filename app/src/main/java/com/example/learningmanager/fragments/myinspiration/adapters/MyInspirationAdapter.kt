package com.example.learningmanager.fragments.myinspiration.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.learningmanager.databinding.ItemMyInspirationLayoutBinding
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class MyInspirationAdapter(
    private val onItemRootClicked: (Int) -> Unit
) : RecyclerView.Adapter<MyInspirationAdapter.SetInspirationItemViewHolder>() {
    var items = emptyList<MyInspirationData>()
    var firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SetInspirationItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemMyInspirationLayoutBinding.inflate(inflater, parent, false)
            .let(::SetInspirationItemViewHolder)
        setListData(items)
    }


    override fun onBindViewHolder(
        holder: MyInspirationAdapter.SetInspirationItemViewHolder,
        position: Int
    ): Unit =
        with(holder) {
            bind(items[position])
        }

    inner class SetInspirationItemViewHolder(
        private val layout: ItemMyInspirationLayoutBinding
    ) : RecyclerView.ViewHolder(layout.root) {
        fun bind(item: MyInspirationData) = with(layout) {
            Log.d("myiamgeurlvalue", "${item.imageUrl}")
            val ONE_MEGABYTE: Long = 1024 * 1024
//                    imageBytesInString = bytes.toString(Charsets.UTF_8)
            if (item.imageUrl != null && item.imageUrl != "") {
                (firebaseStorage.getReferenceFromUrl(item.imageUrl)).getBytes(ONE_MEGABYTE)
                    .addOnSuccessListener { bytes ->
//                        val byteArray = item.imageUrl.toByteArray(Charsets.UTF_8)
                        val bitMap = byteArrayToBitmap(bytes)
                        Glide.with(root.context)
                            .load(bitMap.scale(160, 160))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(ivImageInspiration)
//                        ivImageInspiration.setImageBitmap(bitMap.scale(160, 160))
                        if (item.authorQuote != "" && item.quote != "") {
                            tvName.text = item.authorQuote
                            tvDescription.text = item.quote
                            Log.d("infozero", "${tvName.text}")
                        } else {
                            tvName.text = item.title
                            tvDescription.text = item.description
                            Log.d("infoone", "${tvName.text}")
                        }
                    }
            }
            root.setOnClickListener {
                onItemRootClicked
            }
        }

        fun bitMapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }
    }

    private fun setListData(list: List<MyInspirationData>) {
        this.items = emptyList()
        this.items = list
        notifyDataSetChanged()
    }

    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    override fun getItemCount(): Int = items.size
}
