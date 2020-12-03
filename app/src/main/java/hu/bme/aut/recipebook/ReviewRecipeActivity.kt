package hu.bme.aut.recipebook

import android.content.Intent
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import hu.bme.aut.recipebook.data.Recipe
import hu.bme.aut.recipebook.extension.validateNonEmpty
import kotlinx.android.synthetic.main.activity_add_new_recipe.*
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import kotlinx.android.synthetic.main.activity_review_recipe.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ReviewRecipeActivity : BaseActivity() {

    var recipeName:String = ""
    var origin:String = ""
    var ingredients:String = ""
    var steps:String = ""
    var originBy:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_recipe)


        recipeName = (this.intent.extras.get("recipeName") as? String).toString()
        origin = (this.intent.extras.get("origin") as? String).toString()
        ingredients = (this.intent.extras.get("ingredients") as? String).toString()
        steps = (this.intent.extras.get("steps") as? String).toString()
        originBy = (this.intent.extras.get("originBy") as? String).toString()


        etRecipeName3.setText(recipeName)
        etOrigin3.setText(origin)
        etIngredients3.setText(ingredients)
        etSteps3.setText(steps)


        if(originBy.equals("none")) {

            val intent = Intent(this, AllRecipeActivity::class.java)

            btnExit.setOnClickListener{
                startActivity(intent)
                finish()
            }
        }else{
            val intent = Intent(this, AllRecipeActivity::class.java)
            intent.putExtra("origin", originBy)
            btnExit.setOnClickListener{
                startActivity(intent)
                finish()
            }
        }





    }

}
