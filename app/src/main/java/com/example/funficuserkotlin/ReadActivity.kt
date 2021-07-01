package com.example.funficuserkotlin

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth

class ReadActivity : AppCompatActivity() {
    lateinit var tvShortName: TextView
    lateinit var tvTypeFun: TextView
    lateinit var tvTextFun: TextView
    lateinit var drawerLayout: DrawerLayout
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
        setContentView(R.layout.show_funfic)
        init()
    }
    private fun init() {
        auth = FirebaseAuth.getInstance()
        itemView2 = findViewById(R.id.menu_item_4)
        itemView5 = findViewById(R.id.menu_item_10)
        itemView7 = findViewById(R.id.menu_item_3)
        itemView10 = findViewById(R.id.menu_item_9)
        itemView11 = findViewById(R.id.menu_item_11)
        itemView12 = findViewById(R.id.menu_item_12)
        tvShortName = findViewById(R.id.tShortName)
        tvTypeFun = findViewById(R.id.tTypeFun)
        tvTextFun = findViewById(R.id.tTextFun)
        drawerLayout = findViewById(R.id.drawer_layout)
        getIntentFun()
        showSigned()
    }

    private fun getIntentFun() {
        val i = intent
        if (i != null) {
            tvShortName.text = i.getStringExtra("fun_fic_short_name")
            tvTypeFun!!.text = i.getStringExtra("fun_fic_type")
            tvTextFun.text = i.getStringExtra("fun_fic_text")
        }
    }

    fun ClickMenu(view: View) {
        mainActivity.openDrawer(drawerLayout)
    }

    fun ClickLogo(view: View) {
        mainActivity.closeDrawer(drawerLayout)
    }

    fun ClickFunFic(view: View) {
        mainActivity.redirectActivity(this, ListFunFicActivity::class.java)
    }

    fun updatePassword(view: View) {
        mainActivity.redirectActivity(this, ResetPasswordActivity::class.java)
    }

    fun ClickAddFunFic(view: View) {
        mainActivity.redirectActivity(this, AddFunFic::class.java)
    }

    fun ClickHome(view: View) {
        mainActivity.redirectActivity(this, MainActivity::class.java)
    }

    fun ClickDashboard(view: View) {
        mainActivity.redirectActivity(this, LoginActivity::class.java)
    }

    fun ClickAboutAs(view: View) {
        mainActivity.redirectActivity(this, RegistrationActivity::class.java)
    }

    fun ClickLogout(view: View) {
        mainActivity.logout(this)
    }

    override fun onPause() {
        super.onPause()
        mainActivity.closeDrawer(drawerLayout)
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