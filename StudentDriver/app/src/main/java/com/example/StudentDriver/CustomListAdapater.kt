package com.example.StudentDriver

import android.widget.BaseAdapter

//import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.util.ArrayList

class CustomListAdapater(private val names: ArrayList<String>, private val phoneNumbers: ArrayList<Int>, private val activity: AppCompatActivity) : BaseAdapter() {

    private val x = 0

    override fun getCount(): Int {
        return names.size
    }

    override fun getItem(i: Int): Any {
        return names[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        view = LayoutInflater.from(activity.applicationContext).inflate(R.layout.people_list_row, viewGroup, false)
        (view.findViewById<View>(R.id.personPhone) as TextView).text = phoneNumbers[i].toString()
        (view.findViewById<View>(R.id.personNameTv) as TextView).text = names[i]
        return view
    }
}
