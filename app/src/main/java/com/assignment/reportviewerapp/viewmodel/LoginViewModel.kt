package com.assignment.reportviewerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.reportviewerapp.utils.AppPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val account = task?.getResult(ApiException::class.java)
                account?.let {
                    // Save user data
                    appPreferences.apply {
                        isLoggedIn = true
                        userId = it.id
                        userName = it.displayName
                        userEmail = it.email
                        userPhotoUrl = it.photoUrl?.toString()
                    }
                    _loginState.value = LoginState.Success
                } ?: run {
                    _loginState.value = LoginState.Error("Account information not available")
                }
            } catch (e: ApiException) {
                val errorMessage = when (e.statusCode) {
                    GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "Sign in cancelled"
                    GoogleSignInStatusCodes.SIGN_IN_FAILED -> "Sign in failed"
                    GoogleSignInStatusCodes.NETWORK_ERROR -> "Network error occurred. Please check your connection."
                    else -> "Error: ${e.message}"
                }
                _loginState.value = LoginState.Error(errorMessage)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Unexpected error: ${e.message}")
            }
        }
    }

    fun bypassedLogin() {
        appPreferences.isLoggedIn = true
        _loginState.value = LoginState.Success
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}