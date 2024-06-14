package good.damn.kamchatka.views.interactions

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import good.damn.kamchatka.extensions.checkBounds
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener
import good.damn.kamchatka.views.interactions.interfaces.OnUpdateAnimationListener

class AnimatedTouchInteraction
: BaseTouchInteraction(),
ValueAnimator.AnimatorUpdateListener,
OnActionListener {

    var minValue: Float = 0.75f
    var maxValue: Float = 1.0f

    private var mOnUpdateListener: OnUpdateAnimationListener? = null
    private var mOnActionListener: OnActionListener? = null

    private val mAnimator = ValueAnimator()
    private var mCurrentValue = 0f

    init {
        mAnimator.duration = 350
        mAnimator.interpolator = DecelerateInterpolator()
        mAnimator.addUpdateListener(
            this
        )

        super.setOnActionListener(
            this
        )
    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        mCurrentValue = animation.animatedValue as Float
        mOnUpdateListener?.onUpdate(
            mCurrentValue
        )
    }

    override fun setOnActionListener(
        l: OnActionListener?
    ) {
        mOnActionListener = l
    }

    override fun onDown(
        v: View,
        action: MotionEvent
    ) {
        mOnActionListener?.onUp(
            v,
            action
        )
        mAnimator.setFloatValues(
            maxValue, minValue
        )
        mAnimator.start()
    }

    override fun onUp(
        v: View,
        action: MotionEvent
    ) {
        mAnimator.setFloatValues(
            mCurrentValue, maxValue
        )
        mAnimator.start()

        if (v.checkBounds(
            action.x,
            action.y
        )) {
            mOnActionListener?.onUp(
                v,  action
            )
            return
        }
    }

    fun setDuration(
        i: Long
    ) {
        mAnimator.setDuration(
            i
        )
    }

    fun setInterpolator(
        i: Interpolator
    ) {
        mAnimator.interpolator = i
    }

    fun setOnUpdateAnimationListener(
        l: OnUpdateAnimationListener?
    ) {
        mOnUpdateListener = l
    }

}