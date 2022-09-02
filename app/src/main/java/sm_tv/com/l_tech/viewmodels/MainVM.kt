package sm_tv.com.l_tech.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sm_tv.com.l_tech.fragments.models.Item
import sm_tv.com.l_tech.networking.Repository

class MainVM : ViewModel() {

    var phoneMask: MutableLiveData<String> = MutableLiveData()
    var responseAuth: MutableLiveData<Boolean> = MutableLiveData()
    var listItem: MutableLiveData<List<Item>?> = MutableLiveData()
    private val repository: Repository = Repository()

    fun getMaskFromServer() {
        viewModelScope.launch {
            val repository = repository.getMaskFromServer()
            phoneMask.value = repository
        }
    }

    fun signIn(phone:String, password: String){
        viewModelScope.launch {
            val repository = repository.signIn(phone, password)
            responseAuth.value = repository
        }
    }

    fun getListItem(){
        viewModelScope.launch {
            val repository = repository.getListItem()
            listItem.value = repository
        }
    }
}
