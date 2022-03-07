package com.hlibrary.adapter.recycler

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.hlibrary.adapter.model.HeadFootAdapterVo
import com.hlibrary.adapter.model.RecyclerViewHolder

/**
 * Created by linwenhui on 2015/9/29.
 */
abstract class ExpandRecyclerAdapter<T, HVo : HeadFootAdapterVo<T>?, FVo : HeadFootAdapterVo<T>?>(context: Context?, layoutId: Int) : ObjectBaseRecyclerAdapter<T, ViewDataBinding>(context, layoutId) {
    private val headLayoutIds: MutableList<HVo> = ArrayList()
    private val footLayoutIds: MutableList<FVo> = ArrayList()
    private var onHeadItemClickListener: OnItemClickListener<HVo>? = null
    private var onFootItemClickListener: OnItemClickListener<FVo>? = null
    private val headEventImp: ExpandEventImp<HVo>
    private val footEventImp: ExpandEventImp<FVo>

    fun addHeaderObject(t: HVo?) {
        if (t != null) {
            headLayoutIds.add(t)
        }
    }

    fun addHeaderObject(pos: Int, t: HVo?) {
        if (pos > -1 && pos < headCount && t != null) {
            headLayoutIds.add(pos, t)
        }
    }

    fun removeHeaderObject(t: HVo?) {
        if (t != null) {
            headLayoutIds.remove(t)
        }
    }

    fun removeHeaderObject(pos: Int) {
        if (pos > -1 && pos < headCount) {
            headLayoutIds.removeAt(pos)
        }
    }

    fun addHeaderAll(ts: Collection<HVo>?) {
        if (ts?.isNotEmpty() == true) {
            headLayoutIds.addAll(ts)
        }
    }

    fun addHeaderAll(pos: Int, ts: Collection<HVo>?) {
        if (pos > -1 && pos < headCount && ts?.isNotEmpty() == true) {
            headLayoutIds.addAll(pos, ts)
        }
    }

    fun removeHeaderAll() {
        headLayoutIds.clear()
    }

    fun addFooterObject(t: FVo?) {
        if (t != null) {
            footLayoutIds.add(t)
        }
    }

    fun addFooterObject(pos: Int, t: FVo?) {
        if (pos > -1 && pos < footCount && t != null) {
            footLayoutIds.add(pos, t)
        }
    }

    fun removeFooterObject(t: FVo?) {
        if (t != null) {
            footLayoutIds.remove(t)
        }
    }

    fun removeFooterObject(pos: Int) {
        if (pos > -1 && pos < footCount) {
            footLayoutIds.removeAt(pos)
        }
    }

    fun addFooterAll(ts: Collection<FVo>?) {
        if (ts?.isNotEmpty() == true) {
            footLayoutIds.addAll(ts)
        }
    }

    fun addFooterAll(pos: Int, ts: Collection<FVo>?) {
        if (pos > -1 && pos < footCount && ts?.isNotEmpty() == true) {
            footLayoutIds.addAll(pos, ts)
        }
    }

    fun removeFooterAll() {
        footLayoutIds.clear()
    }


    fun setOnHeadItemClickListener(onHeadItemClickListener: OnItemClickListener<HVo>?) {
        this.onHeadItemClickListener = onHeadItemClickListener
    }

    fun setOnFootItemClickListener(onFootItemClickListener: OnItemClickListener<FVo>?) {
        this.onFootItemClickListener = onFootItemClickListener
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + headCount + footCount
    }

    private val headCount: Int
        get() = headLayoutIds.size

    fun getHeadItem(position: Int): HVo {
        return headLayoutIds[position]
    }

    private val footCount: Int
        get() = footLayoutIds.size

    fun getFootItem(position: Int): FVo {
        return footLayoutIds[position]
    }

    override fun getItem(position: Int): T? {
        return super.getItem(position - headCount)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<ViewDataBinding>, position: Int) {
        val headLayoutNum = headCount
        val footLayoutNum = footCount
        val relativeFootPos = position - headLayoutNum - super.getItemCount()
        if (position >= 0 && position < headLayoutNum) {
            onBindHeadViewHolder(holder, position)
        } else if (relativeFootPos >= 0 && relativeFootPos < footLayoutNum) {
            onBindFootViewHolder(holder, relativeFootPos)
        } else {
            super.onBindViewHolder(holder, position)
            onBindViewHolderNew(holder, position - headLayoutNum)
        }
    }

    fun onBindViewHolderNew(holder: RecyclerViewHolder<ViewDataBinding>, position: Int) {

    }

    fun onBindHeadViewHolder(holder: RecyclerViewHolder<ViewDataBinding>, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(headEventImp)
    }

    fun onBindFootViewHolder(holder: RecyclerViewHolder<ViewDataBinding>, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(footEventImp)
    }

    final override fun getItemViewType(position: Int): Int {
        val headLayoutNum = headCount
        val footLayoutNum = footCount
        val relativeFootPos = position - headLayoutNum - super.getItemCount()
        return if (position >= 0 && position < headLayoutNum) {
            getHeadItem(position)!!.layoutResId
        } else if (relativeFootPos >= 0 && relativeFootPos < footLayoutNum) {
            getFootItem(relativeFootPos)!!.layoutResId
        } else {
            getItemViewTypeNew(position)
        }
    }

    fun getItemViewTypeNew(position: Int): Int {
        return super.getItemViewType(position)
    }

    abstract inner class ExpandEventImp<T>(adapter: ExpandRecyclerAdapter<*, HVo, FVo>?) : EventImp<T, ExpandRecyclerAdapter<*, HVo, FVo>?>(adapter) {

        abstract fun getOnItemClickListener(adapter: ExpandRecyclerAdapter<*, HVo, FVo>?): OnItemClickListener<T>?

        override fun onItemClick(v: View?, postion: Int, bean: T?) {
            if (adapterWeakReference != null) {
                val adapter = adapterWeakReference?.get()
                if (adapter != null) {
                    val itemClickListener = getOnItemClickListener(adapter)
                    itemClickListener?.onItemClick(v, postion, bean)
                }
            }
        }
    }

    init {
        headEventImp = object : ExpandEventImp<HVo>(this) {


            override fun getEventItem(pos: Int): HVo? {
                return getHeadItem(pos)
            }

            override fun getOnItemClickListener(adapter: ExpandRecyclerAdapter<*, HVo, FVo>?): OnItemClickListener<HVo>? {
                return adapter?.onHeadItemClickListener
            }
        }

        footEventImp = object : ExpandEventImp<FVo>(this) {

            override fun getEventItem(pos: Int): FVo? {
                return getFootItem(pos)
            }

            override fun getOnItemClickListener(adapter: ExpandRecyclerAdapter<*, HVo, FVo>?): OnItemClickListener<FVo>? {
                return adapter?.onFootItemClickListener
            }
        }
    }
}