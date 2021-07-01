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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var edOldPassword: EditText
    lateinit var edNewPassword: EditText
    lateinit var firebaseUser: FirebaseUser
    lateinit var auth: FirebaseAuth
    lateinit var itemView2: ImageView
    lateinit var itemView5: ImageView
    lateinit var itemView7: TextView
    lateinit var itemView10: TextView
    lateinit var itemView11: TextView
    lateinit var itemView12: ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
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
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        edOldPassword = findViewById(R.id.edOldPassword)
        edNewPassword = findViewById(R.id.edNewPassword)
        drawerLayout = findViewById(R.id.drawer_layout)
        showSigned()
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
        mainActivity.redirectActivity(this, AddFunFic::class.java)
    }

    fun ClickDashboard(view: View) {
        mainActivity.redirectActivity(this, LoginActivity::class.java)
    }

    fun updatePassword(view: View) {
        recreate()
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

    fun onClickUpdatedPassword(view: View) {
        val email = firebaseUser.email!!
        val credential = EmailAuthProvider.getCredential(email, edOldPassword.toString())
        firebaseUser.reauthenticate(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUser.updatePassword(edNewPassword.toString())
            }
        }
        if (TextUtils.isEmpty(edOldPassword.text.toString()) && TextUtils.isEmpty(edNewPassword.text.toString())) {
            Toast.makeText(applicationContext, "passwords empty", Toast.LENGTH_SHORT).show()
        } else {
            firebaseUser.updatePassword(edNewPassword.toString())
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