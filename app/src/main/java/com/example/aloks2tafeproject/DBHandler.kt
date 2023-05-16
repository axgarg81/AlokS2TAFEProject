package com.example.aloks2tafeproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context, factory: SQLiteDatabase.CursorFactory?)
        : SQLiteOpenHelper(context, DATABASE_NAME, factory,DATABASE_VERSION) {

        private val  tableName:String = "Person"
        private val  keyID:String = "ID"
        private val  keyName:String = "NAME"
        private val  keyMobile:String = "MOBILE"
        private val  keyEmail:String = "EMAIL"
        private val  keyAddress:String = "ADDRESS"
        private val  keyImageFile:String = "IMAGEFILE"

        companion object{
            const val DATABASE_NAME = "HRManager.db"
            const val DATABASE_VERSION = 4
        }

        override fun onCreate(db: SQLiteDatabase) {
            // create sql statement for table
            val createTable = "CREATE TABLE $tableName($keyID INTEGER PRIMARY KEY AUTOINCREMENT, $keyName TEXT, $keyImageFile TEXT, $keyAddress TEXT,$keyMobile TEXT, $keyEmail TEXT)"
            // execute sql
            db.execSQL(createTable)
            // add one sample record using ContentValue object
            val cv = ContentValues()
            cv.put(keyName,"Alok Garg")
            cv.put(keyMobile,"123456789")
            cv.put(keyAddress,"Sydney")
            cv.put(keyImageFile,"first")
            cv.put(keyEmail,"alok@gmail.com")
            // use insert method
            db.insert(tableName,null, cv)

        }

        override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
            // drop existing table
            db.execSQL("DROP TABLE IF EXISTS $tableName")
            // recreate database
            onCreate(db)
        }
        // get all the records from database
        fun getAllPersons():ArrayList<Person>{
            // declare a arraylist to fill all records in contact object
            val personList = ArrayList<Person>()
            // create a sql query
            val selectQuery = "SELECT * FROM $tableName"
            // get database for readable
            val db = this.readableDatabase
            // run query and put result in cursor
            val cursor = db.rawQuery(selectQuery,null)
            // move through cursor and read all the records
            if(cursor.moveToFirst()){
                // loop to read all possible records
                do{
                    // create a contact object
                    val person = Person()
                    // read values from cursor and fill contact object
                    person.id =         cursor.getInt(0)
                    person.name =       cursor.getString(1)
                    person.imageFile =  cursor.getString(2)
                    person.address =    cursor.getString(3)
                    person.mobile =     cursor.getString(4)
                    person.email =      cursor.getString(5)

                    // add contact object to array list
                    personList.add(person)
                }while(cursor.moveToNext())
            }

            // close cursor and database
            cursor.close()
            db.close()
            // return arraylist of contact object
            return personList

        }
        // add new record in the DB
        fun addPerson(person: Person) {
            // get writable db
            val db = this.writableDatabase
            // create content value object
            val cv = ContentValues()
            cv.put(keyName,person.name)
            cv.put(keyImageFile,person.imageFile)
            cv.put(keyAddress,person.address)
            cv.put(keyMobile,person.mobile)
            cv.put(keyEmail,person.email)
            // use insert method
            db.insert(tableName,null, cv)
            // close db
            db.close()
        }
        // delete the existing record in DB by ID
        fun deletePerson(id: Int) {
            // get writable db
            val db = this.writableDatabase
            db.delete(tableName,
                "$keyID=?",
                arrayOf(id.toString()))
            // close db
            db.close()
        }
        // retrieve the record from DB passing ID
        fun getPerson(id: Int): Person {
            // get readable db
            val db = this.readableDatabase
            // create contact object to fill data
            val person = Person()
            // create cursor based on query
            val cursor = db.query(tableName,
                arrayOf(keyID,keyName,keyImageFile,keyAddress,keyMobile,keyEmail),
                "$keyID=?",
                arrayOf(id.toString()),
                null,
                null,
                null)
            // check cursor and read value and put in contact
            if(cursor!=null){
                cursor.moveToFirst()
                person.id = cursor.getInt(0)
                person.name= cursor.getString(1)
                person.imageFile= cursor.getString(2)
                person.address= cursor.getString(3)
                person.mobile= cursor.getString(4)
                person.email= cursor.getString(5)
            }
            // close cursor and db
            cursor.close()
            db.close()
            return person
        }

        fun updatePerson(person: Person) {
            // get writable db
            val db = this.writableDatabase
            // create content value and put values from contact object
            val cv = ContentValues()
            cv.put(keyName,person.name)
            cv.put(keyImageFile,person.imageFile)
            cv.put(keyAddress,person.address)
            cv.put(keyMobile,person.mobile)
            cv.put(keyEmail,person.email)
            db.update(tableName,
                cv,
                "$keyID=?",
                arrayOf(person.id.toString()))
            // close db
            db.close()
        }


}