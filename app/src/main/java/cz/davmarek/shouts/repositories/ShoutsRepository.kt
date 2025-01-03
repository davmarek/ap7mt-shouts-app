package cz.davmarek.shouts.repositories

import cz.davmarek.shouts.api.ShoutsApi
import cz.davmarek.shouts.models.Shout

class ShoutsRepository(private val api: ShoutsApi) {
    suspend fun getShouts(): List<Shout>{
        return api.getShouts()
    }
    
}