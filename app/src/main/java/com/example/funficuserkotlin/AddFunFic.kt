package com.example.funficuserkotlin

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.funficuserkotlin.model.FunFIc
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddFunFic : AppCompatActivity() {
    lateinit var edShortName: EditText
    lateinit var edTypeFunFic: EditText
    lateinit var edTextFunFic: EditText
    lateinit var drawerLayout: DrawerLayout
    val FUN_FIC_KEY = "FunFic"
    lateinit var db: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var itemView2: ImageView
    lateinit var itemView5: ImageView
    lateinit var itemView7: TextView
    lateinit var itemView10: TextView
    lateinit var itemView11: TextView
    lateinit var itemView12: ImageView
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fun_fic)
        init()
    }

    fun init() {
        itemView2 = findViewById(R.id.menu_item_4)
        itemView5 = findViewById(R.id.menu_item_10)
        itemView7 = findViewById(R.id.menu_item_3)
        itemView10 = findViewById(R.id.menu_item_9)
        itemView11 = findViewById(R.id.menu_item_11)
        itemView12 = findViewById(R.id.menu_item_12)
        auth = FirebaseAuth.getInstance()
        drawerLayout = findViewById(R.id.drawer_layout)
        edShortName = findViewById(R.id.edShorName)
        edTypeFunFic = findViewById(R.id.edTypeFunFic)
        edTextFunFic = findViewById(R.id.edTextFunFic)
        db = FirebaseDatabase.getInstance().getReference(FUN_FIC_KEY)
    }

    fun ClickMenu(view: View) {
        mainActivity.openDrawer(drawerLayout)
    }

    fun ClickFunFic(view: View) {
        mainActivity.redirectActivity(this, ListFunFicActivity::class.java)
    }

    fun ClickLogo(view: View) {
        mainActivity.closeDrawer(drawerLayout)
    }

    fun ClickHome(view: View) {
        mainActivity.redirectActivity(this, MainActivity::class.java)
    }

    fun ClickAddFunFic(view: View) {
        recreate()
    }

    fun ClickDashboard(view: View) {
        mainActivity.redirectActivity(this, LoginActivity::class.java)
    }

    fun ClickAboutAs(view: View) {
        mainActivity.redirectActivity(this, RegistrationActivity::class.java)
    }

    fun updatePassword(view: View) {
        mainActivity.redirectActivity(this, ResetPasswordActivity::class.java)
    }

    fun ClickLogout(view: View) {
        mainActivity.logout(this)
    }

    override fun onPause() {
        super.onPause()
        mainActivity.closeDrawer(drawerLayout)
    }

    fun onClickAddFunFic(view: View) {
        if (TextUtils.isEmpty(edShortName.text.toString()) && TextUtils.isEmpty(edTypeFunFic.text.toString()) && TextUtils.isEmpty(
                edTextFunFic.text.toString()
            )
        ) {
            Toast.makeText(applicationContext, "value empty", Toast.LENGTH_SHORT).show()
        }
        val shortName = edShortName.text.toString()
        val typeFunFic = edTypeFunFic.text.toString()
        val textFunFic = edTextFunFic.text.toString()
        val id = db.child("FunFic").push().key
        val funFic = FunFIc(id.toString(), shortName, typeFunFic, textFunFic)
        db.push().setValue(funFic)
        Toast.makeText(applicationContext, "fun fic add", Toast.LENGTH_SHORT).show()
        showSigned()
    }

    private fun showSigned() {
        val user = auth.currentUser!!
        if (user.isEmailVerified) {
            itemView2.visibility = View.VISIBLE
            itemView5.visibility = View.VISIBLE
            itemView7.visibility = View.VISIBLE
            itemView10.visibility = View.VISIBLE
            itemView11.visibility = View.VISIBLE
            itemView12.visibility = View.VISIBLE
        } else {
            Toast.makeText(applicationContext, "Chek your email", Toast.LENGTH_SHORT).show()
        }
    }
}