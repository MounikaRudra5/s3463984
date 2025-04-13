package uk.ac.tees.mad.quoteverse.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepository
import uk.ac.tees.mad.quoteverse.data.local.ThemePreferenceManager
import uk.ac.tees.mad.quoteverse.utils.Constants
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferenceManager: ThemePreferenceManager,
    private val repository: FavoriteQuoteRepository
):ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private var db : FirebaseFirestore = Firebase.firestore

    private val _name = MutableStateFlow("")
    val name:StateFlow<String> get() = _name

    private val _isEmailSent = MutableStateFlow(false)
    val isEmailSent:StateFlow<Boolean> get() = _isEmailSent

    private val _accountDeleted = MutableStateFlow(false)
    val accountDeleted:StateFlow<Boolean> get() = _accountDeleted

    private val _showProgressbar = MutableStateFlow(false)
    val showProgressbar:StateFlow<Boolean> get() = _showProgressbar

    val isDarkTheme = themePreferenceManager.themePreference.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    init {
        viewModelScope.launch {
            try {
                val userDoc = db.collection(Constants.USER)
                    .document(getUserId())
                    .get()
                    .await()
                val userName = userDoc.getString("name")
                if (!userName.isNullOrEmpty()) {
                    _name.value = userName
                }
            } catch (e: Exception) {
                Log.e("SettingsViewModel", "Error fetching user data", e)
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

    fun deleteUser(context: Context) {
        val userId = getUserId()
        if (userId.isNotEmpty()) {
            _showProgressbar.value = true

            db.collection(Constants.USER)
                .document(userId)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        auth.currentUser?.delete()?.addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {

                                viewModelScope.launch {
                                    try {

                                        repository.deleteAllQuotesByUser(userId)
                                        _accountDeleted.value = true
                                        _showProgressbar.value = false
                                        Toast.makeText(context,"Account deleted", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Log.e("DeleteUser", "Error deleting quotes from Room", e)
                                        _accountDeleted.value = false
                                    } finally {
                                        _showProgressbar.value = false
                                    }
                                }
                            } else {
                                Log.e("DeleteUser", "Error deleting from Firebase Authentication", authTask.exception)
                                _showProgressbar.value = false
                            }
                        }
                    } else {
                        Log.e("DeleteUser", "Error deleting user from Firestore", task.exception)
                        _showProgressbar.value = false  // Hide progress bar on failure
                    }
                }
        } else {
            Log.e("DeleteUser", "User ID is empty")
            _showProgressbar.value = false  // Hide progress bar if user ID is empty
        }
    }

}