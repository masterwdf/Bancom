package com.example.bancom.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bancom.R
import com.example.bancom.base.BaseBottomSheet
import com.example.bancom.common.Constant
import com.example.bancom.databinding.FragmentDetailBinding
import com.example.domain.entity.Post
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseBottomSheet() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private var userId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentDetailBinding.inflate(layoutInflater)

        initView()
        initViewModel()
        initViewObserver()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.detail_title_bar)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = 1000
        }
        val view = view
        view?.post {
            val parent = view.parent as View
            val params =
                parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior =
                behavior as BottomSheetBehavior<*>?
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.peekHeight = view.measuredHeight
            }
        }
    }

    override fun initView() {
        binding.btnGuardar.setOnClickListener {
            savePost()
        }
    }

    private fun savePost() {
        hideKeyBoard(binding.btnGuardar)

        if (binding.edtTitle.text.isNullOrEmpty()) {
            showMessageSnack(binding.root, getString(R.string.detail_title_required))
            return
        }

        if (binding.edtDescription.text.isNullOrEmpty()) {
            showMessageSnack(binding.root, getString(R.string.detail_description_required))
            return
        }

        userId?.let {
            showProgress()
            viewModel.addPosts(
                Post(
                    null, it, binding.edtTitle.text.toString(), binding.edtDescription.text.toString()
                )
            )
        }
    }

    override fun initViewModel() {
        userId = arguments?.getLong(Constant.BUNDLE_USER_ID)
    }

    override fun initViewObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.post.collect {
                    when (it) {
                        is DetailUIState.SuccessPost -> {
                            hideProgress()
                            showMessageSnack(binding.root, "Se agregó correctamente")
                            dismissAllowingStateLoss()
                        }

                        is DetailUIState.Error -> {
                            hideProgress()
                            processError(binding.root, it.exception)
                        }

                        else -> {
                            hideProgress()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}