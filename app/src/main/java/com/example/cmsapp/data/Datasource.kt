package com.example.cmsapp.data

import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import kotlinx.datetime.LocalDate


object Datasource {
    val users = listOf(
        User(id = 1, username = "Joao", password = "ffff", email = "jj@gmail.com", isAdmin = true, birthdate = LocalDate(1985, 1, 1), salt = ""),
        User(id = 2, username = "Jeswus", password = "aaa", email = "jc@heavn.com", isAdmin = false, birthdate = LocalDate(1992, 1, 1), salt = ""),
        User(id = 3, username = "Maria", password = "bbb", email = "maria@gmail.com", isAdmin = false, birthdate = LocalDate(1990, 1, 1), salt = ""),
        User(id = 4, username = "Napoleao", password = "aaaa", email = "napo@leao.fr", isAdmin = true, birthdate = LocalDate(1980, 1, 1), salt = ""),
        User(id = 5, username = "Alice", password = "ccc", email = "alice@wonderland.com", isAdmin = true, birthdate = LocalDate(1988, 1, 1), salt = ""),
        User(id = 6, username = "Bob", password = "ddd", email = "bob@builder.com", isAdmin = false, birthdate = LocalDate(1995, 1, 1), salt = ""),
        User(id = 7, username = "Carol", password = "eee", email = "carol@network.com", isAdmin = true, birthdate = LocalDate(1987, 1, 1), salt = ""),
        User(id = 8, username = "Dave", password = "fff", email = "dave@tech.com", isAdmin = false, birthdate = LocalDate(1993, 1, 1), salt = ""),
        User(id = 9, username = "Eve", password = "ggg", email = "eve@security.com", isAdmin = true, birthdate = LocalDate(1991, 1, 1), salt = ""),
        User(id = 10, username = "Frank", password = "hhh", email = "frank@data.com", isAdmin = false, birthdate = LocalDate(1989, 1, 1), salt = ""),
        User(id = 11, username = "Grace", password = "iii", email = "grace@compute.com", isAdmin = true, birthdate = LocalDate(1986, 1, 1), salt = ""),
        User(id = 12, username = "Heidi", password = "jjj", email = "heidi@crypto.com", isAdmin = false, birthdate = LocalDate(1994, 1, 1), salt = ""),
        User(id = 13, username = "Ivan", password = "kkk", email = "ivan@risk.com", isAdmin = true, birthdate = LocalDate(1982, 1, 1), salt = ""),
        User(id = 14, username = "Judy", password = "lll", email = "judy@codes.com", isAdmin = false, birthdate = LocalDate(1990, 1, 1), salt = ""),
        User(id = 15, username = "Mallory", password = "mmm", email = "mallory@attack.com", isAdmin = true, birthdate = LocalDate(1984, 1, 1), salt = "")
    )

   /* val movies = listOf(
        Movie(1, "Inception", "A mind-bending thriller", 2010, 1, 148, listOf("Sci-Fi", "Thriller")),
        Movie(2, "The Matrix", "A hacker discovers reality is an illusion", 1999, 2, 136, listOf("Sci-Fi", "Action")),
        Movie(3, "The Shawshank Redemption", "A man finds hope in prison", 1994, 3, 142, listOf("Drama")),
        Movie(4, "Pulp Fiction", "Stories of crime intertwine", 1994, 4, 154, listOf("Crime", "Drama")),
        Movie(5, "The Dark Knight", "Batman faces the Joker", 2008, 1, 152, listOf("Action", "Crime")),
        Movie(6, "Forrest Gump", "A man with a big heart lives an extraordinary life and aids and some filler herew to test the screen size yadda yadda", 1994, 2, 142, listOf("Drama", "Romance")),
        Movie(7, "Fight Club", "An office worker and a soap maker form a fight club", 1999, 3, 139, listOf("Drama")),
        Movie(8, "Interstellar", "A team travels through a wormhole", 2014, 4, 169, listOf("Sci-Fi", "Adventure")),
        Movie(9, "The Godfather", "The story of the Corleone family", 1972, 1, 175, listOf("Crime", "Drama")),
        Movie(10, "The Lord of the Rings: The Fellowship of the Ringssssssssssssssssssss", "A hobbit embarks on a journey", 2001, 2, 178, listOf("Fantasy", "Adventure")),
        Movie(11, "The Lion King", "A young lion cub finds his place", 1994, 3, 88, listOf("Animation", "Adventure")),
        Movie(12, "Gladiator", "A betrayed general seeks revenge", 2000, 4, 155, listOf("Action", "Drama")),
        Movie(13, "Jurassic Park", "Dinosaurs come back to life", 1993, 1, 127, listOf("Adventure", "Sci-Fi")),
        Movie(14, "Titanic", "A romance blossoms on a doomed ship", 1997, 2, 195, listOf("Drama", "Romance")),
        Movie(15, "Saving Private Ryan", "A mission to save one soldier", 1998, 3, 169, listOf("Action", "War"))
    )
*/

    val genres = listOf(
        "Action","Animation","Comedy","Drama","Documentary","Horror","Sci-Fi","Romance","Thriller"
    )

}