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
import hu.bme.aut.recipebook.adapter.RecipesByOriginAdapter
import hu.bme.aut.recipebook.data.Recipe
import kotlinx.android.synthetic.main.activity_all_recipe.*
import kotlinx.android.synthetic.main.activity_found_recipe.*
import kotlinx.android.synthetic.main.app_bar_recipes.*
import kotlinx.android.synthetic.main.content_recipes.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FoundRecipeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var listsAdapter: RecipesByOriginAdapter
    private var origin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_recipe)

        origin = (this.intent.extras.get("origin") as? String).toString()


        nav_view3.setNavigationItemSelectedListener(this)

        listsAdapter = RecipesByOriginAdapter(applicationContext)
        rvPosts.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        rvPosts.adapter = listsAdapter

        initPostsListener()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, FindRecipeActivity::class.java))

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        drawer_layout3.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initPostsListener() {
        FirebaseDatabase.getInstance()
            .getReference("items")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                    val newList = dataSnapshot.getValue<Recipe>(Recipe::class.java)
                    listsAdapter.addPost(newList, origin)

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
