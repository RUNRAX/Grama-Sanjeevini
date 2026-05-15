package com.example.healthwealth.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

/**
 * Utility class for iOS-inspired animations.
 */
object GlassAnimationUtils {

    /**
     * Staggered fade + translate-up entrance for RecyclerView items.
     */
    fun animateItemEntrance(view: View, position: Int) {
        view.alpha = 0f
        view.translationY = 100f

        view.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay((position * 40).toLong())
            .setInterpolator(DecelerateInterpolator(2f))
            .start()
    }

    /**
     * iOS-style spring press animation.
     */
    fun setupPressAnimation(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .scaleX(0.96f)
                        .scaleY(0.96f)
                        .setDuration(120)
                        .setInterpolator(DecelerateInterpolator())
                        .start()
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(400)
                        .setInterpolator(OvershootInterpolator(1.2f))
                        .start()
                }
            }
            false
        }
    }

    /**
     * Subtle pulse for critical items.
     */
    fun startBreathingPulse(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.7f)
        animator.duration = 1500
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }
}
