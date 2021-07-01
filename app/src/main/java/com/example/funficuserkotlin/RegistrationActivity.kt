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
import com.example.funficuserkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    lateinit var edLogin: EditText
    lateinit var edPassword: EditText
    lateinit var drawerLayout: DrawerLayout
    lateinit var auth: FirebaseAuth
    val USER_KEY = "User"
    lateinit var authUser: DatabaseReference
    lateinit var itemView2: ImageView
    lateinit var itemView5: ImageView
    lateinit var itemView7: TextView
    lateinit var itemView10: TextView
    lateinit var itemView11: TextView
    lateinit var itemView12: ImageView
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        init()
    }
    fun init() {
        auth = FirebaseAuth.getInstance()
        itemView2 = findViewById(R.id.menu_item_4)
        itemView5 = findViewById(R.id.menu_item_10)
        itemView7 = findViewById(R.id.menu_item_3)
        itemView10 = findViewById(R.id.menu_item_9)
        itemView11 = findViewById(R.id.menu_item_11)
        itemView12 = findViewById(R.id.menu_item_12)
        drawerLayout = findViewById(R.id.drawer_layout)
        edLogin = findViewById(R.id.up_email)
        edPassword = findViewById(R.id.up_password)
        auth = FirebaseAuth.getInstance()
        authUser = FirebaseDatabase.getInstance().getReference(USER_KEY)
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

    fun ClickAddFunFic(view: View) {
        mainActivity.redirectActivity(this, AddFunFic::class.java)
    }

    fun ClickHome(view: View) {
        mainActivity.redirectActivity(this, MainActivity::class.java)
    }

    fun ClickDashboard(view: View) {
        mainActivity.redirectActivity(this, LoginActivity::class.java)
    }

    fun updatePassword(view: View) {
        mainActivity.redirectActivity(this, ResetPasswordActivity::class.java)
    }

    fun ClickAboutAs(view: View) {
        recreate()
    }

    fun ClickLogout(view: View) {
        mainActivity.logout(this)
    }

    override fun onPause() {
        super.onPause()
        mainActivity.closeDrawer(drawerLayout)
    }

    fun onClickSingUp(view: View) {
        if (TextUtils.isEmpty(edLogin.text.toString()) && TextUtils.isEmpty(edPassword.text.toString())) {
            Toast.makeText(applicationContext, "login or password empty", Toast.LENGTH_SHORT).show()
        }
        auth.createUserWithEmailAndPassword(edLogin.text.toString(), edPassword.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVer()
                    val id = authUser.child("User").push().key
                    val email = edLogin.text.toString()
                    val password = edPassword.text.toString()
                    val user = User(id.toString(), email, password)
                    authUser.push().setValue(user)
                    showSigned()
                    Toast.makeText(
                        applicationContext,
                        "user Sing up successfull",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(applicationContext, "user Sing up falled", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun sendEmailVer() {
        val user = auth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Chek your email", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "send email falled", Toast.LENGTH_SHORT).show()
            }
        }
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