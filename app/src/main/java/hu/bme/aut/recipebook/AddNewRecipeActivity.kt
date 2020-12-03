package hu.bme.aut.recipebook

import android.content.Intent
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.recipebook.data.Recipe
import hu.bme.aut.recipebook.extension.validateNonEmpty
import kotlinx.android.synthetic.main.activity_add_new_recipe.*

class AddNewRecipeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)

        btnSave.setOnClickListener { saveClick() }

        btnCancel.setOnClickListener{
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

    private fun validateForm() = etRecipeName.validateNonEmpty() && etOrigin.validateNonEmpty() && etOrigin.validateNonEmpty() && etIngredients.validateNonEmpty() && etSteps.validateNonEmpty()

    private fun uploadItem() {

        val newItem = Recipe(
            uid,
            etIngredients.text.toString(),
            etSteps.text.toString(),
            etOrigin.text.toString(),
            etRecipeName.text.toString()
        )

        FirebaseDatabase.getInstance().reference
            .child("items")
            .child(etRecipeName.text.toString())
            .setValue(newItem)
            .addOnCompleteListener {
                toast(getString(R.string.item_created))
                val intent = Intent(this, RecipesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }

    }
}
