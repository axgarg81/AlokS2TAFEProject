package com.example.aloks2tafeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.aloks2tafeproject.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // create DBHandler object
    private val dbh = DBHandler(this,null)
    // create arrays for contact object and ids
    private var personList:MutableList<Person> = arrayListOf()
    private var idList:MutableList<Int> = arrayListOf()
    // create Menu values
    private val menuAdd = Menu.FIRST+1
    private val menuEdit = Menu.FIRST+2
    private val menuRemove = Menu.FIRST+3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // set context menu for listview
        registerForContextMenu(binding.lstView)
        // load the existing data in Listview function
        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // add option menu
        menu.add(Menu.NONE,menuAdd,Menu.NONE,"ADD")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // this will start new activity
        val goAdd = Intent(this,ADDEDIT::class.java)
        startActivity(goAdd)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // add context menu
        menu.add(Menu.NONE,menuEdit,Menu.NONE,"EDIT")
        menu.add(Menu.NONE,menuRemove,Menu.NONE,"DELETE")
        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // adapter context menu info object
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        // code for edit and delete
        when(item.itemId){
            menuEdit->{
                // create intent and start activity with data pass on
                val addEdit = Intent(this,ADDEDIT::class.java)
                addEdit.putExtra("ID",idList[info.position])
                startActivity(addEdit)
            }
            menuRemove->{
                // call delete function dbHandler
                dbh.deletePerson(idList[info.position])
                // refresh list view after delete
                initAdapter()
            }
        }
        return super.onContextItemSelected(item)
    }
    private fun initAdapter() {
        try{
            // clear all values
            personList.clear()
            idList.clear()
            // get all contacts from DBHandler and go through loop
            for(person:Person in dbh.getAllPersons()){
                // read and add to contactList
                personList.add(person)
                idList.add(person.id)
            }
            // create array adapter
            val adp = CustomAdapter(this,personList as ArrayList<Person>)
            // assign adapter to list view
            binding.lstView.adapter = adp
        }catch (e:Exception){
            // show error message toast
            Toast.makeText(this,"Problem: ${e.message.toString()}",
                Toast.LENGTH_LONG).show()
        }
    }

}