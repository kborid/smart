package com.kborid.setting.ui.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kborid.setting.R
import org.slf4j.LoggerFactory

class MainAdapter(context: Context, data: ArrayList<String>) : BaseAdapter() {

    private val logger = LoggerFactory.getLogger(MainAdapter::class.java)

    var context: Context? = null
    var data: ArrayList<String>? = null

    init {
        this.context = context
        this.data = data
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        logger.info("执行getView()方法，position：{}，view：{}", position, convertView)
        val viewHolder: TestViewHolder
        val view: View
        if (null == convertView) {
            view = View.inflate(context, R.layout.item_home, null)
            viewHolder = TestViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as TestViewHolder
        }

        viewHolder.name.text = data?.get(position) ?: ""
        viewHolder.icon.setImageResource(R.mipmap.logo_small)

        return view
    }

    override fun getItem(position: Int): Any {
        return data?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data?.size ?: 0
    }

    class TestViewHolder(var view: View) {
        var icon: ImageView = view.findViewById(R.id.fruit_image)
        var name: TextView = view.findViewById(R.id.fruit_name)
    }
}