package hu.bme.aut.recipebook

import android.content.Intent
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.recipebook.data.Recipe
import hu.bme.aut.recipebook.extension.validateNonEmpty
import kotlinx.android.synthetic.main.activity_add_new_recipe.*
import kotlinx.android.synthetic.main.activity_edit_recipe.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditRecipe : BaseActivity() {

    var recipeName:String = ""
    var origin:String = ""
    var ingredients:String = ""
    var steps:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        print(this.intent.extras.get("name"))

        recipeName = (this.intent.extras.get("recipeName") as? String).toString()
        origin = (this.intent.extras.get("origin") as? String).toString()
        ingredients = (this.intent.extras.get("ingredients") as? String).toString()
        steps = (this.intent.extras.get("steps") as? String).toString()


        etRecipeName2.setText(recipeName)
        etOrigin2.setText(origin)
        etIngredients2.setText(ingredients)
        etSteps2.setText(steps)



        btnSave2.setOnClickListener{ saveClick() }
        btnCancel2.setOnClickListener{
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun saveClick() {
        if (!validateForm()) {
            return
        }

        uploadItem()
    }

    private fun validateForm()=
        etRecipeName2.validateNonEmpty() &&
                etSteps2.validateNonEmpty() &&
                etIngredients2.validateNonEmpty() &&
                etOrigin2.validateNonEmpty()


    private fun uploadItem() {
        val newItem = Recipe(
            uid,
            etIngredients2.text.toString(),
            etSteps2.text.toString(),
            etOrigin2.text.toString(),
            etRecipeName2.text.toString()
        )

        FirebaseDatabase.getInstance().reference
            .child("items")
            .child(etRecipeName2.text.toString())
            .setValue(newItem)
            .addOnCompleteListener {
                toast(getString(R.string.item_updated))
                val intent = Intent(this, RecipesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
    }
}
