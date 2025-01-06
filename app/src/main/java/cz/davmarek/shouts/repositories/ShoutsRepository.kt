package cz.davmarek.shouts.repositories

import cz.davmarek.shouts.api.ShoutsApi
import cz.davmarek.shouts.models.CreateShoutRequest
import cz.davmarek.shouts.models.Shout

class ShoutsRepository(private val api: ShoutsApi) {
    suspend fun getShouts(): List<Shout>{
        return api.getShouts()
    }

    suspend fun getShout(id: String): Shout{
        return api.getShout(id)
    }

    suspend fun createShout(text: String): Shout {
        return api.createShout(CreateShoutRequest(text))
    }

    suspend fun updateShout(id: String, text: String) {
        return api.updateShout(id, text)
    }

    suspend fun deleteShout(id: String) {
        return api.deleteShout(id)
    }

    suspend fun searchShouts(searchQuery: String): List<Shout> {
        return api.searchShouts(searchQuery)
    }

}