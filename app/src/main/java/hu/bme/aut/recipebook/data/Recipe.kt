package hu.bme.aut.recipebook.data

class Recipe (
    var uid: String,
    var ingredients: String,
    var steps: String,
    var origin: String,
    var name: String
)
{
    constructor(): this(
        "",
        "",
        "",
        "",
        ""
    )
}