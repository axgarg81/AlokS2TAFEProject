package com.example.aloks2tafeproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.aloks2tafeproject.databinding.AddeditBinding
//import kotlinx.android.synthetic.main.addedit.*

class ADDEDIT:Activity(), View.OnClickListener {
    private lateinit var binding:AddeditBinding
    // create DBHandler object
    private val dbh = DBHandler(this,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= AddeditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // set on click listener
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        val extras = intent.extras
        if(extras!=null){
            // read and assign id from intent
            val id:Int = extras.getInt("ID")
            // get contact based on id
            val person = dbh.getPerson(id)
            // edit text populate
            binding.etId.setText(person.id.toString())
            binding.etName.setText(person.name)
            binding.etMobile.setText(person.mobile)
            binding.etAddress.setText(person.address)
            binding.etEmail.setText(person.email)
            binding.etImageFile.setText(person.imageFile)
            binding.ivImage.setImageResource(this.resources.getIdentifier(
                person.imageFile, "drawable",
                "com.example.aloks2tafeproject"))
        }
    }

    override fun onClick(btn: View) {
        // code to run for buttons
        when(btn.id){
            R.id.btnSave->{
                // save code
                // read value from ID and put 0 if no value
                val cid:Int =try{
                    binding.etId.text.toString().toInt()
                }catch (e:Exception){
                    0
                }
                // decide add or update
                if(cid==0){
                    addContact()
                }else{
                    editContact(cid)
                }
            }
            R.id.btnCancel->{
                // cancel code
                goBack()
            }
        }
    }

    private fun editContact(cid:Int) {
        // create contact object and fill new values
        val person = dbh.getPerson(cid)
        person.name = binding.etName.text.toString()
        person.mobile = binding.etMobile.text.toString()
        person.address = binding.etAddress.text.toString()
        person.email = binding.etEmail.text.toString()
        person.imageFile = binding.etImageFile.text.toString()
        // call dbHandler update function
        dbh.updatePerson(person)
        // display confirmation and go to Main Activity
        Toast.makeText(this,"Person has been updated",
            Toast.LENGTH_LONG).show()
        goBack()
    }

    private fun addContact() {
        // read values from edit text and assign to contact object
        val person = Person()
        person.name = binding.etName.text.toString()
        person.mobile = binding.etMobile.text.toString()
        person.address = binding.etAddress.text.toString()
        person.email = binding.etEmail.text.toString()
        person.imageFile = binding.etImageFile.text.toString()

        // call dbHandler function to add
        dbh.addPerson(person)
        // display confirmation
        Toast.makeText(this,"New Person created",
            Toast.LENGTH_LONG).show()
        goBack()

    }

    private fun goBack() {
        // go back to Main Activity
        val mainAct = Intent(this,MainActivity::class.java)
        // to start another activity
        startActivity(mainAct)
    }
}

