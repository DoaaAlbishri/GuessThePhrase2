package com.example.guessthephrase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv1 = findViewById<TextView>(R.id.tv)
        val tv2 = findViewById<TextView>(R.id.tv1)
        val tv3 = findViewById<TextView>(R.id.tv2)
        val tv4 = findViewById<TextView>(R.id.tv3)
        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)
        var myLayout = findViewById<ConstraintLayout>(R.id.clMain)
        val tv5 = findViewById<TextView>(R.id.tv4)


        var pharse = "Hello World"
        var str = "*".repeat(pharse.length)
        //var str=""
        var Newstr = str
        var left = 10
        var letter = 10

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedIdValue = sharedPreferences.getInt("id_key",0)
        if(sharedIdValue.equals(0)){
            tv5.setText("default Score: ${sharedIdValue.toString()}")
        }else{
                tv5.setText("High Score : ${sharedIdValue.toString()}")
        }
/*
        for (i in 0..pharse.length - 1) {
            if (pharse[i].equals("\\s")){
                continue
            }else{
               str= pharse.replace(pharse[i],'*')
            }
        }
*/


        tv1.setText("Pharse: ${str} \n Gussed Letter: ")


        button.setOnClickListener {
                if (left > 0) {
                    var input = editText.text.toString()
                    if (!editText.text.isEmpty()) {
                        if (pharse.toLowerCase() == input.toLowerCase()) {
                            --left
                            tv1.setText("Pharse: ${input} \n Gussed Letter: ")
                            tv4.setText("${left} guesses remaining")
                            score = 20 - left
                            val id:Int = Integer.parseInt(score.toString())
                            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                            if(score > sharedIdValue ) {
                                editor.putInt("id_key", id)
                                editor.apply()
                                editor.commit()
                            }
                            val sharedIdValue = sharedPreferences.getInt("id_key",0)
                            if(sharedIdValue.equals(0)){
                                tv5.setText("default Score: ${sharedIdValue.toString()}")
                            }else{
                                    tv5.setText("High Score : ${sharedIdValue.toString()}")
                            }
                                this.recreate()
                        } else {
                            --left
                            tv2.setText("Wrong guess : ${input}")
                            tv4.setText("${left} guesses remaining")
                        }
                    }
                        if(left==0){
                            editText.hint = "Gusses the letter"
                        }
                }
                else if (letter>0 && left<=0){
                    editText.hint = "Gusses the letter"
                    var input:Char = editText.text.toString()[0]
                    var count = 0
                    if (!editText.text.isEmpty()&&editText.text.length==1) {
                        letter--
                        for (i in 0..pharse.length - 1) {
                            if (pharse[i].toLowerCase().equals(input.toLowerCase())) {
                                var chars = Newstr.toCharArray()
                                chars[i] = input
                                Newstr = String(chars)
                                // Newstr = Newstr.replace(Newstr[i], input)
                                count++
                            }
                            if (pharse[i] == ' ') {
                                var chars = Newstr.toCharArray()
                                chars[i] = ' '
                                Newstr = String(chars)
                            }
                        }
                        if (count > 0) {
                            tv1.setText("Pharse: ${Newstr} \n Gussed Letter: ${input} ")
                            tv2.setText("Wrong guess : ")
                            tv3.setText("Founds: ${count} ${input.toUpperCase()}(s)")
                            tv4.setText("${letter} guesses remaining")
                        } else {
                            tv2.setText("Wrong guess : ${input}")
                            tv4.setText("${letter} guesses remaining")
                        }
                    }else{
                        Snackbar.make(myLayout,"Enter one letter please",Snackbar.LENGTH_LONG).show()
                    }
                if(Newstr.equals(pharse,true)||letter==0){
                    score=(20-10)-letter
                    val id:Int = Integer.parseInt(score.toString())
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    if(score > sharedIdValue ) {
                        editor.putInt("id_key", id)
                        editor.apply()
                        editor.commit()
                    }
                    val sharedIdValue = sharedPreferences.getInt("id_key",0)
                    if(sharedIdValue.equals(0)){
                        tv5.setText("default Score: ${sharedIdValue.toString()}")
                    }else{
                            tv5.setText("High Score : ${sharedIdValue.toString()}")
                    }
                    this.recreate()
                }
                }
            editText.text.clear()

        }

    }
}