package com.sulv9.talktodo.util

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(requireActivity(), colorRes)