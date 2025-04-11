package uk.ac.tees.mad.quoteverse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.quoteverse.data.local.ThemePreferenceManager
import uk.ac.tees.mad.quoteverse.utils.Constants
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferenceManager: ThemePreferenceManager
):ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private var db : FirebaseFirestore = Firebase.firestore

    private val _name = MutableStateFlow("")
    val name:StateFlow<String> get() = _name

    private val _isEmailSent = MutableStateFlow(false)
    val isEmailSent:StateFlow<Boolean> get() = _isEmailSent

    val isDarkTheme = themePreferenceManager.themePreference.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    init {
        viewModelScope.launch {
            db.collection(Constants.USER)
                .document(getUserId())
                .get().addOnSuccessListener {
                    val userName = it.get("name") as String
                    _name.value = userName
                }
        }
    }

    fun changeUserName(newName:String){
        viewModelScope.launch {
            try {
                val userDocRef = db.collection(Constants.USER).document(getUserId())

                userDocRef.update("name", newName).addOnSuccessListener {
                    _name.value = newName
                }
            } catch (e: Exception) {
               Log.e("Name change", e.message.toString())
            }
        }
    }

    fun logOut(){
        auth.signOut()
    }

    fun resetPassword(email:String){
        viewModelScope.launch {
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    _isEmailSent.value = true
                }
                .addOnFailureListener {
                    _isEmailSent.value = true
                    Log.e("change password", "Error in password reset")
                }
        }
    }

    fun setIsEmailSent(value:Boolean){
        _isEmailSent.value = value
    }

    private fun getUserId():String{
        return auth.currentUser?.uid ?: ""
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themePreferenceManager.saveThemePreference(!isDarkTheme.value)
        }
    }
}