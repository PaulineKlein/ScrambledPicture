package com.pklein.scrambledpicture.presentation.grid

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.GridLayout
import androidx.appcompat.widget.AppCompatImageView
import com.pklein.scrambledpicture.R
import com.pklein.scrambledpicture.databinding.ScrambledGridBinding


class ScrambledGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    var arrayImageViews: Array<AppCompatImageView>
    var arraySplittedImages: Array<Bitmap?>
    private var binding: ScrambledGridBinding =
        ScrambledGridBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
        LayoutInflater.from(context).inflate(R.layout.scrambled_grid, this, true)
        arrayImageViews = arrayOf(
            binding.iv1,
            binding.iv2,
            binding.iv3,
            binding.iv4,
            binding.iv5,
            binding.iv6,
            binding.iv7,
            binding.iv8,
            binding.iv9,
        )
        arraySplittedImages = arrayOf()
    }

    fun splitBitmap(picture: Bitmap?): Array<Bitmap?> {
        val scaledBitmap = Bitmap.createScaledBitmap(picture!!, 512, 512, true)
        val imgs = arrayOfNulls<Bitmap>(9)
        imgs[0] = Bitmap.createBitmap(scaledBitmap, 0, 0, 171, 171)
        imgs[1] = Bitmap.createBitmap(scaledBitmap, 171, 0, 171, 171)
        imgs[2] = Bitmap.createBitmap(scaledBitmap, 341, 0, 171, 171)
        imgs[3] = Bitmap.createBitmap(scaledBitmap, 0, 171, 171, 171)
        imgs[4] = Bitmap.createBitmap(scaledBitmap, 171, 171, 171, 171)
        imgs[5] = Bitmap.createBitmap(scaledBitmap, 341, 171, 171, 171)
        imgs[6] = Bitmap.createBitmap(scaledBitmap, 0, 341, 171, 171)
        imgs[7] = Bitmap.createBitmap(scaledBitmap, 171, 341, 171, 171)
        imgs[8] = Bitmap.createBitmap(scaledBitmap, 341, 341, 171, 171)
        arraySplittedImages = imgs
        return imgs
    }

    fun scrambledImage(listBitmaps: Array<Bitmap?>) {
        if (listBitmaps.size == arrayImageViews.size) {
            arrayImageViews[0].setImageBitmap(listBitmaps[2])
            arrayImageViews[1].setImageBitmap(listBitmaps[6])
            arrayImageViews[2].setImageBitmap(listBitmaps[5])
            arrayImageViews[3].setImageBitmap(listBitmaps[8])
            arrayImageViews[4].setImageBitmap(listBitmaps[3])
            arrayImageViews[5].setImageBitmap(listBitmaps[7])
            arrayImageViews[6].setImageBitmap(listBitmaps[1])
            arrayImageViews[7].setImageBitmap(listBitmaps[0])
            arrayImageViews[8].setImageBitmap(listBitmaps[4])
        }
    }

    fun unScrambledImage(context: Context?) {
        if (!arraySplittedImages.isNullOrEmpty()) {
            for (i in arraySplittedImages.indices) {
                imageViewAnimatedChange(context, arrayImageViews[i], arraySplittedImages[i])
            }
        }
    }

    private fun imageViewAnimatedChange(
        context: Context?,
        imageView: AppCompatImageView,
        new_image: Bitmap?
    ) {
        val animOut: Animation =
            AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
        val animIn: Animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        animOut.interpolator = AccelerateInterpolator()
        animIn.interpolator = DecelerateInterpolator()
        animOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                imageView.setImageBitmap(new_image)
                animIn.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {}
                })
                imageView.startAnimation(animIn)
            }
        })
        imageView.startAnimation(animOut)
    }
}