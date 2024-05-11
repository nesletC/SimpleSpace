package com.simplespace.android.spaces.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.spaces.data.SpaceAccessKeys
import com.simplespace.android.spaces.data.SpaceAuthentication
import com.simplespace.android.spaces.data.SpaceAuthentication.Action
import com.simplespace.android.spaces.model.SpaceAccess
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SpaceAuthenticationViewModel(
    request: Action,
) : ViewModel() {

    private val _authenticationFailed = MutableSharedFlow<Unit>()

    val authenticationFailed = _authenticationFailed.asSharedFlow()

    private val _ioException =
        MutableSharedFlow<SpaceAuthentication.Error.IO>()

    val ioException = _ioException.asSharedFlow()

    private val _success = MutableSharedFlow<Unit>()

    val success = _success.asSharedFlow()


    val newKeyExpected = request is Action.Save.New


    operator fun invoke(input: String) {

        viewModelScope.launch {

            SpaceAccessKeys.validatedKeyString(input)?.let {

                val error = action(SpaceAccessKeys.getAccess(it))

                if (error == null) {

                    _success.emit(Unit)
                }

                else {

                    when (error) {

                        SpaceAuthentication.Error.AuthenticationFailed ->
                            _authenticationFailed.emit(Unit)

                        is SpaceAuthentication.Error.IO -> _ioException.emit(error)
                    }
                }
            }
        }
    }

    private val action: suspend (SpaceAccess) -> SpaceAuthentication.Error? = when (request) {

        is Action.Delete -> {
            {
                SpaceAuthentication.delete(
                    it, request.index
                )
            }
        }
        is Action.Save.New -> {
            {

                SpaceAuthentication.save(
                    it, request.metaToSave
                )
            }
        }
        is Action.Save.Update -> {
            {
                SpaceAuthentication.edit(
                    it, request.index, request.metaToSave
                )
            }
        }
        Action.Load -> {
            {
                SpaceAuthentication.load(
                    it
                )
            }
        }
    }
}