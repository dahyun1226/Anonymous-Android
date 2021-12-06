package com.anonymous.appilogue.features.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentNicknameEditDialogBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.features.main.MainViewModel
import com.anonymous.appilogue.utils.showSoftInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameEditFragment :
    BaseFragment<FragmentNicknameEditDialogBinding, MainViewModel>(R.layout.fragment_nickname_edit_dialog) {
    override val viewModel: MainViewModel by activityViewModels()
    private val _profileViewModel: ProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            mainViewModel = viewModel
            profileViewModel = _profileViewModel
        }
        with(binding) {
            viewModel.hideBottomNavigation()
            etNickname.showSoftInput()
            tvCancel.setOnClickListener {
                finishDialog()
            }
            tvComplete.setOnClickListener {
                saveNickname()
                finishDialog()
            }
        }
    }

    private fun saveNickname() {
        viewModel.myUser.value?.let { user ->
            _profileViewModel.saveNickname(user)
            _profileViewModel.nickname.value?.let { nickname ->
                viewModel.editNickname(
                    nickname = nickname
                )
            }
        }
    }

    private fun finishDialog() {
        parentFragmentManager.commit {
            remove(this@NicknameEditFragment)
        }
        viewModel.showBottomNavigation()
    }
}