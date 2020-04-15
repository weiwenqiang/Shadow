package com.zzz.myemergencyclientnew.widget.tangram

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.utils.UiUtils
import java.util.*

class MeItemView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) :
    FrameLayout(mContext, attrs, defStyleAttr),
    ITangramViewLifeCycle {

    init {
        initUI(context)
    }

    private lateinit var stv: SuperTextView

    private fun initUI(context: Context) {
        View.inflate(context, R.layout.v_me_item, this)
        stv = findViewById(R.id.stv_me)
    }

    override fun cellInited(cell: BaseCell<*>) {
        setOnClickListener(cell)
    }

    override fun postBindView(cell: BaseCell<*>) {
        val title=  cell.optStringParam("title")
        val iconNormal=  cell.optStringParam("iconNormal")
        val background=  cell.optStringParam("background")

        stv.setCenterString(title)
        Glide.with(context.applicationContext)
            .load(iconNormal)
            .dontAnimate()
            .placeholder(R.drawable.ic_placeholder)
            .into(object :
                CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    stv.setLeftTvDrawableRight(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        val resID = context.resources.getIdentifier(background, "drawable", context.packageName)
        stv.setSBackground(context.getDrawable(resID))
        stv.isClickable = false
        val funType = cell.optIntParam("funType")
        if(funType == 3){

        }
    }

    override fun postUnBindView(cell: BaseCell<*>) {}
}