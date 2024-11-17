package com.example.mymoviesapp.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class CenterZoomLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {

    private val shrinkAmount = 0.15f
    private val shrinkDistance = 0.9f  // Disesuaikan agar efek shrink lebih terasa

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        applyScaleEffect()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        applyScaleEffect()
        return scrolled
    }

    private fun applyScaleEffect() {
        val midPoint = width / 2f  // Titik tengah RecyclerView
        val d0 = 0f  // Jarak minimal (tengah)
        val d1 = shrinkDistance * midPoint  // Jarak maksimum untuk pengecilan
        val s0 = 1f  // Skala awal (untuk item di tengah)
        val s1 = 1f - shrinkAmount  // Skala akhir (untuk item di luar pusat)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child?.let {
                // Titik tengah dari setiap item
                val childMidPoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                val d = abs(midPoint - childMidPoint)  // Jarak antara item dengan pusat RecyclerView

                // Skala dihitung berdasarkan jarak dari pusat
                val scale = if (d < d1) {
                    s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                } else {
                    s1
                }

                // Terapkan skala dan translationZ
                child.scaleX = scale
                child.scaleY = scale
                child.translationZ = scale * 10  // Efek depth (opsional)
            }
        }
    }
}
