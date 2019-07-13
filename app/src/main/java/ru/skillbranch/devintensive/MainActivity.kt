package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var benderImage: ImageView

    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val statusName = savedInstanceState?.getString(SIS_STATUS) ?: Bender.Status.NORMAL.name
        val questionName = savedInstanceState?.getString(SIS_QUESTION) ?: Bender.Question.NAME.name

        Log.d("M_MainActivity", "onCreate $statusName $questionName")
        benderObj = Bender(Bender.Status.valueOf(statusName), Bender.Question.valueOf(questionName))

        refreshUi()

        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                send()
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        Log.d("M_MainActivity", "onSaveInstanceState ${benderObj.status.name} ${benderObj.question.name}")

        outState?.putString(SIS_STATUS, benderObj.status.name)
        outState?.putString(SIS_QUESTION, benderObj.question.name)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_send -> {
                send()
            }
        }
    }

    private fun send() {
        Log.d("M_MainActivity", "isKeyboardOpen(): ${isKeyboardOpen()}")
        this.hideKeyboard()
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        refreshUi(phrase, color)
    }

    private fun refreshUi(
        phrase: String = benderObj.askQuestion(),
        color: Triple<Int, Int, Int> = benderObj.status.color
    ) {
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }

    companion object {
        private const val SIS_STATUS: String = "STATUS"
        private const val SIS_QUESTION: String = "QUESTION"
    }

}
