package hu.bme.aut.recipebook

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.recipebook.adapter.AllRecipeAdapter
import hu.bme.aut.recipebook.adapter.RecipesAdapter
import hu.bme.aut.recipebook.data.Recipe
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.android.synthetic.main.app_bar_recipes.*
import kotlinx.android.synthetic.main.content_recipes.*

class RecipesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var listsAdapter: RecipesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        fab.setImageResource(R.drawable.ic_filter_vintage_black_24dp)
        fab.setOnClickListener {
            val createListIntent = Intent(this, AddNewRecipeActivity::class.java)
            startActivity(createListIntent)
        }

        nav_view.setNavigationItemSelectedListener(this)

        listsAdapter = RecipesAdapter(applicationContext)
        rvPosts.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        rvPosts.adapter = listsAdapter

        initPostsListener()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            val builder = AlertDialog.Builder(this@RecipesActivity)
            builder.setTitle(getString(R.string.sign_out))
            builder.setMessage(getString(R.string.sign_out_message))
            builder.setPositiveButton(getString(R.string.ok)){ dialog, which ->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                dialog.dismiss()
                finish()
            }

            builder.setNegativeButton(getString(R.string.no)){ dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.nav_search -> {
                startActivity(Intent(this, FindRecipeActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initPostsListener() {
        FirebaseDatabase.getInstance()
            .getReference("items")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                    val newList = dataSnapshot.getValue<Recipe>(Recipe::class.java)
                    listsAdapter.addPost(newList)

                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }
}
