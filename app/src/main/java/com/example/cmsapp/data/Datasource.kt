package com.example.cmsapp.data

import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

object Datasource {
    val users = listOf(
        User(1, "Joao", "ffff", "jj@gmail.com", true, LocalDate.of(1985, 1, 1)),
        User(2, "Jeswus", "aaa", "jc@heavn.com", false, LocalDate.of(1992, 1, 1)),
        User(3, "Maria", "bbb", "maria@gmail.com", false, LocalDate.of(1990, 1, 1)),
        User(4, "Napoleao", "aaaa", "napo@leao.fr", true, LocalDate.of(1980, 1, 1)),
        User(5, "Alice", "ccc", "alice@wonderland.com", true, LocalDate.of(1988, 1, 1)),
        User(6, "Bob", "ddd", "bob@builder.com", false, LocalDate.of(1995, 1, 1)),
        User(7, "Carol", "eee", "carol@network.com", true, LocalDate.of(1987, 1, 1)),
        User(8, "Dave", "fff", "dave@tech.com", false, LocalDate.of(1993, 1, 1)),
        User(9, "Eve", "ggg", "eve@security.com", true, LocalDate.of(1991, 1, 1)),
        User(10, "Frank", "hhh", "frank@data.com", false, LocalDate.of(1989, 1, 1)),
        User(11, "Grace", "iii", "grace@compute.com", true, LocalDate.of(1986, 1, 1)),
        User(12, "Heidi", "jjj", "heidi@crypto.com", false, LocalDate.of(1994, 1, 1)),
        User(13, "Ivan", "kkk", "ivan@risk.com", true, LocalDate.of(1982, 1, 1)),
        User(14, "Judy", "lll", "judy@codes.com", false, LocalDate.of(1990, 1, 1)),
        User(15, "Mallory", "mmm", "mallory@attack.com", true, LocalDate.of(1984, 1, 1))
    )

    val movies = listOf(
        Movie(1, "Inception", "A mind-bending thriller", 2010, 1, 148, LocalDateTime.of(2023, 11, 2, 14, 0), listOf("Sci-Fi", "Thriller")),
        Movie(2, "The Matrix", "A hacker discovers reality is an illusion", 1999, 2, 136, LocalDateTime.of(2023, 10, 20, 19, 30), listOf("Sci-Fi", "Action")),
        Movie(3, "The Shawshank Redemption", "A man finds hope in prison", 1994, 3, 142, LocalDateTime.of(2023, 8, 19, 16, 45), listOf("Drama")),
        Movie(4, "Pulp Fiction", "Stories of crime intertwine", 1994, 4, 154, LocalDateTime.of(2023, 7, 25, 21, 15), listOf("Crime", "Drama")),
        Movie(5, "The Dark Knight", "Batman faces the Joker", 2008, 1, 152, LocalDateTime.of(2023, 5, 30, 13, 20), listOf("Action", "Crime")),
        Movie(6, "Forrest Gump", "A man with a big heart lives an extraordinary life", 1994, 2, 142, LocalDateTime.of(2023, 4, 21, 18, 10), listOf("Drama", "Romance")),
        Movie(7, "Fight Club", "An office worker and a soap maker form a fight club", 1999, 3, 139, LocalDateTime.of(2023, 6, 15, 20, 40), listOf("Drama")),
        Movie(8, "Interstellar", "A team travels through a wormhole", 2014, 4, 169, LocalDateTime.of(2023, 3, 9, 12, 0), listOf("Sci-Fi", "Adventure")),
        Movie(9, "The Godfather", "The story of the Corleone family", 1972, 1, 175, LocalDateTime.of(2023, 9, 12, 15, 50), listOf("Crime", "Drama")),
        Movie(10, "The Lord of the Rings: The Fellowship of the Ringsssssssssss", "A hobbit embarks on a journey", 2001, 2, 178, LocalDateTime.of(2023, 8, 3, 10, 30), listOf("Fantasy", "Adventure")),
        Movie(11, "The Lion King", "A young lion cub finds his place", 1994, 3, 88, LocalDateTime.of(2023, 10, 28, 14, 0), listOf("Animation", "Adventure")),
        Movie(12, "Gladiator", "A betrayed general seeks revenge", 2000, 4, 155, LocalDateTime.of(2023, 7, 18, 22, 10), listOf("Action", "Drama")),
        Movie(13, "Jurassic Park", "Dinosaurs come back to life", 1993, 1, 127, LocalDateTime.of(2023, 8, 9, 11, 45), listOf("Adventure", "Sci-Fi")),
        Movie(14, "Titanic", "A romance blossoms on a doomed ship", 1997, 2, 195, LocalDateTime.of(2023, 11, 5, 19, 0), listOf("Drama", "Romance")),
        Movie(15, "Saving Private Ryan", "A mission to save one soldier", 1998, 3, 169, LocalDateTime.of(2023, 5, 16, 16, 30), listOf("Action", "War"))
    )


}