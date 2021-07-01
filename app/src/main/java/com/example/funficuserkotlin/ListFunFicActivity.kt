package com.example.funficuserkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.funficuserkotlin.model.FunFIc
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListFunFicActivity : AppCompatActivity(){
    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listData: List<String>
    lateinit var listFunFic: List<FunFIc>
    lateinit var databaseReference: DatabaseReference
    val USER_KEY = "User"
    val FUN_FUC_KEY = "FunFic"
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
        setContentView(R.layout.activity_list_fun_fic_ativity)
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
        listFunFic = ArrayList<FunFIc>()
        drawerLayout = findViewById(R.id.drawer_layout)
        listView = findViewById(R.id.listView)
        listData = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listData)
        listView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference(FUN_FUC_KEY)
        getDataFromDB()
        setOnClickItem()
        showSigned()
    }

    fun ClickMenu(view: View) {
        mainActivity.openDrawer(drawerLayout)
    }

    fun ClickFunFic(view: View) {
        recreate()
    }

    fun ClickLogo(view: View) {
        mainActivity.closeDrawer(drawerLayout)
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
        mainActivity.redirectActivity(this, RegistrationActivity::class.java)
    }

    fun ClickAddFunFic(view: View) {
        mainActivity.redirectActivity(this, AddFunFic::class.java)
    }

    fun ClickLogout(view: View) {
        mainActivity.logout(this)
    }

    override fun onPause() {
        super.onPause()
        mainActivity.closeDrawer(drawerLayout)
    }

    private fun getDataFromDB() {
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (listData.size > 0) listData.toMutableList().clear()
                if (listFunFic.size > 0) listFunFic.toMutableList().clear()
                for (ds in snapshot.children) {
                    val funfic: FunFIc = ds.getValue(FunFIc::class.java)!!
                    listData.toMutableList().add(funfic.shortName)
                    listFunFic.toMutableList().add(funfic)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        databaseReference.addValueEventListener(valueEventListener)
    }


    private fun setOnClickItem() {
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val funFic: FunFIc = listFunFic[position]
                val i = Intent(this, ReadActivity::class.java)
                i.putExtra("fun_fic_short_name", funFic.shortName)
                i.putExtra("fun_fic_type", funFic.typeFunFic)
                i.putExtra("fun_fic_text", funFic.textFunFic)
                startActivity(i)
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