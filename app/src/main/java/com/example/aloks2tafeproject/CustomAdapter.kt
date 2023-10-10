package com.example.aloks2tafeproject

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView



//Class MyAdapter
class CustomAdapter(private val context: Context,
                    private val arrayList: ArrayList<Person>) : BaseAdapter() {
    private lateinit var txtName: TextView
    private lateinit var txtAddress: TextView
    private lateinit var txtMobile: TextView
    private lateinit var txtEmail: TextView
    private lateinit var ivImage: ImageView
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val cView = LayoutInflater.from(context).inflate(R.layout.row_item,
            parent, false)
        txtName = cView.findViewById(R.id.name)
        txtAddress = cView.findViewById(R.id.address)
        txtEmail = cView.findViewById(R.id.email)
        txtMobile = cView.findViewById(R.id.mobile)
        ivImage = cView.findViewById(R.id.imageFile)
        txtName.text = arrayList[position].name
        txtAddress.text = arrayList[position].address
        txtMobile.text = arrayList[position].mobile
        txtEmail.text = arrayList[position].email

        ivImage.setImageResource(context.resources.getIdentifier(
            arrayList[position].imageFile, "drawable",
            "com.example.aloks2tafeproject"))
        return cView
    }
}