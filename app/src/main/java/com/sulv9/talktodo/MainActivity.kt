package com.sulv9.talktodo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.sulv9.talktodo.base.BaseActivity
import com.sulv9.talktodo.data.model.GroupWithTodos
import com.sulv9.talktodo.databinding.ActivityMainBinding
import com.sulv9.talktodo.pages.newgroup.NewTodoGroupFragment
import com.sulv9.talktodo.pages.newgroup.NewTodoGroupFragmentDirections
import com.sulv9.talktodo.util.CacheConst
import com.sulv9.talktodo.util.CacheUtil
import com.sulv9.talktodo.util.setLightStatusBar

class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    private val mCurrentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.main_nav_host)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun initBinding(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        setLightStatusBar(true)
        super.onCreate(savedInstanceState)
        initListener()
    }

    private fun initListener() {
        // 在onCreate时Fragment还未创建，不能直接使用findNavController()，需要使用findFragmentById替代
        val navFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navFragment.navController.addOnDestinationChangedListener(this)
        binding.mainFabAddNewTodoGroup.apply {
            setShowMotionSpecResource(R.animator.fab_show)
            setHideMotionSpecResource(R.animator.fab_hide)
            setOnClickListener { navigateToNewTodoGroup() }
        }
    }

    private fun navigateToNewTodoGroup() {
        if (mCurrentFragment is NewTodoGroupFragment) return
        mCurrentFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }
        val isDrafted = CacheUtil.get(CacheConst.KEY_IS_DRAFTED, false)
        val draftGroups = CacheUtil.get(CacheConst.KEY_DRAFT_GROUPS, GroupWithTodos::class.java)
        val directions = NewTodoGroupFragmentDirections
            .actionGlobalAddNewTodoGroup(isDrafted, draftGroups)
        findNavController(R.id.main_nav_host).navigate(directions)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {
                showBottomAppBar()
            }
            R.id.staticsFragment -> {
                showBottomAppBar()
            }
            R.id.newTodoGroupFragment -> {
                hideBottomAppBar()
            }
            R.id.todoGroupDetailFragment -> {
                hideBottomAppBar()
            }
            R.id.searchFragment -> {
                hideBottomAppBar()
            }
        }
    }

    private fun showBottomAppBar() {
//        binding.mainBottomAppBar.visibility = View.VISIBLE
        binding.mainBottomAppBar.performShow()
        binding.mainFabAddNewTodoGroup.show()
    }

    private fun hideBottomAppBar() {
        binding.mainBottomAppBar.performHide()
        binding.mainFabAddNewTodoGroup.hide()
//        with(binding.mainBottomAppBar) {
//            performHide()
//            animate().setListener(object : AnimatorListenerAdapter() {
//                var isCanceled = false
//                override fun onAnimationEnd(animator: Animator?) {
//                    if (isCanceled) return
//                    visibility = View.GONE
//                    binding.mainFabAddNewTodoGroup.visibility = View.INVISIBLE
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                    isCanceled = true
//                }
//            })
//        }
    }

}