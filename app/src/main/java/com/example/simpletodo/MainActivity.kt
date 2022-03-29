package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()

    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item
                listOfTasks.removeAt(position)

                //tell adapter
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }


        loadItems()

        //look up recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

            //set up so user can enter task

        findViewById<Button>(R.id.button).setOnClickListener{

            val userInputText = findViewById<EditText>(R.id.addTaskField)

            val userInput = userInputText.text.toString()

            listOfTasks.add(userInput)

            //tell adapter
            adapter.notifyItemInserted(listOfTasks.size -1)

            //reset edit text
            userInputText.setText("")

            saveItems()

        }


    }
    //save the data user has inputted
    // Create a method to get the file we need
    fun getDataFile() : File {
        return File(filesDir, "saveFile.txt")
    }

    // Load the items by reading each line in file
    fun loadItems() {
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Write items into file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }
}

