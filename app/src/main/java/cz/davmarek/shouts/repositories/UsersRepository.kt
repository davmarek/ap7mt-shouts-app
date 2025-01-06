package cz.davmarek.shouts.repositories

import cz.davmarek.shouts.api.UsersApi
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.User

class UsersRepository(private val api: UsersApi) {
//    suspend fun getShouts(): List<Shout>{
//        return api.getShouts()
//    }

    suspend fun getUser(id: String): User {
        return api.getUser(id)
    }

//    suspend fun createShout(text: String): Shout {
//        return api.createShout(CreateShoutRequest(text))
//    }
//
//    suspend fun updateShout(id: String, text: String) {
//        return api.updateShout(id, text)
//    }
//
//    suspend fun deleteShout(id: String) {
//        return api.deleteShout(id)
//    }

}