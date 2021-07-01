package com.example.funficuserkotlin

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init() {
        drawerLayout = findViewById(R.id.drawer_layout)
    }

    fun ClickMenu(view: View) {
        openDrawer(drawerLayout)
    }

    fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun ClickLogo(view: View) {
        closeDrawer(drawerLayout)
    }

    fun closeDrawer(drawerLayout: DrawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun ClickHome(view: View) {
        recreate()
    }

    fun ClickDashboard(view: View) {
        redirectActivity(this, LoginActivity::class.java)
    }

    fun updatePassword(view: View) {
        redirectActivity(this, ResetPasswordActivity::class.java)
    }

    fun ClickFunFic(view: View) {
        redirectActivity(this, ListFunFicActivity::class.java)
    }

    fun ClickAboutAs(view: View) {
        redirectActivity(this, RegistrationActivity::class.java)
    }

    fun ClickAddFunFic(view: View) {
        redirectActivity(this, AddFunFic::class.java)
    }

    fun ClickLogout(view: View) {
        logout(this)
    }

    fun logout(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout ?")
        builder.setPositiveButton("YES") { dialog, which ->
            activity.finishAffinity()
            System.exit(0)
        }
        builder.setNegativeButton(
            "NO"
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    fun redirectActivity(activity: Activity, aClass: Class<*>?) {
        val i = Intent(activity, aClass)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(i)
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}