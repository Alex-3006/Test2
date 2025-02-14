package com.example.test2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException

import com.example.test2.ui.CameraFragment

class MainActivity : AppCompatActivity() {
    private val fileName = "user_data.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val readButton = findViewById<Button>(R.id.readButton)
        val textView = findViewById<TextView>(R.id.textView)
        val inputName = findViewById<EditText>(R.id.inputName)
        val inputAge = findViewById<EditText>(R.id.inputAge)
        val inputEmail = findViewById<EditText>(R.id.inputEmail)

        // Save JSON data when the button is clicked
        saveButton.setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("name", inputName.text)
            jsonObject.put("age", inputAge.text)
            jsonObject.put("email", inputEmail.text)

            if (writeJsonToFile(jsonObject)) {
                textView.text = "JSON saved successfully!"
            } else {
                textView.text = "Failed to save JSON."
            }
        }

        // Read JSON data when the button is clicked
        readButton.setOnClickListener {
            val jsonData = readJsonFromFile()
            textView.text = jsonData ?: "No JSON data found."
        }

        val cameraButton = findViewById<Button>(R.id.cameraButton)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        cameraButton.setOnClickListener {
            fragmentTransaction.replace(R.id.fragment_container, CameraFragment())
            fragmentTransaction.commit()
        }
    }

    private fun writeJsonToFile(jsonObject: JSONObject): Boolean {
        return try {
            val file = File(filesDir, "user_data.json")

            val writer = FileWriter(file, false) // 'false' ensures overwriting, not appending
            writer.write(jsonObject.toString(4)) // Pretty-print JSON
            writer.flush()
            writer.close()

            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    // Function to read JSON data from the file
    private fun readJsonFromFile(): String? {
        val file = File(filesDir, fileName)
        return if (file.exists()) {
            file.readText()
        } else {
            null
        }
    }
}