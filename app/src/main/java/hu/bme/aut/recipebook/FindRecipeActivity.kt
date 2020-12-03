package hu.bme.aut.recipebook

import android.content.Intent
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.recipebook.extension.validateNonEmpty
import kotlinx.android.synthetic.main.activity_find_recipe.*
import kotlinx.android.synthetic.main.activity_main.*

class FindRecipeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_find_recipe)

        btnSearch.setOnClickListener { searchClick() }
        btnAll.setOnClickListener { allClick() }
    }

    private fun validateForm() = etOriginFindBy.validateNonEmpty()

    private fun searchClick() {
        if (!validateForm()) {
            return
        }

        val intent = Intent(this@FindRecipeActivity, FoundRecipeActivity::class.java)

        intent.putExtra("origin", etOriginFindBy.text.toString())

        this@FindRecipeActivity.startActivity(intent)

        finish()
    }

    private fun allClick() {

        startActivity(Intent(this@FindRecipeActivity, AllRecipeActivity::class.java))
        finish()

    }

}
