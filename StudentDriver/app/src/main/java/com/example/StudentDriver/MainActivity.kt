package com.example.StudentDriver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference

import android.os.Bundle


import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import com.example.StudentDriver.Activity.MainActivity

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var myDatabaseReference: DatabaseReference? = null
    private var personId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this@MainActivity,MainActivity::class.java))

        // for data persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Person")
        personId = myDatabaseReference!!.push().key


        /*((ListView)findViewById(R.id.peopleList)).
                setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.people_list_row, R.id.personNameTv, readData()));*/

        findViewById<View>(R.id.addBtn).setOnClickListener {
            addPerson((findViewById<View>(R.id.fullNameEditText) as EditText).text.toString(),
                    Integer.parseInt((findViewById<View>(R.id.phoneNumberEditText) as EditText).text.toString()))
        }
        findViewById<View>(R.id.updateBtn).setOnClickListener { updatePerson((findViewById<View>(R.id.fullNameEditText) as EditText).text.toString(), Integer.parseInt((findViewById<View>(R.id.phoneNumberEditText) as EditText).text.toString())) }
        findViewById<View>(R.id.deleteBtn).setOnClickListener { removePerson("first added") }
        findViewById<View>(R.id.loadBtn).setOnClickListener { readData() }

        findViewById<View>(R.id.findBtn).setOnClickListener { findPerson((findViewById<View>(R.id.fullNameEditText) as EditText).text.toString()) }


    }

    private fun addPerson(name: String, phoneNumber: Int) {
//        val person = Person(name, phoneNumber)
//        myDatabaseReference!!.child(personId!!).setValue(person)
        startActivity(Intent(this@MainActivity,Main2Activity::class.java))

    }

    private fun updatePerson(name: String, phoneNumber: Int) {
        myDatabaseReference!!.child(personId!!).child("fullName").setValue(name)
        myDatabaseReference!!.child(personId!!).child("phoneNumber").setValue(phoneNumber)
    }

    private fun removePerson(name: String) {
        /*Query deleteQuery = myDatabaseReference.orderByChild("fullName").equalTo(name);
        deleteQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/
        myDatabaseReference!!.child(personId!!).removeValue()
    }

    private fun readData() {
        val names = ArrayList<String>()
        val phoneNumbers = ArrayList<Int>()
        myDatabaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val snapshotIterator = dataSnapshot.children
                val iterator = snapshotIterator.iterator()
                while (iterator.hasNext()) {
                    val value = iterator.next().getValue<Person>(Person::class.java!!)
                    //names.add(value!!.fullName)
                    phoneNumbers.add(value!!.phoneNumber)
                    ((findViewById<View>(R.id.peopleList) as ListView).adapter as CustomListAdapater).notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        (findViewById<View>(R.id.peopleList) as ListView).adapter = CustomListAdapater(names, phoneNumbers, this)
        //setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.people_list_row, R.id.personNameTv,names));
    }

    private fun findPerson(name: String) {
        val deleteQuery = myDatabaseReference!!.orderByChild("fullName").equalTo(name)
        deleteQuery.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val snapshotIterator = dataSnapshot.children
                val iterator = snapshotIterator.iterator()
                while (iterator.hasNext()) {
                    Log.d("Item found: ", iterator.next().value!!.toString() + "---")
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Item not found: ", "this item is not in the list")
            }
        })
    }

}
