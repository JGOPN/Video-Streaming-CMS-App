package com.example.cmsapp.data

import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import java.util.Date

object Datasource {
    val users = listOf(
        User(1, "Joao", "ffff", "jj@gmail.com", true),
        User(2, "Jeswus", "aaa", "jc@heavn.com", false),
        User(3, "Maria", "bbb", "maria@gmail.com", false),
        User(4, "Napoleao", "aaaa", "napo@leao.fr", true),
        User(5, "Alice", "ccc", "alice@wonderland.com", true),
        User(6, "Bob", "ddd", "bob@builder.com", false),
        User(7, "Carol", "eee", "carol@network.com", true),
        User(8, "Dave", "fff", "dave@tech.com", false),
        User(9, "Eve", "ggg", "eve@security.com", true),
        User(10, "Frank", "hhh", "frank@data.com", false),
        User(11, "Grace", "iii", "grace@compute.com", true),
        User(12, "Heidi", "jjj", "heidi@crypto.com", false),
        User(13, "Ivan", "kkk", "ivan@risk.com", true),
        User(14, "Judy", "lll", "judy@codes.com", false),
        User(15, "Mallory", "mmm", "mallory@attack.com", true)
    )

    val movies = listOf(
        Movie(1, "Inception", "A mind-bending thriller", 2010, 1, 148, Pair(Date(123, 10, 2), 85), listOf("Sci-Fi", "Thriller")),
        Movie(2, "The Matrix", "A hacker discovers reality is an illusion", 1999, 2, 136, Pair(Date(123, 9, 20), 120), listOf("Sci-Fi", "Action")),
        Movie(3, "The Shawshank Redemption", "A man finds hope in prison", 1994, 3, 142, Pair(Date(123, 7, 19), 142), listOf("Drama")),
        Movie(4, "Pulp Fiction", "Stories of crime intertwine", 1994, 4, 154, Pair(Date(123, 6, 25), 95), listOf("Crime", "Drama")),
        Movie(5, "The Dark Knight", "Batman faces the Joker", 2008, 1, 152, Pair(Date(123, 4, 30), 120), listOf("Action", "Crime")),
        Movie(6, "Forrest Gump", "A man with a big heart lives an extraordinary life", 1994, 2, 142, Pair(Date(123, 3, 21), 110), listOf("Drama", "Romance")),
        Movie(7, "Fight Club", "An office worker and a soap maker form a fight club", 1999, 3, 139, Pair(Date(123, 5, 15), 80), listOf("Drama")),
        Movie(8, "Interstellar", "A team travels through a wormhole", 2014, 4, 169, Pair(Date(123, 2, 9), 90), listOf("Sci-Fi", "Adventure")),
        Movie(9, "The Godfather", "The story of the Corleone family", 1972, 1, 175, Pair(Date(123, 8, 12), 140), listOf("Crime", "Drama")),
        Movie(10, "The Lord of the Rings: The Fellowship of the Ringsssssssssss", "A hobbit embarks on a journey", 2001, 2, 178, Pair(Date(123, 7, 3), 120), listOf("Fantasy", "Adventure")),
        Movie(11, "The Lion King", "A young lion cub finds his place", 1994, 3, 88, Pair(Date(123, 9, 28), 88), listOf("Animation", "Adventure")),
        Movie(12, "Gladiator", "A betrayed general seeks revenge", 2000, 4, 155, Pair(Date(123, 6, 18), 95), listOf("Action", "Drama")),
        Movie(13, "Jurassic Park", "Dinosaurs come back to life", 1993, 1, 127, Pair(Date(123, 7, 9), 110), listOf("Adventure", "Sci-Fi")),
        Movie(14, "Titanic", "A romance blossoms on a doomed ship", 1997, 2, 195, Pair(Date(123, 10, 5), 150), listOf("Drama", "Romance")),
        Movie(15, "Saving Private Ryan", "A mission to save one soldier", 1998, 3, 169, Pair(Date(123, 4, 16), 125), listOf("Action", "War"))
    )


}