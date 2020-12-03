package hu.bme.aut.recipebook.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.recipebook.EditRecipe
import hu.bme.aut.recipebook.R
import hu.bme.aut.recipebook.ReviewRecipeActivity
import hu.bme.aut.recipebook.data.Recipe
import kotlinx.android.synthetic.main.card_recipes.view.*

class AllRecipeAdapter(private val context: Context) : RecyclerView.Adapter<AllRecipeAdapter.ViewHolder>() {

    private val Recipes: MutableList<Recipe> = mutableListOf()
    private var lastPosition = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRecipeName: TextView = itemView.tvRecipeName
        val tvOrigin: TextView = itemView.tvOrigin
        val llItem: LinearLayout = itemView.llItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.card_recipes, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tmpItem = Recipes[position]
        viewHolder.tvRecipeName.text = tmpItem.name
        viewHolder.tvOrigin.text = tmpItem.origin

        val ingredientsList = listOf<String>()

        viewHolder.llItem.setOnClickListener{

            FirebaseDatabase.getInstance().reference.child("items").addValueEventListener(object : ValueEventListener {

                override fun onDataChange(datasnapshot: DataSnapshot) {

                    var map: Map<String,Any>
                    var i = 0

                    for (ds in datasnapshot.children) {

                        if (ds.key.toString().equals(tmpItem.name)) {

                            map = ds.value as Map<String, Any>
                            val ingredients = map["ingredients"]

                            val ingredientList = ingredients.toString().split(",").toTypedArray()


                            for(ingredient in ingredientList){
                                ingredientsList.plus(ingredient)

                            }
                        }
                    }
                    val intent = Intent(it.context, ReviewRecipeActivity::class.java)

                    val list = ingredientsList.toTypedArray()

                    intent.putExtra("recipeName", tmpItem.name)
                    intent.putExtra("ingredients", tmpItem.ingredients)
                    intent.putExtra("origin", tmpItem.origin)
                    intent.putExtra("steps", tmpItem.steps)
                    intent.putExtra("originBy", "none")

                    it.context.startActivity(intent)
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }



        setAnimation(viewHolder.itemView, position)
    }

    override fun getItemCount() = Recipes.size

    fun addPost(rlist: Recipe?) {
        rlist ?: return

        Recipes.add(rlist)
        notifyDataSetChanged()

    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}