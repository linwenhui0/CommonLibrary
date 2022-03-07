package com.hlibrary.adapter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hlibrary.adapter.model.RecyclerViewHolder
import java.lang.ref.WeakReference
import java.util.*

abstract class ObjectBaseRecyclerAdapter<T, R : ViewDataBinding>(context: Context?, protected val layoutId: Int) : RecyclerView.Adapter<RecyclerViewHolder<R>>() {

    @JvmField
    protected var data: MutableList<T> = ArrayList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    protected var itemClickListener: OnItemClickListener<T>? = null
    private val eventImp: EventImp<T, ObjectBaseRecyclerAdapter<T,R>>

    init {
        eventImp = object : EventImp<T, ObjectBaseRecyclerAdapter<T,R>>(this) {
            override fun getEventItem(pos: Int): T? {
                return getItem(pos)
            }
        }
    }

    fun addObject(t: T?) {
        if (t != null) {
            data.add(t)
        }
    }

    fun addObject(pos: Int, t: T?) {
        if (pos > -1 && pos < itemCount && t != null) {
            data.add(pos, t)
        }
    }

    fun removeObject(t: T) {
        data.remove(t)
    }

    fun removeObject(pos: Int) {
        if (pos > -1 && pos < itemCount) {
            data.removeAt(pos)
        }
    }

    fun addAll(ts: Collection<T>?) {
        if (ts?.isNotEmpty() == true) {
            data.addAll(ts)
        }
    }

    fun addAll(pos: Int, ts: Collection<T>?) {
        if (pos > -1 && pos < itemCount && ts?.isNotEmpty() == true) {
            data.addAll(pos, ts)
        }
    }

    fun removeAll() {
        data.clear()
    }

    fun indexOf(model: T?): Int {
        return if (model != null) {
            data.indexOf(model)
        } else -1
    }

    open fun getItem(position: Int): T? {
        return try {
            data[position]
        } catch (e: Exception) {
            null
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>?) {
        this.itemClickListener = itemClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<R> {
        val holder: RecyclerViewHolder<R>
        val binding = DataBindingUtil.inflate<R>(inflater, viewType, parent, false)
        if (binding != null) {
            holder = RecyclerViewHolder(binding.root)
            holder.binding = binding
        } else {
            val v = inflater.inflate(viewType, parent, false)
            holder = RecyclerViewHolder(v)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<R>, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(eventImp)

    }


    abstract inner class EventImp<T, H : ObjectBaseRecyclerAdapter<T,R>?>(adapter: H) : View.OnClickListener, OnItemClickListener<T> {

        protected var adapterWeakReference: WeakReference<H>? = WeakReference(adapter)

        override fun onClick(v: View) {
            val obj = v.tag
            if (obj is Int) {
                val bean = getEventItem(obj)
                onItemClick(v, obj, bean)
            }
        }

        abstract fun getEventItem(pos: Int): T?

        override fun onItemClick(v: View?, position: Int, bean: T?) {
            if (adapterWeakReference != null) {
                val adapter = adapterWeakReference?.get()
                val itemClickListener = adapter?.itemClickListener
                itemClickListener?.onItemClick(v, position, bean)

            }
        }

    }


}