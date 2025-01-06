package cz.davmarek.shouts.repositories

import cz.davmarek.shouts.api.UsersApi
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.User

class UsersRepository(private val api: UsersApi) {

    suspend fun getUser(id: String): User {
        return api.getUser(id)
    }

    suspend fun getUserShouts(id: String): List<Shout> {
        return api.getUserShouts(id)
    }

}