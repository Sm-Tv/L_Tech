package sm_tv.com.l_tech.networking

import sm_tv.com.l_tech.fragments.models.Item

class Repository {
    suspend fun getMaskFromServer(): String {
        val response = RetrofitInstance.api.getMaskFromServer()
        return if (response.isSuccessful) {
            response.body()?.phoneMask ?: "+7 (ХХХ) ХХХ-ХХ-ХХ"
        } else {
            "+7 (ХХХ) ХХХ-ХХ-ХХ"
        }
    }

    suspend fun signIn(phone: String, password: String): Boolean {
        val response = RetrofitInstance.api.chekAuth(phone, password)
        return response.isSuccessful
    }

    suspend fun getListItem(): List<Item>? {
        val response = RetrofitInstance.api.getListItem()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
