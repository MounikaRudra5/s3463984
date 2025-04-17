package uk.ac.tees.mad.quoteverse.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.ac.tees.mad.quoteverse.utils.Constants

class AuthenticationViewModel :ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore


    private val _name = MutableStateFlow("")
    val name:StateFlow<String> get() = _name

    private val _email = MutableStateFlow("")
    val email:StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password:StateFlow<String> get() = _password

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess:StateFlow<Boolean> get() = _loginSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun onNameChange(newName:String){
        _name.value = newName
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun onChangeISLoginSuccess(){
        _loginSuccess.value = false
    }

    fun createNewUser(context: Context){
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task->
                _isLoading.value = false
                _loginSuccess.value = task.isSuccessful
                if (task.isSuccessful){
                    val uid = task.result.user?.uid
                    db.collection(Constants.USER)
                        .document(uid!!)
                        .set(mapOf(
                            "name" to _name.value,
                            "email" to _email.value,
                            "id" to uid
                        ), SetOptions.merge())
                }
                else{
                    Toast.makeText(context,"Failed to sign up, please check network", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logInUser(context: Context){
        _isLoading.value = true
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener {task->
                _isLoading.value = false
                _loginSuccess.value = task.isSuccessful
                if(!task.isSuccessful){
                    Toast.makeText(context,"Failed to login, please check network", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun validateLoginForm(context: Context):Boolean{
        if(_email.value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()){
            Toast.makeText(context, "Wrong email address", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(_password.value.length<6){
            Toast.makeText(context, "Password is less than 6 character", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun validateSignupForm(context: Context):Boolean{
        if(_name.value.isEmpty()){
            Toast.makeText(context, "Name should not be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(_email.value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()){
            Toast.makeText(context, "Wrong email address", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(_password.value.length<6){
            Toast.makeText(context, "Password is less than 6 character", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}