package com.example.bancom.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bancom.R
import com.example.bancom.base.BaseFragment
import com.example.bancom.common.Constant
import com.example.bancom.databinding.FragmentHomeBinding
import com.example.bancom.feature.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment(), UserListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(layoutInflater)

        initView()
        initViewModel()
        initViewObserver()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.home_title_bar)
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
    }

    override fun initView() {
        adapter = HomeUserAdapter(this)
        binding.recyclerUser.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerUser.adapter = adapter
    }

    override fun initViewModel() {
        showProgress()
        viewModel.getUsers()
    }

    override fun initViewObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect {
                    when (it) {
                        is HomeUIState.ShowListUser -> {
                            hideProgress()
                            adapter.submitList(it.data)
                        }

                        is HomeUIState.Error -> {
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

    override fun goToDetail(userId: Long) {
        val bundle = Bundle()
        bundle.putLong(Constant.BUNDLE_USER_ID, userId)

        //val navController = Navigation.findNavController(binding.root)
        //navController.navigate(R.id.detailFragment, bundle)

        val myBottomSheet = DetailFragment()
        myBottomSheet.arguments = bundle
        myBottomSheet.show(childFragmentManager, myBottomSheet.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}